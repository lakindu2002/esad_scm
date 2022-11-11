package com.esad.supply_chain_management.repository;

import com.esad.supply_chain_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * use JPA Repository to obtain certain implementations such as find all
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
