package com.project.ecommerce_backend.controller;


import com.project.ecommerce_backend.exception.ProductException;
import com.project.ecommerce_backend.exception.UserException;
import com.project.ecommerce_backend.model.Cart;
import com.project.ecommerce_backend.model.User;
import com.project.ecommerce_backend.request.AddItemRequest;
import com.project.ecommerce_backend.response.ApiResponse;
import com.project.ecommerce_backend.services.CartItemService;
import com.project.ecommerce_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
//@Tag(name="Cart Management",description="find user cart,add item to cart")
public class CartController {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
//    @Operation(description="find cart by user id")
    public ResponseEntity<Cart>findUserCart(@RequestHeader("Authorization")String jwt)throws UserException{
        User user=userService.findUserProfileByJwt(jwt);
        Cart cart=cartItemService.findUserCart(user.getId());

        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<ApiResponse>addItemToCart(@RequestBody AddItemRequest req,@RequestHeader("Authorization")String jwt)throws UserException, ProductException{
        User user=userService.findUserProfileByJwt(jwt);
        cartItemService.addCartItem(user.getId(), req);
        ApiResponse res=new ApiResponse();
        res.setMessage("item added to cart");
        res.setStatus(true);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }
//    @DeleteMapping("/remove/{cartItemId}")
//    public ResponseEntity<ApiResponse> removeItemFromCart(
//            @PathVariable Long cartItemId,
//            @RequestHeader("Authorization") String jwt
//    ) throws UserException {
//        User user = userService.findUserProfileByJwt(jwt);
//        cartItemService.removeCartItem(user.getId(), cartItemId);
//
//        ApiResponse res = new ApiResponse();
//        res.setMessage("Item removed from cart");
//        res.setStatus(true);
//
//        return new ResponseEntity<>(res, HttpStatus.OK);
//    }


}
