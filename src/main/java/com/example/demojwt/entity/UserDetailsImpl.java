package com.example.demojwt.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

   // String email;
    //String password;
    //private List<GrantedAuthority> authorities;
    private User user;
    /*UserDetailsImpl(User user){
        this.email=user.getEmail();
        this.password=user.getPassword();
        this.authorities= Arrays.stream(user.getRole().split(","))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities=new ArrayList<GrantedAuthority>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+this.user.getRole().toUpperCase()));
        return grantedAuthorities;
    }

    //@Override
    //public Collection<? extends GrantedAuthority> getAuthorities() {
        //HashSet<SimpleGrantedAuthority> set=new HashSet<>();
        //set.add(new SimpleGrantedAuthority(this
          //      .user.getRole()));

      //  return authorities;
    //}

    @Override
    public String getPassword() {
        return this.user.getPassword();
        //return password;
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
        //return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
