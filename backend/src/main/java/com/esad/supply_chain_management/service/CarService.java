package com.esad.supply_chain_management.service;

import com.esad.supply_chain_management.dto.CarDTO;
import com.esad.supply_chain_management.exceptions.ResourceExistsException;
import com.esad.supply_chain_management.exceptions.ResourceNotFoundException;

import java.util.List;

public interface CarService {
    /**
     * Creates a car if the license and chassis number is unique
     *
     * @param createAttributes The car attributes
     * @return The created car
     * @throws ResourceExistsException thrown if license plate or chassis number exists
     */
    CarDTO createCar(CarDTO createAttributes) throws ResourceExistsException;

    /**
     * Returns a list of all the cars present in the system
     *
     * @return The list of cars
     */
    List<CarDTO> getAllCars();

    /**
     * Updates the car based on the values passed.
     * @param patchAttr The values to patch
     * @param id The id of the car
     * @return The updated car
     * @throws ResourceNotFoundException thrown if the car does not exist
     */
    CarDTO updateCar(CarDTO patchAttr, Long id) throws ResourceNotFoundException;
}
