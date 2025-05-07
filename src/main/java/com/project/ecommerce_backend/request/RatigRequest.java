package com.project.ecommerce_backend.request;

public class RatigRequest {

    private Long ProductId;
    private int rating;

    public Long getProductId() {
        return ProductId;
    }

    public void setProductId(Long productId) {
        ProductId = productId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
