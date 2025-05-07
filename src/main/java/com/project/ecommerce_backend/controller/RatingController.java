package com.project.ecommerce_backend.controller;

import com.project.ecommerce_backend.exception.ProductException;
import com.project.ecommerce_backend.exception.UserException;
import com.project.ecommerce_backend.model.Rating;
import com.project.ecommerce_backend.model.User;
import com.project.ecommerce_backend.request.RatigRequest;
import com.project.ecommerce_backend.services.RatingService;
import com.project.ecommerce_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;
    @Autowired
    private UserService userService;



    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody RatigRequest ratingRequest,
                                               @RequestHeader("Authorization") String jwt) throws ProductException, UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Rating rating = ratingService.createRating(ratingRequest, user);
        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductRatings(@PathVariable Long productId,@RequestHeader("Authorization")String jwt)throws UserException,ProductException {
        List<Rating> ratings = ratingService.getProductRatings(productId);
        return new ResponseEntity<>(ratings, HttpStatus.CREATED);
    }
}
