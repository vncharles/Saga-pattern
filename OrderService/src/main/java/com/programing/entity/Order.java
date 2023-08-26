package com.programing.entity;

import com.programing.model.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "t_order")
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Integer quantity;
    private Double totalPrice;
    private Date createDate;
    private OrderStatus status;
    private Long paymentId;
}
