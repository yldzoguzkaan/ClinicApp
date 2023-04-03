package com.example.clinicapp.service;

import com.example.clinicapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return (UserDetails) userRepository.findByUsername(username).orElseThrow();
    }
}
