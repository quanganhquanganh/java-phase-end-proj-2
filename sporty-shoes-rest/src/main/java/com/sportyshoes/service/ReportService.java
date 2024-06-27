package com.sportyshoes.service;

import com.sportyshoes.dto.PurchaseReportDTO;
import com.sportyshoes.model.Order;
import com.sportyshoes.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    private final OrderRepository orderRepository;

    public ReportService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<PurchaseReportDTO> generatePurchaseReport(LocalDate startDate, LocalDate endDate, Long categoryId) {
        System.out.println(startDate);
        List<Order> orders = orderRepository.findByOrderDateBetween(
                startDate.atStartOfDay(),
                endDate.plusDays(1).atStartOfDay()
        );
        

        return orders.stream()
                .flatMap(order -> order.getOrderItems().stream()
                        .map(item -> new SimpleEntry<>(order, item)))
                .map(entry -> new PurchaseReportDTO(
                        entry.getValue().getProduct().getId(),
                        entry.getValue().getProduct().getName(),
                        entry.getValue().getProduct().getCategory().getName(),
                        entry.getValue().getQuantity(),
                        entry.getValue().getProduct().getPrice().multiply(BigDecimal.valueOf(entry.getValue().getQuantity())),
                        entry.getKey().getOrderDate()
                ))
                .collect(Collectors.toList());
    }
}
