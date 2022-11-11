package com.esad.supply_chain_management.security;

import com.esad.supply_chain_management.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A custom user principle class that will be used to initialize a Spring User on successful authentication
 */
public class CustomUserPrinciple implements UserDetails {
    //class that will be used to manage the Spring User for Authentication Context

    //denotes the currently logged in user
    private User theUser;

    public String getName() {
        return this.theUser.getName();
    }

    /**
     * During creation, accept an instance of an application user (as defined in modal class)
     * @param theUser The application user.
     */
    public CustomUserPrinciple(User theUser) {
        this.theUser = theUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // add a custom role as ADMINISTRATOR for the logged in user.
        List<SimpleGrantedAuthority> theAuthorities = new ArrayList<>();
        theAuthorities.add(new SimpleGrantedAuthority("ADMINISTRATOR"));
        return theAuthorities;
    }

    @Override
    public String getPassword() {
        // return password of user
        return theUser.getPassword();
    }

    @Override
    public String getUsername() {
        // return username - email.
        return theUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        // account not expired
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // account not locked
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // credentials not expired
        return true;
    }

    @Override
    public boolean isEnabled() {
        // account enabled.
        return true;
    }
}
