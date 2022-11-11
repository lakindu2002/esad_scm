package com.esad.supply_chain_management.service;

import com.esad.supply_chain_management.dto.RawMaterialDTO;
import com.esad.supply_chain_management.exceptions.ResourceNotFoundException;
import com.esad.supply_chain_management.model.RawMaterial;
import com.esad.supply_chain_management.model.Supplier;
import com.esad.supply_chain_management.repository.RawMaterialRepository;
import com.esad.supply_chain_management.repository.SupplierRepository;
import com.esad.supply_chain_management.util.raw_material_sorter.SortStrategy;
import com.esad.supply_chain_management.util.raw_material_sorter.StrategyName;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RawMaterialServiceImpl implements RawMaterialService {

    private SupplierRepository supplierRepository;
    private RawMaterialRepository rawMaterialRepository;
    private ModelMapper mapper;
    private SortStrategy strategy;

    @Autowired
    public RawMaterialServiceImpl(SupplierRepository supplierRepository, RawMaterialRepository rawMaterialRepository, ModelMapper mapper, SortStrategy strategy) {
        this.supplierRepository = supplierRepository;
        this.rawMaterialRepository = rawMaterialRepository;
        this.mapper = mapper;
        this.strategy = strategy;
    }

    /**
     * Returns a list of all raw materials
     *
     * @return The raw materials to return
     */
    @Override
    public List<RawMaterialDTO> getAllRawMaterials() {
        List<RawMaterial> materialsInDb = rawMaterialRepository.findAll();
        List<RawMaterialDTO> dtoList = materialsInDb.stream().map((rawMaterial -> mapper.map(rawMaterial, RawMaterialDTO.class))).collect(Collectors.toList());
        return dtoList;
    }

    /**
     * Updates the raw material based on the patch attributes passed
     *
     * @param patchAttr The attributes to updated
     * @param id        The Id to of the raw material to update
     * @return The raw material
     * @throws ResourceNotFoundException thrown when raw material is not found
     */
    @Override
    public RawMaterialDTO updateRawMaterial(RawMaterialDTO patchAttr, Long id) throws ResourceNotFoundException {
        Optional<RawMaterial> response = rawMaterialRepository.findById(id);
        if (response.isEmpty()) {
            throw new ResourceNotFoundException("The raw material does not exist");
        }
        RawMaterial itemInDb = response.get();
        if (patchAttr.getPrice() != 0) {
            itemInDb.setPrice(patchAttr.getPrice());
        }
        if (patchAttr.getThresholdQuantity() != 0) {
            itemInDb.setThresholdQuantity(patchAttr.getThresholdQuantity());
        }
        if (!Objects.isNull(patchAttr.getName())) {
            itemInDb.setName(patchAttr.getName());
        }
        if (patchAttr.getQuantity() != 0) {
            itemInDb.setQuantity(patchAttr.getQuantity());
        }
        if (!Objects.isNull(patchAttr.getSupplier())) {
            Optional<Supplier> supplierResp = supplierRepository.findById(patchAttr.getSupplier().getId());
            if (supplierResp.isEmpty()) {
                throw new ResourceNotFoundException("Supplier does not exist");
            }
            itemInDb.setSupplier(supplierResp.get());
        }
        RawMaterial updatedEntity = rawMaterialRepository.save(itemInDb);
        return mapper.map(updatedEntity, RawMaterialDTO.class);
    }

    /**
     * Sort the raw materials in ascending order of the threshold quantity
     *
     * @return The sorted raw materials
     */
    @Override
    public List<RawMaterialDTO> performAscendingSort() {
        strategy.setSorter(StrategyName.ASCENDING);
        return strategy.performSort();
    }

    /**
     * Sort the raw materials in descending order of the threshold quantity
     *
     * @return The sorted raw materials
     */
    @Override
    public List<RawMaterialDTO> performDescendingSort() {
        strategy.setSorter(StrategyName.DESCENDING);
        return strategy.performSort();
    }
}
