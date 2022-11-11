package com.esad.supply_chain_management.util.item_creator;

import com.esad.supply_chain_management.dto.ItemDTO;
import com.esad.supply_chain_management.dto.RawMaterialDTO;
import com.esad.supply_chain_management.exceptions.ResourceNotCreatedException;
import com.esad.supply_chain_management.exceptions.ResourceNotFoundException;
import com.esad.supply_chain_management.model.RawMaterial;
import com.esad.supply_chain_management.model.Supplier;
import com.esad.supply_chain_management.repository.RawMaterialRepository;
import com.esad.supply_chain_management.repository.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service(value = "rawMaterialCreator")
public class RawMaterialCreator extends ItemCreator {
    private SupplierRepository supplierRepository;
    private RawMaterialRepository rawMaterialRepository;
    private ModelMapper mapper;

    @Autowired
    public RawMaterialCreator(SupplierRepository supplierRepository, RawMaterialRepository rawMaterialRepository, ModelMapper mapper) {
        this.supplierRepository = supplierRepository;
        this.rawMaterialRepository = rawMaterialRepository;
        this.mapper = mapper;
    }

    @Override
    public ItemDTO createItem(ItemDTO itemToCreate, HashMap<String, Object> optionalArgs) throws ResourceNotCreatedException, ResourceNotFoundException {
        // validate item comes into create item due to template method steps., so can directly add.
        // map from DTO to class
        RawMaterial persistingObject = mapper.map(itemToCreate, RawMaterial.class);
        Long supplierId = (Long) optionalArgs.get("supplierId");
        Optional<Supplier> supplierResp = supplierRepository.findById(supplierId);
        if (supplierResp.isEmpty()) {
            // the supplier passed from client does not exist in the database, hence throw error.
            throw new ResourceNotFoundException("Supplier does not exist");
        }
        persistingObject.setSupplier((supplierResp.get()));
        RawMaterial createdRawMaterial = rawMaterialRepository.save(persistingObject);
        return mapper.map(createdRawMaterial, RawMaterialDTO.class);
    }
}
