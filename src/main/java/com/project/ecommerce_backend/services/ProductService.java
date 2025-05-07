package com.project.ecommerce_backend.services;

import com.project.ecommerce_backend.exception.ProductException;
import com.project.ecommerce_backend.model.Product;
import com.project.ecommerce_backend.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.*;
public interface ProductService {
    public Product createProduct(CreateProductRequest req);

    public String deleteProduct(Long productId) throws ProductException;

    public Product updateProduct(Long productId,Product req) throws ProductException;

    public Product findProductById(Long id) throws ProductException;

    public List<Product> findProductByCategory(String category);

    public Page<Product> getAllProduct(String category,List<String>colors,List<String>sizes,Integer minPrice,Integer maxPrice,Integer minDiscount,String sort,String stock,Integer pageNumber,Integer pageSize);
}
