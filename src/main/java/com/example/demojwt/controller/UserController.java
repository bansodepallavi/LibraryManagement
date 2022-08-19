package com.example.demojwt.controller;

import com.example.demojwt.entity.User;
import com.example.demojwt.exception.ApiResponse;
import com.example.demojwt.exception.InvalidFieldException;
import com.example.demojwt.helper.JwtAuthResponse;
import com.example.demojwt.helper.JwtUtil;
import com.example.demojwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.Optional;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    JwtUtil jwtTockenHelper;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> register(@Valid @RequestBody User user){
            User u = userService.register(user);
            return ResponseEntity.of(Optional.of(u));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        this.authenticate(user.getEmail(), user.getPassword());

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getEmail());

        String token = this.jwtTockenHelper.generateToken(userDetails);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @RequestMapping("/welcome")
    public String welcome(){
        return "Welcome to home page";
    }

    private void authenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
                password);

        try {
            this.authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            System.out.println("Invalid Detials !!");
            throw new InvalidFieldException("Invalid username or password !!");
        }

    }

}
