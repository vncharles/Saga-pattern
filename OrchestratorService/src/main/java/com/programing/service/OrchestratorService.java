package com.programing.service;

import com.programing.model.KafkaOrchestratorResponseDTO;
import com.programing.model.KafkaOrderCreatedDTO;
import com.programing.model.OrderStatus;
import com.programing.producer.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrchestratorService {
    private final KafkaProducer kafkaProducer;

    @KafkaListener(topics = "${topic.name.order.created}", groupId = "${spring.kafka.consumer.order-group-id}")
    public void orderCreated(KafkaOrderCreatedDTO orderCreated) {
        KafkaOrchestratorResponseDTO reserveProduct = KafkaOrchestratorResponseDTO.builder()
                .orderId(orderCreated.getOrderId())
                .orderStatus(orderCreated.getStatus())
                .productId(orderCreated.getProductId())
                .quantity(orderCreated.getQuantity())
                .paymentId(orderCreated.getPaymentId())
                .build();

        kafkaProducer.sendReserveProduct(reserveProduct);
    }

    @KafkaListener(topics = "${topic.name.stock.reserved}", groupId = "${spring.kafka.consumer.stock-group-id}")
    public void reservedProduct(KafkaOrchestratorResponseDTO productResponse) {

        kafkaProducer.sendProcessPayment(productResponse);
    }

    @KafkaListener(topics = "${topic.name.stock.cancel}", groupId = "${spring.kafka.consumer.stock-group-id}")
    public void cancelProduct(KafkaOrchestratorResponseDTO productResponse) {
        productResponse.setOrderStatus(OrderStatus.ORDER_CANCELED);
        kafkaProducer.sendOrderReject(productResponse);
    }

    @KafkaListener(topics = "${topic.name.payment.out}", groupId = "${spring.kafka.consumer.payment-group-id}")
    public void paymentProcessed(KafkaOrchestratorResponseDTO orchestratorResponse) {
        orchestratorResponse.setOrderStatus(OrderStatus.ORDER_COMPLETED);
        kafkaProducer.sendOrderApprove(orchestratorResponse);
    }

    @KafkaListener(topics = "${topic.name.payment.cancel}", groupId = "${spring.kafka.consumer.payment-group-id}")
    public void paymentCancel(KafkaOrchestratorResponseDTO orchestratorResponseDTO) {
        orchestratorResponseDTO.setOrderStatus(OrderStatus.ORDER_CANCELED);
        kafkaProducer.sendOrderReject(orchestratorResponseDTO);
        kafkaProducer.sendUpdateProduct(orchestratorResponseDTO);
    }

}
