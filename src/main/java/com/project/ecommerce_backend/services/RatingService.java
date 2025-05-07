package com.project.ecommerce_backend.services;


import com.project.ecommerce_backend.exception.ProductException;
import com.project.ecommerce_backend.model.Rating;
import com.project.ecommerce_backend.model.User;
import com.project.ecommerce_backend.request.RatigRequest;
import org.springframework.stereotype.Service;
import java.util.*;

public interface RatingService {


    public Rating createRating(RatigRequest req, User user) throws ProductException;

    public List<Rating> getProductRatings(Long productId);
}
