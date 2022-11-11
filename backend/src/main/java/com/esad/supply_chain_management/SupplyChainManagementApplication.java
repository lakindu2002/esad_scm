package com.esad.supply_chain_management;

import com.esad.supply_chain_management.model.User;
import com.esad.supply_chain_management.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class SupplyChainManagementApplication {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostConstruct
    public void initialize() {
        if (userRepository.findById("admin@test.com").isEmpty()) {
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("Administrator - Test");
            user.setPassword(encoder.encode("admin12345"));

            userRepository.save(user);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(SupplyChainManagementApplication.class, args);
    }

    @Bean(name = "modelMapper")
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public Executor executor() {
        ThreadPoolTaskExecutor theExecutor = new ThreadPoolTaskExecutor();
        theExecutor.initialize(); //enable running async operations in app.
        return theExecutor;
    }
}
