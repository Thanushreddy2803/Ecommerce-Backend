package com.project.ecommerce_backend.services;


import com.project.ecommerce_backend.exception.ProductException;
import com.project.ecommerce_backend.exception.UserException;
import com.project.ecommerce_backend.model.Cart;
import com.project.ecommerce_backend.model.CartItem;
import com.project.ecommerce_backend.model.Product;
import com.project.ecommerce_backend.model.User;
import com.project.ecommerce_backend.repositry.CartRepository;
import com.project.ecommerce_backend.request.AddItemRequest;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImplementation implements CartItemService{
    private CartRepository cartRepository;
    private ItemService ItemService;
    private ProductService productService;

    public CartServiceImplementation(CartRepository cartRepository, ProductService productService, com.project.ecommerce_backend.services.ItemService itemService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.ItemService = itemService;
    }

    @Override
    public Cart createCart(User user) {
        Cart cart=new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
        Cart cart=cartRepository.findByUserId(userId);
        Product product=productService.findProductById(req.getProductId());

        CartItem isPresent=ItemService.isCartItemExist(cart,product, req.getSize(), userId);

        if (isPresent==null){
            CartItem cartItem=new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(userId);

            int price= req.getQuantity()*product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());

            CartItem createdCartItem=ItemService.createCartItem(cartItem);

            cart.getCartItem().add(createdCartItem);

        }


        return "Item Added Successfully.";
    }

    @Override
    public Cart findUserCart(Long userId) {

        Cart cart=cartRepository.findByUserId(userId);

        int totalPrice=0;
        int totalDiscountedPrice=0;
        int totalItem=0;

        for (CartItem cartItem: cart.getCartItem()){
            totalPrice=totalPrice+cartItem.getPrice();
            totalDiscountedPrice+=cartItem.getDiscountedPrice();
            totalItem+=cartItem.getQuantity();
        }
        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalPrice(totalPrice);
        cart.setTotalItem(totalItem);
        cart.setDiscount(totalPrice-totalDiscountedPrice);
        return cartRepository.save(cart);
}
//    @Override
//    public void removeCartItem(Long userId, Long cartItemId) throws UserException {
//        Cart item = cartRepository.findById(cartItemId)
//                .orElseThrow(() -> new RuntimeException("Cart item not found"));
//
//        if (!item.getUser().getId().equals(userId)) {
//            throw new RuntimeException("Unauthorized to delete this cart item");
//        }
//
//        cartRepository.delete(item);
//    }

}
