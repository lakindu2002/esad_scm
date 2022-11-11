package com.esad.supply_chain_management.service;

import com.esad.supply_chain_management.dto.TruckDTO;
import com.esad.supply_chain_management.exceptions.ResourceExistsException;
import com.esad.supply_chain_management.exceptions.ResourceNotFoundException;

import java.util.List;

public interface TruckService {
    /**
     * Creates a truck if the license and chassis number is unique
     *
     * @param createTruck The truck attributes
     * @return The created truck
     * @throws ResourceExistsException thrown if license plate or chassis number exists
     */
    TruckDTO createTruck(TruckDTO createTruck) throws ResourceExistsException;

    /**
     * Returns a list of all the trucks present in the system
     *
     * @return The list of trucks
     */
    List<TruckDTO> getAllTrucks();

    /**
     * Updates the truck based on the values passed.
     *
     * @param patchAttr The values to patch
     * @param id        The id of the truck
     * @return The updated truck
     * @throws ResourceNotFoundException thrown if the truck does not exist
     */
    TruckDTO updateTruck(TruckDTO patchAttr, Long id) throws ResourceNotFoundException;
}
