package com.example.demojwt.service;

import com.example.demojwt.entity.User;
import com.example.demojwt.entity.UserDetailsImpl;
import com.example.demojwt.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired(required = true)
    public UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=userRepo.findByEmail(email);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
       if(email.equals(user.getEmail())) {
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    //new ArrayList<>());
                    grantedAuthorities);
        }else{
            throw new UsernameNotFoundException("User not found");
        }
        //Optional<User> user= userRepo.findByEmail(email);
        //return user.map(UserDetailsImpl :: new)
                //.orElseThrow(() -> new UsernameNotFoundException(email+" doesn't exists"));
    }

}
