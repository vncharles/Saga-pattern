package com.programing.service;

import com.programing.entity.Product;
import com.programing.model.OrderStatus;
import com.programing.model.KafkaOrchestratorResponseDTO;
import com.programing.producer.KafkaProducer;
import com.programing.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final KafkaProducer kafkaProducer;

    public List<Product> getAll() {return productRepository.findAll();}

    @PostConstruct
    private void init() {
        productRepository.save(new Product("Product1", "Descrioption", 50000.0, 2));
        productRepository.save(new Product("Product2", "Descrioption", 30000.0, 2));
        productRepository.save(new Product("Product3", "Descrioption", 40000.0, 2));
    }

    @KafkaListener(topics = "${topic.name.stock.in}", groupId = "${spring.kafka.consumer.stock-group-id}")
    public void reseveProduct(KafkaOrchestratorResponseDTO reserveProduct) {
        Product product = null;

        try {
            product = productRepository.findById(reserveProduct.getProductId()).get();
        } catch (Exception e) {
            KafkaOrchestratorResponseDTO kafkaProductResponse = KafkaOrchestratorResponseDTO.builder()
                    .orderId(reserveProduct.getOrderId())
                    .orderStatus(OrderStatus.ORDER_CANCELED)
                    .build();

            kafkaProducer.sendProductCancel(kafkaProductResponse);

            e.printStackTrace();
            return;
        }

        if(!product.checkStock(reserveProduct.getQuantity())) {
            KafkaOrchestratorResponseDTO kafkaProductResponse = KafkaOrchestratorResponseDTO.builder()
                    .orderId(reserveProduct.getOrderId())
                    .orderStatus(OrderStatus.ORDER_CANCELED)
                    .build();

            kafkaProducer.sendProductCancel(kafkaProductResponse);
        } else {
            product.reduceStock(reserveProduct.getQuantity());
            productRepository.save(product);

            KafkaOrchestratorResponseDTO kafkaProductResponse = KafkaOrchestratorResponseDTO.builder()
                    .orderId(reserveProduct.getOrderId())
                    .orderStatus(reserveProduct.getOrderStatus())
                    .productId(product.getId())
                    .quantity(reserveProduct.getQuantity())
                    .price(product.getPrice())
                    .paymentId(reserveProduct.getPaymentId())
                    .build();

            kafkaProducer.sendProductReserved(kafkaProductResponse);
        }
    }

    @KafkaListener(topics = "${topic.name.stock.update}", groupId = "${spring.kafka.consumer.stock-group-id}")
    public void updateProduct(KafkaOrchestratorResponseDTO orchestratorResponse) {
        Product product = productRepository.findById(orchestratorResponse.getProductId()).get();

        product.increaseStock(orchestratorResponse.getQuantity());
        productRepository.save(product);
    }
}
