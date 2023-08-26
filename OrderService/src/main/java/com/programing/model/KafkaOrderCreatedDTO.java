package com.programing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor
@NoArgsConstructor
public class KafkaOrderCreatedDTO {
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private OrderStatus status;
    private Long paymentId;
}
