package com.programing.producer;

import com.programing.model.KafkaOrchestratorResponseDTO;
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
    @Value("${topic.name.stock.reserved}")
    private String topicProductReserved;
    @Value("${topic.name.stock.cancel}")
    private String topicProductCancel;

    public void sendProductReserved(KafkaOrchestratorResponseDTO payload) {
        log.info("Sending to orchestrator topic={}, payload={}", topicProductReserved, payload);
        kafkaTemplate.send(topicProductReserved, payload);
    }

    public void sendProductCancel(KafkaOrchestratorResponseDTO payload) {
        log.info("Sending to orchestrator topic={}, payload={}", topicProductCancel, payload);
        kafkaTemplate.send(topicProductCancel, payload);
    }
}
