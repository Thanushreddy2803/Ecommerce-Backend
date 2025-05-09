package com.project.ecommerce_backend.services;

import com.project.ecommerce_backend.exception.ProductException;
import com.project.ecommerce_backend.model.Category;
import com.project.ecommerce_backend.model.Product;
import com.project.ecommerce_backend.repositry.CategoryRepository;
import com.project.ecommerce_backend.repositry.OrderItemRepository;
import com.project.ecommerce_backend.repositry.ProductRepository;
import com.project.ecommerce_backend.request.CreateProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImplementation implements ProductService {
    @Autowired
    private  ProductRepository productRepository;
    @Autowired
    private  UserService userService;
    @Autowired
    private  CategoryRepository categoryRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;



    @Override
    public Product createProduct(CreateProductRequest req) {
        Category topLavel = categoryRepository.findByName(req.getTopLavelCategory());
        if (topLavel == null) {
            Category topLavelCategory = new Category();
            topLavelCategory.setName(req.getTopLavelCategory());
            topLavelCategory.setLavel(1);
            topLavel = categoryRepository.save(topLavelCategory);
        }

        Category secondLavel = categoryRepository.findByNameAndParent(req.getSecondLavelCategory(), topLavel.getName());
        if (secondLavel == null) {
            Category secondLavelCategory = new Category();
            secondLavelCategory.setName(req.getSecondLavelCategory());
            secondLavelCategory.setParentCategory(topLavel);
            secondLavelCategory.setLavel(2);
            secondLavel = categoryRepository.save(secondLavelCategory);
        }

        Category thirdLavel = categoryRepository.findByNameAndParent(req.getThirdLavelCategory(), secondLavel.getName());
        if (thirdLavel == null) {
            Category thirdLavelCategory = new Category();
            thirdLavelCategory.setName(req.getThirdLavelCategory()); // ✅ Fixed incorrect category assignment
            thirdLavelCategory.setParentCategory(secondLavel);
            thirdLavelCategory.setLavel(3);
            thirdLavel = categoryRepository.save(thirdLavelCategory);
        }

        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setColor(req.getColor());
        product.setDescription(req.getDescription());
        product.setDiscountedPrice(req.getDiscountedPrice());
        product.setDiscountedPercent(req.getDiscountedPercent());
        product.setImageUrl(req.getImageUrl());
        product.setBrand(req.getBrand());
        product.setPrice(req.getPrice());
        product.setSizes(req.getSize());
        product.setQuantity(req.getQuantity());
        product.setCategory(thirdLavel);
        product.setCreatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product = findProductById(productId);
        product.getSizes().clear();
        productRepository.delete(product);




//        productRepository.deleteById(productId);
        return "Product deleted Successfully";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product product = findProductById(productId);
        if (req.getQuantity() != 0) {
            product.setQuantity(req.getQuantity());
        }
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) throws ProductException {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product not found"));
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        return List.of();
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);

        if (!colors.isEmpty()) {
            products = products.stream().filter(p -> colors.stream().anyMatch(c -> c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());
        }

        if (stock != null) {
            if (stock.equals("in_stock")) {
                products = products.stream().filter(p -> p.getQuantity() > 0).collect(Collectors.toList());
            } else if (stock.equals("out_of_stock")) {
                products = products.stream().filter(p -> p.getQuantity() < 1).collect(Collectors.toList());
            }
        }

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min(startIndex + pageable.getPageSize(), products.size());

        List<Product> pageContent = products.subList(startIndex, endIndex);
        return new PageImpl<>(pageContent, pageable, products.size());
    }
}