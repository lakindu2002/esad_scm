package com.esad.supply_chain_management.security;

import com.esad.supply_chain_management.dto.CustomDTO;
import com.esad.supply_chain_management.util.jwt.TokenManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jose4j.jwt.JwtClaims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A once per request filter that gets added to a security filter chain.
 * This filter will be responsible for validating the JWT Token before sending requests to the backend
 * Hence, if only a token is valid, it will forward the request.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // retrieve the authorization header
            String authHeader = request.getHeader("Authorization"); //retrieve the header with name Authorization
            // jwt tokens have "Bearer " prepended to it, so if its not there, theres not JWT token, hence no point validating
            // directly forward along the chain.
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }
            //token is present.
            String passedToken = authHeader.substring("Bearer ".length()); //get the token
            JwtClaims decodedClaims = TokenManager.getTokenManager().decodeToken(passedToken);

            // retrieve claims
            String email = (String) decodedClaims.getClaimsMap().get("email");
            String role = (String) decodedClaims.getClaimsMap().get("role");

            // compile authorities from token
            List<SimpleGrantedAuthority> theAuthorities = new ArrayList<>();
            theAuthorities.add(new SimpleGrantedAuthority(role));

            // authenticate user per request.
            UsernamePasswordAuthenticationToken theTokenForRequest = new UsernamePasswordAuthenticationToken(email, null, theAuthorities);
            theTokenForRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(theTokenForRequest);

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            CustomDTO theException = new CustomDTO(403, "The token could not be authenticated");
            // write JSON response to the client using Jackson Project
            new ObjectMapper().writer().writeValue(response.getOutputStream(), theException);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }
}
