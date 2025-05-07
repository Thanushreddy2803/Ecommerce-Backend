package com.project.ecommerce_backend.repositry;

import com.project.ecommerce_backend.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
