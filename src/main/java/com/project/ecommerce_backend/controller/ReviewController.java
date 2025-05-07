package com.project.ecommerce_backend.controller;

import com.project.ecommerce_backend.exception.ProductException;
import com.project.ecommerce_backend.exception.UserException;
import com.project.ecommerce_backend.model.Review;
import com.project.ecommerce_backend.model.User;
import com.project.ecommerce_backend.request.ReviewRequest;
import com.project.ecommerce_backend.services.ReviewService;
import com.project.ecommerce_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private  ReviewService reviewService;
    @Autowired
    private  UserService userService;


    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest reviewRequest,
                                               @RequestHeader("Authorization") String jwt) throws ProductException, UserException {

        User user = userService.findUserProfileByJwt(jwt);
        Review review = reviewService.createReview(reviewRequest, user);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getAllReviews(@PathVariable Long productId)throws UserException,ProductException {
        List<Review> reviews = reviewService.getAllReview(productId);
        return new ResponseEntity<>(reviews, HttpStatus.ACCEPTED);
    }
}
