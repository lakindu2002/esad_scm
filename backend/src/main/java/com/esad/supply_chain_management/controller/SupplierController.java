package com.esad.supply_chain_management.controller;

import com.esad.supply_chain_management.dto.CustomDTO;
import com.esad.supply_chain_management.dto.SupplierDTO;
import com.esad.supply_chain_management.exceptions.ResourceCannotBeDeletedException;
import com.esad.supply_chain_management.exceptions.ResourceExistsException;
import com.esad.supply_chain_management.exceptions.ResourceNotFoundException;
import com.esad.supply_chain_management.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "/api/suppliers")
public class SupplierController {
    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<SupplierDTO> createSupplier(@RequestBody(required = true) SupplierDTO supplier) throws ResourceExistsException {
        // method will create a supplier
        SupplierDTO createdSupplier = supplierService.createSupplier(supplier);
        return new ResponseEntity<>(createdSupplier, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        // method will get all suppliers
        List<SupplierDTO> suppliers = supplierService.getAllSuppliers();
        return new ResponseEntity<>(suppliers, HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<SupplierDTO> updateSupplier(@RequestBody(required = true) SupplierDTO patchAttrs, @PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        // method will update suppliers by ID
        SupplierDTO updatedSupplier = supplierService.updateSupplier(patchAttrs, id);
        return new ResponseEntity<>(updatedSupplier, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CustomDTO> deleteSupplier(@PathVariable(value = "id") Long id) throws ResourceCannotBeDeletedException, ResourceNotFoundException {
        supplierService.deleteSupplierById(id); // will delete supplier by id only if supplier does not manage any raw materials.
        CustomDTO dto = new CustomDTO(200, "Supplier Deleted");
        return ResponseEntity.ok(dto);
    }
}
