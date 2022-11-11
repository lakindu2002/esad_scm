package com.esad.supply_chain_management.repository;

import com.esad.supply_chain_management.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * use JPA Repository to obtain certain implementations such as find all
 */
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    // creating a custom jpa sql query to find supplier by email
    // query gets resolved at compile time.
    @Query("SELECT supplier from Supplier supplier WHERE supplier.email = :email")
    Supplier findSupplierByEmail(String email);
}
