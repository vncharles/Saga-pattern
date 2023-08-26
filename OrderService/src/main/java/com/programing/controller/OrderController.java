package com.programing.controller;

import com.programing.entity.Order;
import com.programing.model.OrderRequest;
import com.programing.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public Order create(@RequestBody OrderRequest request) {
        return orderService.create(request);
    }

    @GetMapping
    public List<Order> getAll() {
        return orderService.getAll();
    }
}
