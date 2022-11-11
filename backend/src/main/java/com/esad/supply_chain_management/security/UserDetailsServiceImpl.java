package com.esad.supply_chain_management.security;

import com.esad.supply_chain_management.model.User;
import com.esad.supply_chain_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Class to provide custom implementation of load user by username for logging in purposes
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // find the user by username inside the user repository. if not found, throw an exception.
        User userInDb = userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("Username invalid"));
        // initialize a spring user and return.
        return new CustomUserPrinciple(userInDb);
    }
}
