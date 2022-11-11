package com.esad.supply_chain_management.repository;

import com.esad.supply_chain_management.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * use JPA Repository to obtain certain implementations such as find all
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    // custom JPQL Query to find vehicle by license plate.
    // returns optional as vehicle can exist or not
    @Query("SELECT theVehicle from Vehicle theVehicle WHERE theVehicle.licensePlate = :licensePlate")
    Optional<Vehicle> findByLicensePlate(String licensePlate);

    // custom JPQL Query to find vehicle by chassis number.
    // returns optional as vehicle can exist or not
    @Query("SELECT theVehicle from Vehicle theVehicle WHERE theVehicle.chassisNumber = :chassisNumber")
    Optional<Vehicle> findByChassisNumber(String chassisNumber);
}
