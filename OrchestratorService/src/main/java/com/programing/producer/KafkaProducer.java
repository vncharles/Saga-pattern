package com.programing.producer;

import com.programing.model.KafkaOrchestratorResponseDTO;
import com.programing.model.KafkaOrderCreatedDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, KafkaOrchestratorResponseDTO> kafkaTemplate;

    @Value("${topic.name.stock.in}")
    private String topicReserveProduct;
    @Value("${topic.name.stock.update}")
    private String topicUpdateProduct;
    @Value("${topic.name.order.approve}")
    private String topicOrderApprove;
    @Value("${topic.name.order.reject}")
    private String topicOrderReject;
    @Value("${topic.name.payment.in}")
    private String topicProcessPayment;

    public void sendReserveProduct(KafkaOrchestratorResponseDTO payload) {
        log.info("Sending to stock topic={}, payload={}", topicReserveProduct, payload);
        kafkaTemplate.send(topicReserveProduct, payload);
    }

    public void sendUpdateProduct(KafkaOrchestratorResponseDTO payload) {
        log.info("Sending to stock topic={}, payload={}", topicUpdateProduct, payload);
        kafkaTemplate.send(topicUpdateProduct, payload);
    }

    public void sendProcessPayment(KafkaOrchestratorResponseDTO payload) {
        log.info("Sending to payment topic={}, payload={}", topicProcessPayment, payload);
        kafkaTemplate.send(topicProcessPayment, payload);
    }

    public void sendOrderApprove(KafkaOrchestratorResponseDTO payload) {
        log.info("Sending to order topic={}, payload={}", topicOrderApprove, payload);
        kafkaTemplate.send(topicOrderApprove, payload);
    }

    public void sendOrderReject(KafkaOrchestratorResponseDTO payload) {
        log.info("Sending to order topic={}, payload={}", topicOrderReject, payload);
        kafkaTemplate.send(topicOrderReject, payload);
    }


}
