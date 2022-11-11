package com.esad.supply_chain_management.controller;

import com.esad.supply_chain_management.dto.ProductDTO;
import com.esad.supply_chain_management.exceptions.ResourceNotCreatedException;
import com.esad.supply_chain_management.exceptions.ResourceNotFoundException;
import com.esad.supply_chain_management.service.ProductService;
import com.esad.supply_chain_management.util.item_creator.ItemCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/products")
public class ProductController {
    private ProductService productService;
    private ItemCreator itemCreator;

    // @Qualifier lets specify the name of the bean to inject into this class from IOC Container.
    // here instance of "productCreator" bean is injected as ItemCreator for Template Initialization.
    @Autowired
    public ProductController(ProductService productService, @Qualifier("productCreator") ItemCreator itemCreator) {
        this.productService = productService;
        this.itemCreator = itemCreator;
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> productList = productService.getAllProducts();
        return ResponseEntity.ok(productList);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO body) throws ResourceNotCreatedException, ResourceNotFoundException {
        // invoke the template method "processItemCreation"
        // the template method has a set of steps declared including validation and creation.
        // these two processes get executed in the template, and returns a created item.
        ProductDTO createdProduct = (ProductDTO) itemCreator.processItemCreation(body, null);
        return ResponseEntity.ok(createdProduct);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO patchAttr, @PathVariable("id") Long id) throws ResourceNotFoundException {
        ProductDTO updatedResponse = productService.updateProduct(patchAttr, id); // updates a product by id.
        return ResponseEntity.ok(updatedResponse);
    }
}
