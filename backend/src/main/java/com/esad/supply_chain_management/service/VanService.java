package com.esad.supply_chain_management.service;

import com.esad.supply_chain_management.dto.VanDTO;
import com.esad.supply_chain_management.exceptions.ResourceExistsException;
import com.esad.supply_chain_management.exceptions.ResourceNotFoundException;

import java.util.List;

public interface VanService {
    /**
     * Creates a van if the license and chassis number is unique
     *
     * @param createAttr The van attributes
     * @return The created van
     * @throws ResourceExistsException thrown if license plate or chassis number exists
     */
    VanDTO createVan(VanDTO createAttr) throws ResourceExistsException;

    /**
     * Returns a list of all the vans present in the system
     *
     * @return The list of vans
     */
    List<VanDTO> getAllVans();

    /**
     * Updates the van based on the values passed.
     * @param patchAttr The values to patch
     * @param id The id of the van
     * @return The updated van
     * @throws ResourceNotFoundException thrown if the van does not exist
     */
    VanDTO updateVan(VanDTO patchAttr, Long id) throws ResourceNotFoundException;
}
