package com.sportyshoes.resources;

import com.sportyshoes.dto.OrderDTO;
import com.sportyshoes.dto.OrderItemDTO;
import com.sportyshoes.dto.PurchaseReportDTO;
import com.sportyshoes.model.User;
import com.sportyshoes.service.OrderService;
import com.sportyshoes.service.ReportService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	private final OrderService orderService;
    private final ReportService reportService;

    public OrderController(OrderService orderService, ReportService reportService) {
        this.orderService = orderService;
        this.reportService = reportService;
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
    
    @GetMapping("/report")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PurchaseReportDTO>> generatePurchaseReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Long categoryId
    ) {
        List<PurchaseReportDTO> report = reportService.generatePurchaseReport(startDate, endDate, categoryId);
        return ResponseEntity.ok(report);
    }
}