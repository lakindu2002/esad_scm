package com.esad.supply_chain_management.service;

import com.esad.supply_chain_management.dto.SupplierDTO;
import com.esad.supply_chain_management.exceptions.ResourceCannotBeDeletedException;
import com.esad.supply_chain_management.exceptions.ResourceExistsException;
import com.esad.supply_chain_management.exceptions.ResourceNotFoundException;

import java.util.List;

public interface SupplierService {
    /**
     * Creates a supplier in the system if the supplier email does not already exist
     *
     * @param supplier The supplier to create
     * @return The created supplier
     * @throws ResourceExistsException Thrown if supplier with same email exists.
     */
    SupplierDTO createSupplier(SupplierDTO supplier) throws ResourceExistsException;

    /**
     * Returns a list of all the suppliers in the system.
     *
     * @return The list of suppliers.
     */
    List<SupplierDTO> getAllSuppliers();

    /**
     * Updates the supplier based on given attributes
     *
     * @param patchAttrs The attributes to update
     * @param id         The id of the supplier
     * @return The updated supplier
     * @throws ResourceNotFoundException Thrown if the supplier does not exist
     */
    SupplierDTO updateSupplier(SupplierDTO patchAttrs, Long id) throws ResourceNotFoundException;

    /**
     * Deletes a supplier only if the supplier is not managing any raw material at the moment
     *
     * @param id The id of the supplier to delete
     * @return Boolean to indicate if deleted or not
     * @throws ResourceCannotBeDeletedException Thrown if supplier is managing raw materials
     * @throws ResourceNotFoundException        Thrown if supplier does not exist.
     */
    boolean deleteSupplierById(Long id) throws ResourceCannotBeDeletedException, ResourceNotFoundException;
}
