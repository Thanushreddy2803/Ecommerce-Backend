package com.project.ecommerce_backend.services;


import com.project.ecommerce_backend.exception.ProductException;
import com.project.ecommerce_backend.exception.UserException;
import com.project.ecommerce_backend.model.*;
import com.project.ecommerce_backend.request.AddItemRequest;

public interface CartItemService {
    public Cart createCart(User user);

    public  String addCartItem(Long userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long userId);

}
