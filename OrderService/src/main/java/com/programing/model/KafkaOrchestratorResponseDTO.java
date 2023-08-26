package com.programing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor
@NoArgsConstructor
public class KafkaOrchestratorResponseDTO {
    private Long orderId;
    private OrderStatus orderStatus;
    private Long productId;
    private Integer quantity;
    private Double price;
    private Long paymentId;
}
