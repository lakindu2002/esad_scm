package com.esad.supply_chain_management.service;

import com.esad.supply_chain_management.dto.SupplierDTO;
import com.esad.supply_chain_management.exceptions.ResourceCannotBeDeletedException;
import com.esad.supply_chain_management.exceptions.ResourceExistsException;
import com.esad.supply_chain_management.exceptions.ResourceNotFoundException;
import com.esad.supply_chain_management.model.Item;
import com.esad.supply_chain_management.model.Supplier;
import com.esad.supply_chain_management.repository.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    /**
     * Creates a supplier in the system if the supplier email does not already exist
     *
     * @param supplier The supplier to create
     * @return The created supplier
     * @throws ResourceExistsException Thrown if supplier with same email exists.
     */
    @Override
    public SupplierDTO createSupplier(SupplierDTO supplier) throws ResourceExistsException {
        ModelMapper mapper = new ModelMapper();
        Supplier parsedSupplier = mapper.map(supplier, Supplier.class);
        parsedSupplier.setEmail(supplier.getEmail().toLowerCase(Locale.ROOT));

        if (!Objects.isNull(supplierRepository.findSupplierByEmail(supplier.getEmail()))) {
            throw new ResourceExistsException("A supplier with the same email already exists");
        }

        Supplier createdSupplier = supplierRepository.save(parsedSupplier);
        return mapper.map(createdSupplier, SupplierDTO.class);
    }

    /**
     * Returns a list of all the suppliers in the system.
     *
     * @return The list of suppliers.
     */
    @Override
    public List<SupplierDTO> getAllSuppliers() {
        List<Supplier> suppliers = supplierRepository.findAll();
        ModelMapper mapper = new ModelMapper();
        return suppliers.stream().map((supplier -> mapper.map(supplier, SupplierDTO.class))).collect(Collectors.toList());
    }

    /**
     * Updates the supplier based on given attributes
     *
     * @param patchAttrs The attributes to update
     * @param id         The id of the supplier
     * @return The updated supplier
     * @throws ResourceNotFoundException Thrown if the supplier does not exist
     */
    @Override
    public SupplierDTO updateSupplier(SupplierDTO patchAttrs, Long id) throws ResourceNotFoundException {
        ModelMapper mapper = new ModelMapper();
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isEmpty()) {
            throw new ResourceNotFoundException("The supplier does not exist");
        }
        Supplier supplierInDb = supplier.get();

        if (!Objects.isNull(patchAttrs.getEmail())) {
            supplierInDb.setEmail(patchAttrs.getEmail());
        }

        if (!Objects.isNull(patchAttrs.getName())) {
            supplierInDb.setName(patchAttrs.getName());
        }

        if (!Objects.isNull(patchAttrs.getManagingRawMaterials())) {
            List<Item> rawMaterials = patchAttrs.getManagingRawMaterials().stream().map((item) -> mapper.map(item, Item.class)).collect(Collectors.toList());
            supplierInDb.setManagingRawMaterials(rawMaterials);
        }
        Supplier updatedSupplier = supplierRepository.save(supplierInDb);
        return mapper.map(updatedSupplier, SupplierDTO.class);
    }

    /**
     * Deletes a supplier only if the supplier is not managing any raw material at the moment
     *
     * @param id The id of the supplier to delete
     * @return Boolean to indicate if deleted or not
     * @throws ResourceCannotBeDeletedException Thrown if supplier is managing raw materials
     * @throws ResourceNotFoundException        Thrown if supplier does not exist.
     */
    @Override
    public boolean deleteSupplierById(Long id) throws ResourceCannotBeDeletedException, ResourceNotFoundException {
        Optional<Supplier> supplierToDelete = supplierRepository.findById(id);
        if (supplierToDelete.isEmpty()) {
            throw new ResourceNotFoundException("The supplier does not exist");
        }
        Supplier supplier = supplierToDelete.get();
        List<Item> itemsManagedBySupplier = supplier.getManagingRawMaterials();

        if (itemsManagedBySupplier.isEmpty()) {
            supplierRepository.delete(supplier);
            return true;
        }
        throw new ResourceCannotBeDeletedException("This supplier has items that are currently managed. Assign these items to a new supplier to remove this supplier");
    }
}
