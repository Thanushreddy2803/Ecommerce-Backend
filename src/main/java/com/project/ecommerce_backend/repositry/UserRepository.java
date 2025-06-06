package com.project.ecommerce_backend.repositry;

import com.project.ecommerce_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long > {

    public User findByEmail(String email);
}
