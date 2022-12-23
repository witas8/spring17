package com.example.spring17.security.service;

import com.example.spring17.exceptions.NotFoundException;
import com.example.spring17.model.user.entity.User;
import com.example.spring17.repository.UserRepo;
import com.example.spring17.service.user.UserServiceSelector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserServiceSelector userServiceSelector;

    private final UserRepo userRepo;


    private String authUserName;

    @Before("test")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User", "username", username));
        log.info("User found in the database: {}", username);

        //the authorities collection has to be extended by SimpleGrantedAuthority and in our case will be consists of roles
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

}
