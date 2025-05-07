package com.project.ecommerce_backend.repositry;

import com.project.ecommerce_backend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
