package com.esad.supply_chain_management.service;

import com.esad.supply_chain_management.dto.AuthDTO;
import com.esad.supply_chain_management.dto.AuthResponseDTO;
import org.jose4j.lang.JoseException;

public interface AuthService {
    /**
     * A method that will help authenticate the user
     * @param request Request object containing username and password
     * @return A response object including token, expiry, name, email
     * @throws JoseException Error thrown when creating the token
     */
    AuthResponseDTO login(AuthDTO request) throws JoseException;
}
