package com.example.demojwt.service;

import com.example.demojwt.entity.User;



public interface UserService {
    User register(User user);

    User login(User user);
}
