package com.programing.service;

import com.programing.entity.Order;
import com.programing.model.OrderStatus;
import com.programing.model.KafkaOrchestratorResponseDTO;
import com.programing.model.KafkaOrderCreatedDTO;
import com.programing.model.OrderRequest;
import com.programing.producer.KafkaProducer;
import com.programing.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaProducer kafkaProducer;

    public List<Order> getAll(){return orderRepository.findAll();}

    public Order create(OrderRequest request) {
        Order order = orderRepository.save(toOrder(request));

        KafkaOrderCreatedDTO orderCreatedDTO = KafkaOrderCreatedDTO.builder()
                .orderId(order.getId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .status(order.getStatus())
                .paymentId(order.getPaymentId())
                .build();

        kafkaProducer.sendOrder(orderCreatedDTO);

        return order;
    }

    private Order toOrder(OrderRequest request) {
        Order order = Order.builder()
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .createDate(new Date())
                .status(OrderStatus.ORDER_CREATED)
                .paymentId(request.getPaymentId())
                .build();

        return order;
    }

    @KafkaListener(topics = "${topic.name.order.reject}",
            groupId = "${spring.kafka.consumer.order-group-id}")
    public void rejectOrder(KafkaOrchestratorResponseDTO orderReject) {
        Order order = orderRepository.findById(orderReject.getOrderId()).orElseThrow();

        order.setStatus(orderReject.getOrderStatus());
        orderRepository.save(order);
    }

    @KafkaListener(topics = "${topic.name.order.approve}",
            groupId = "${spring.kafka.consumer.order-group-id}")
    public void approveOrder(KafkaOrchestratorResponseDTO orderApprove) {
        Order order = orderRepository.findById(orderApprove.getOrderId()).orElseThrow();

        order.setTotalPrice(order.getQuantity()*orderApprove.getPrice());
        order.setStatus(orderApprove.getOrderStatus());

        orderRepository.save(order);
    }
}
