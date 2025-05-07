package com.project.ecommerce_backend.services;

import com.project.ecommerce_backend.model.Address;
import com.project.ecommerce_backend.model.*;
import  com.project.ecommerce_backend.exception.OrderException;

import java.util.List;

public interface OrderService  {

    public  Order createOrder(User user, Address shippingAddress);

    public  Order findOrderById(Long orderId) throws OrderException;

    public List<Order> usersOrderHistory(Long userId);

    public Order placedOrder(Long orderId) throws OrderException;

    public Order confiredOrder(Long orderId) throws OrderException;

    public Order shippedOrder(Long orderId) throws OrderException;

    public Order deliveredOrder(Long orderId) throws OrderException;

    public Order cancelledOrder(Long orderId) throws OrderException;

    public List<Order>getAllOrders();

    public void deleteOrder(Long orderId) throws OrderException;
}
