package com.project.ecommerce_backend.services;

import com.project.ecommerce_backend.exception.CartItemException;
import com.project.ecommerce_backend.exception.UserException;
import com.project.ecommerce_backend.model.Cart;
import com.project.ecommerce_backend.model.CartItem;
import com.project.ecommerce_backend.model.Product;

public interface ItemService {

    public CartItem createCartItem(CartItem cartItem);
    public CartItem updateCartItem(Long userId,Long id,CartItem cartItem) throws CartItemException, UserException;

    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

    public void removeCartItem(Long userId,Long cartItemId) throws  CartItemException,UserException;
    public  CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
