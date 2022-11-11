package com.esad.supply_chain_management.controller;

import com.esad.supply_chain_management.dto.RawMaterialDTO;
import com.esad.supply_chain_management.exceptions.ResourceNotCreatedException;
import com.esad.supply_chain_management.exceptions.ResourceNotFoundException;
import com.esad.supply_chain_management.service.RawMaterialService;
import com.esad.supply_chain_management.util.item_creator.ItemCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/api/raw-materials")
public class RawMaterialController {
    private RawMaterialService rawMaterialService;
    private ItemCreator itemCreator;

    // @Qualifier lets specify the name of the bean to inject into this class from IOC Container.
    // here instance of "rawMaterialCreator" bean is injected as ItemCreator for Template Initialization.
    // this helps create raw materials.
    @Autowired
    public RawMaterialController(RawMaterialService rawMaterialService, @Qualifier(value = "rawMaterialCreator") ItemCreator itemCreator) {
        this.rawMaterialService = rawMaterialService;
        this.itemCreator = itemCreator;
    }

    @GetMapping
    public ResponseEntity<List<RawMaterialDTO>> getAllRawMaterials() {
        // return all raw materials
        List<RawMaterialDTO> rawMaterialDTOList = rawMaterialService.getAllRawMaterials();
        return ResponseEntity.ok(rawMaterialDTOList);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<RawMaterialDTO> createRawMaterial(@RequestBody RawMaterialDTO material) throws ResourceNotCreatedException, ResourceNotFoundException {
        HashMap<String, Object> args = new HashMap<>();
        args.put("supplierId", material.getSupplier().getId());
        // creates the raw material using the template method that defines the required steps.
        RawMaterialDTO createdItem = (RawMaterialDTO) itemCreator.processItemCreation(material, args);
        return ResponseEntity.ok(createdItem);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<RawMaterialDTO> updateRawMaterial(@RequestBody RawMaterialDTO patchAttr, @PathVariable(name = "id") Long rawMaterialId) throws ResourceNotFoundException {
        // updates a raw material by id
        RawMaterialDTO updatedObject = rawMaterialService.updateRawMaterial(patchAttr, rawMaterialId);
        return ResponseEntity.ok(updatedObject);
    }

    @GetMapping(path = "/sort/ascending")
    public ResponseEntity<List<RawMaterialDTO>> sortAscending() {
        // returns a list of all raw materials sorted in ascending order of threshold quantity
        List<RawMaterialDTO> sortedItems = rawMaterialService.performAscendingSort();
        return ResponseEntity.ok(sortedItems);
    }

    @GetMapping(path = "/sort/descending")
    public ResponseEntity<List<RawMaterialDTO>> sortDescending() {
        // returns a list of all raw materials sorted in descending order of threshold quantity
        List<RawMaterialDTO> sortedItems = rawMaterialService.performDescendingSort();
        return ResponseEntity.ok(sortedItems);
    }
}
