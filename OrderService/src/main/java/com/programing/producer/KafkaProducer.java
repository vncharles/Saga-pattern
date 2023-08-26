package com.programing.producer;

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
    private final KafkaTemplate<String, KafkaOrderCreatedDTO> kafkaTemplate;
    @Value("${topic.name.order.created}")
    private String topicCreateOrder;

    public void sendOrder(KafkaOrderCreatedDTO payload) {
        log.info("Sending to orchestrator topic={}, payload={}", topicCreateOrder, payload);
        kafkaTemplate.send(topicCreateOrder, payload);
    }
}
