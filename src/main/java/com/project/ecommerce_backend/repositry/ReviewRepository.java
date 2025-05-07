package com.project.ecommerce_backend.repositry;

import com.project.ecommerce_backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    @Query("SELECT re FROM Review re where re.product.id=:productId")
    public List<Review> getAllProductReview(@Param("productId")Long productId);
}
