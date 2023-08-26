package com.programing.model;

import lombok.Data;

@Data
public class OrderRequest {
    private Long productId;
    private Integer quantity;
    private Long paymentId;
}
