package com.project.ecommerce_backend.services;

import com.project.ecommerce_backend.exception.ProductException;
import com.project.ecommerce_backend.model.Product;
import com.project.ecommerce_backend.model.Review;
import com.project.ecommerce_backend.model.User;
import com.project.ecommerce_backend.repositry.ProductRepository;
import com.project.ecommerce_backend.repositry.ReviewRepository;
import com.project.ecommerce_backend.request.ReviewRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImplementation implements ReviewService{
    private ReviewRepository reviewRepository;
    private ProductRepository productRepository;
    private ProductService productService;

    public ReviewServiceImplementation(ReviewRepository reviewRepository, ProductRepository productRepository, ProductService productService) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {
        Product product=productService.findProductById(req.getProductId());
        Review review=new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setReviewText(req.getReview());
        review.setCreatedAt(LocalDateTime.now());


        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductReview(productId);
    }
}
