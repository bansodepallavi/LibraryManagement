package com.example.demojwt.service.impl;

import com.example.demojwt.entity.Book;
import com.example.demojwt.entity.Cart;
import com.example.demojwt.entity.User;
import com.example.demojwt.exception.InvalidFieldException;
import com.example.demojwt.exception.ResourseNotFoundException;
import com.example.demojwt.helper.JwtAuthResponse;
import com.example.demojwt.helper.JwtUtil;
import com.example.demojwt.repository.UserRepo;
import com.example.demojwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserServiceImpl implements UserService {
    final static String role = "ROLE_USER";
    @Autowired
    UserRepo userRepo;
    //private UserServiceImpl authenticationManager;


    @Override
    public User register(User user){
        validateFields(user);
        User user1 = userRepo.findByEmail(user.getEmail());
        if (user1 == null) {
            if(user.getRole() == null)
                user.setRole(role);
            userRepo.save(user);
            return user;
        } else {
            throw new InvalidFieldException("User already exists...");
        }
    }

    @Override
    public User login(User user) {
        validateFields(user);
        User user1 = userRepo.findById(user.getEmail())
                .orElseThrow(() -> new InvalidFieldException("Invalid username or password"));
        if(!user.getPassword().equals(user1.getPassword()))
            throw new InvalidFieldException("Invalid username or password");
        return user1;

    }

    public void validateFields(User user){
        String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";
        Pattern pattern=Pattern.compile(EMAIL_PATTERN);
        Matcher matcher=pattern.matcher(user.getEmail());
        if(!matcher.matches())
            throw new InvalidFieldException("please enter valid email address");
        if(user.getPassword() == null || user.getPassword().length() <= 4)
            throw new InvalidFieldException("password should be greater than length 4");

    }
}
