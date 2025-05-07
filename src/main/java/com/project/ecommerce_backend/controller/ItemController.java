package com.project.ecommerce_backend.controller;

import com.project.ecommerce_backend.exception.CartItemException;
import com.project.ecommerce_backend.exception.UserException;
import com.project.ecommerce_backend.model.CartItem;
import com.project.ecommerce_backend.model.User;
import com.project.ecommerce_backend.request.AddItemRequest;
import com.project.ecommerce_backend.response.ApiResponse;
import com.project.ecommerce_backend.services.ItemService;
import com.project.ecommerce_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_items")
public class ItemController {

    @Autowired
    private  ItemService itemService;
    @Autowired
    private UserService userService;



    @PostMapping("/add")
    public ResponseEntity<CartItem> addCartItem(@RequestBody CartItem cartItem) {
        CartItem createdItem = itemService.createCartItem(cartItem);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long cartItemId,
            @RequestBody CartItem cartItem
    ) throws CartItemException, UserException {
        User user = userService.findUserProfileByJwt(jwt);

        // 🔒 Optionally validate ownership before updating
        CartItem updatedItem = itemService.updateCartItem(user.getId(), cartItemId, cartItem);

        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }



    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long cartItemId
    ) throws UserException, CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        itemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse response = new ApiResponse();
        response.setStatus(true);
        response.setMessage("Item removed from cart");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

