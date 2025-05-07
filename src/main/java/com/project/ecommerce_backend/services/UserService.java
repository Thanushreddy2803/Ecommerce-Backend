package com.project.ecommerce_backend.services;

import com.project.ecommerce_backend.exception.UserException;
import com.project.ecommerce_backend.model.User;

public interface UserService {

    public User findUserById(Long userId) throws UserException;

    public  User findUserProfileByJwt(String jwt) throws UserException;
}
