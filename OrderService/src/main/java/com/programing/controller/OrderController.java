package com.programing.controller;

import com.programing.entity.Order;
import com.programing.model.OrderRequest;
import com.programing.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public Order create(@RequestBody OrderRequest request) {
        return orderService.create(request);
    }
}
