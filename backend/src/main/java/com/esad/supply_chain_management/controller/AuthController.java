package com.esad.supply_chain_management.controller;

import com.esad.supply_chain_management.dto.AuthDTO;
import com.esad.supply_chain_management.dto.AuthResponseDTO;
import com.esad.supply_chain_management.service.AuthService;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * The endpoint that will be responsible for authentication (login)
     * @param authRequest Accepts a request payload for username and password
     * @return A Response Entity (as JSON) which contains token information, name, email and expiry
     * @throws JoseException An exception thrown during an error in Token Creation
     */
    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthDTO authRequest) throws JoseException {
        AuthResponseDTO dto = authService.login(authRequest);
        return ResponseEntity.ok(dto);
    }
}
