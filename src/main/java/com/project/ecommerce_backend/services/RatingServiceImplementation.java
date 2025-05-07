package com.project.ecommerce_backend.services;

import com.project.ecommerce_backend.exception.ProductException;
import com.project.ecommerce_backend.model.Product;
import com.project.ecommerce_backend.model.Rating;
import com.project.ecommerce_backend.model.User;
import com.project.ecommerce_backend.repositry.RatingRepository;
import com.project.ecommerce_backend.request.RatigRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImplementation implements RatingService{
    private ProductService productService;
    private RatingRepository ratingRepository;

    public RatingServiceImplementation(ProductService productService, RatingRepository ratingRepository) {
        this.productService = productService;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public Rating createRating(RatigRequest req, User user) throws ProductException {
        Product product=productService.findProductById(req.getProductId());

        Rating rating=new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreatedAt(LocalDateTime.now());

        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductRatings(Long productId) {




        return ratingRepository.getAllProductRating(productId);
    }
}
