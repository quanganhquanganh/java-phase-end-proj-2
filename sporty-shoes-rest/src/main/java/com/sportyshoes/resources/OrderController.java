package com.sportyshoes.resources;

import com.sportyshoes.dto.OrderDTO;
import com.sportyshoes.dto.OrderItemDTO;
import com.sportyshoes.model.User;
import com.sportyshoes.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody List<OrderItemDTO> orderItems) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getId();
        OrderDTO placedOrder = orderService.placeOrder(userId, orderItems);
        return ResponseEntity.ok(placedOrder);
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderDTO>> getUserOrders() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((User) authentication.getPrincipal()).getId();
        List<OrderDTO> userOrders = orderService.getUserOrders(userId);
        return ResponseEntity.ok(userOrders);
    }
}