package com.esad.supply_chain_management.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

/**
 * Configuration class that is used to define the security configurations for the application.
 */
@Configuration //configuration class for bean definition methods
@EnableWebSecurity //enable Spring Security for Web
@EnableGlobalMethodSecurity(prePostEnabled = true) //enable security at method level
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * Use of the user details service injected with defined instance to help located user by username.
     */
    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    /**
     * The JWT Filter added as a dependency injected by IOC container to configure the filter chain
     */
    @Autowired
    @Qualifier("jwtFilter")
    private JwtFilter jwtFilter;

    /**
     * Define a bean that initializes a BCrypt password encoder to hash the password when storing in db
     *
     * @return The initialized encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // return bcrypt encoder with strength of 10 (10 iterations)
        return new BCryptPasswordEncoder(10);
    }

    /**
     * Initialize a DAO Authentication provider that uses the created password encoder for authentication.
     * It also uses the user details service defined to help locate the user.
     *
     * @return Initialized DAO Authentication Provider
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        //method used to define a DAO Authenticator
        DaoAuthenticationProvider theProvider = new DaoAuthenticationProvider();

        theProvider.setPasswordEncoder(passwordEncoder()); //assign the defined BCrypt Encoder for authentication
        //pass the custom user details service implementation so spring knows where to find user information from
        theProvider.setUserDetailsService(userDetailsService);
        return theProvider;
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // created to help perform authentication.
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //used to configure the Authentication Provider for Spring Security
        auth.authenticationProvider(daoAuthenticationProvider()); //configure a DAO Provider for Authentication of App.
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // disable csrf checks
                .cors()  // use cors
                .configurationSource(corsConfigurationSource()) // use defined cors configuration
                .and()
                .authorizeRequests() // authorize all requests
                .antMatchers("/api/auth/**") // provide public access to auth endpoints.
                .permitAll()
                .anyRequest() //other requests must be authenticated
                .authenticated()
                .and()
                // add JWT filter before UsernamePasswordAUthentication filter class to ensure only an authenticated user enters system.
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement()
                // dont maintain sessions.
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); //since REST, do not maintain sessions.
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        //create a URL Based CORS Configurator
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        //create a CORS Configuration Object
        final CorsConfiguration corsConfiguration = new CorsConfiguration();

        //configure the cors object
        corsConfiguration.setAllowCredentials(true);
        //allow requests from NextJS Client
        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        //configure headers allowed via CORS
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
        //configure methods allowed from external domain
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        //set endpoint as base path to enable CORS access for whole API
        source.registerCorsConfiguration("/**", corsConfiguration);
        //create a FilterRegistrationBean and return it
        return source;
    }
}