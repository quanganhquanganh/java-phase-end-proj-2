package com.sportyshoes.service;

import com.sportyshoes.dto.OrderDTO;
import com.sportyshoes.dto.OrderItemDTO;
import com.sportyshoes.model.Order;
import com.sportyshoes.model.OrderItem;
import com.sportyshoes.model.Product;
import com.sportyshoes.model.User;
import com.sportyshoes.repository.OrderRepository;
import com.sportyshoes.repository.ProductRepository;
import com.sportyshoes.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderDTO placeOrder(Long userId, List<OrderItemDTO> orderItemDTOs) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemDTO itemDTO : orderItemDTOs) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDTO.getQuantity());

            orderItems.add(orderItem);
            totalAmount = totalAmount.add(product.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity())));
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    public List<OrderDTO> getUserOrders(Long userId) {
        List<Order> userOrders = orderRepository.findByUserId(userId);
        return userOrders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setUserId(order.getUser().getId());
        dto.setOrderItems(order.getOrderItems().stream()
                .map(this::convertToOrderItemDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    private OrderItemDTO convertToOrderItemDTO(OrderItem orderItem) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setProductId(orderItem.getProduct().getId());
        dto.setQuantity(orderItem.getQuantity());
        return dto;
    }
}
