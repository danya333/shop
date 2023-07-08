package com.example.shop.services;

import com.example.shop.Repositories.OrderRepository;
import com.example.shop.models.Order;
import com.example.shop.models.OrderStatus;
import com.example.shop.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;

    // Получить список всех заказов
    public List<Order> findAllOrders(){
        return orderRepository.findAll();
    }

    // Получить заказ по ID
    public Order findOrderById(Long id){
        return orderRepository.findById(id).orElse(null);
    }

    // Изменить статус заказа
    public void editOrderStatus(Long orderId, String status){
        Order order = findOrderById(orderId);
        if (status.compareTo("confirm") == 0){
            order.setStatus(OrderStatus.CONFIRMED);
            orderRepository.save(order);
        } else if (status.compareTo("reject") == 0) {
            order.setStatus(OrderStatus.REJECTED);
            orderRepository.save(order);
        }
    }

    // Получить все заказы пользователя
    public List<Order> findAllOrdersByUser(){
        User user = userService.getCurrentUser();
        return orderRepository.findAllByUser(user);
    }


}
