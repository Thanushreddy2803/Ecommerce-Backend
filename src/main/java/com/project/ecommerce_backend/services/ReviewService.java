package com.project.ecommerce_backend.services;

import com.project.ecommerce_backend.exception.ProductException;
import com.project.ecommerce_backend.model.Review;
import com.project.ecommerce_backend.model.User;
import com.project.ecommerce_backend.request.ReviewRequest;

import java.util.List;

public interface ReviewService{
    public Review createReview(ReviewRequest req, User user) throws ProductException;
    public List<Review> getAllReview(Long productId);
}
