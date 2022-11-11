package com.esad.supply_chain_management.service;

import com.esad.supply_chain_management.dto.RawMaterialDTO;
import com.esad.supply_chain_management.exceptions.ResourceNotFoundException;

import java.util.List;

public interface RawMaterialService {
    /**
     * Returns a list of all raw materials
     *
     * @return The raw materials to return
     */
    List<RawMaterialDTO> getAllRawMaterials();

    /**
     * Updates the raw material based on the patch attributes passed
     *
     * @param patchAttr The attributes to updated
     * @param id        The Id to of the raw material to update
     * @return The raw material
     * @throws ResourceNotFoundException thrown when raw material is not found
     */
    RawMaterialDTO updateRawMaterial(RawMaterialDTO patchAttr, Long id) throws ResourceNotFoundException;

    /**
     * Sort the raw materials in ascending order of the threshold quantity using Strategy
     *
     * @return The sorted raw materials
     */
    List<RawMaterialDTO> performAscendingSort();

    /**
     * Sort the raw materials in descending order of the threshold quantity using Strategy
     *
     * @return The sorted raw materials
     */
    List<RawMaterialDTO> performDescendingSort();
}
