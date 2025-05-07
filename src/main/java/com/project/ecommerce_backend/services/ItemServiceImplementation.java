package com.project.ecommerce_backend.services;

import com.project.ecommerce_backend.exception.CartItemException;
import com.project.ecommerce_backend.exception.ProductException;
import com.project.ecommerce_backend.exception.UserException;
import com.project.ecommerce_backend.model.Cart;
import com.project.ecommerce_backend.model.CartItem;
import com.project.ecommerce_backend.model.Product;
import com.project.ecommerce_backend.model.User;
import com.project.ecommerce_backend.repositry.CartItemRepository;
import com.project.ecommerce_backend.repositry.CartRepository;
import com.project.ecommerce_backend.request.AddItemRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemServiceImplementation implements ItemService{


    private CartItemRepository cartItemRepository;
    private UserService userService;
    private CartRepository cartRepository;

    public ItemServiceImplementation(CartItemRepository cartItemRepository, UserService userService, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
    }

    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());

        CartItem createdCartItem=cartItemRepository.save(cartItem);
        return createdCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
        CartItem item=findCartItemById(id);
        User user=userService.findUserById(item.getUserId());

        if(user.getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity()*item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());


        }


        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
       CartItem cartItem=cartItemRepository.isCartItemExist(cart,product,size,userId);



        return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem cartItem=findCartItemById(cartItemId);

        User user=userService.findUserById(cartItem.getUserId());

        User reqUser=userService.findUserById(userId);
        if(user.getId().equals(reqUser.getId())){
            cartItemRepository.deleteById(cartItemId);

        }
        else {
            throw new UserException("Unable to access the item");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt=cartItemRepository.findById(cartItemId);

        if(opt.isPresent()){
            return opt.get();
        }
        else {
            throw new CartItemException("cartItem not found");
        }
    }
}
