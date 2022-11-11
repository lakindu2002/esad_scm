package com.esad.supply_chain_management.service;

import com.esad.supply_chain_management.dto.AuthDTO;
import com.esad.supply_chain_management.dto.AuthResponseDTO;
import com.esad.supply_chain_management.security.CustomUserPrinciple;
import com.esad.supply_chain_management.util.jwt.TokenManager;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service // mark as service class
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    /**
     * A method that will help authenticate the user
     *
     * @param request Request object containing username and password
     * @return A response object including token, expiry, name, email
     * @throws JoseException Error thrown when creating the token
     */
    @Override
    public AuthResponseDTO login(AuthDTO request) throws JoseException {
        // authenticate the user -> trigger user service details impl.
        Authentication successfulAuth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        // retrieve created user principle
        CustomUserPrinciple thePrincipal = (CustomUserPrinciple) successfulAuth.getPrincipal();

        String token = TokenManager.getTokenManager().createToken(thePrincipal.getUsername());
        AuthResponseDTO dto = new AuthResponseDTO();
        dto.setEmail(thePrincipal.getUsername());
        dto.setName(thePrincipal.getName());
        dto.setToken(token);
        dto.setExpiresAt(System.currentTimeMillis() + (30 * 60 * 1000));

        return dto;
    }
}
