package com.esad.supply_chain_management.service;

import com.esad.supply_chain_management.dto.ProductDTO;
import com.esad.supply_chain_management.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ProductService {
    /**
     * Returns a list of all the products in the system
     *
     * @return list of all products
     */
    List<ProductDTO> getAllProducts();

    /**
     * Updates the products based on the patch attributes passed
     *
     * @param patchAttr The attributes to updated
     * @param id        The Id to of the product to update
     * @return The updated product
     * @throws ResourceNotFoundException thrown when product is not found
     */
    ProductDTO updateProduct(ProductDTO patchAttr, Long id) throws ResourceNotFoundException;
}
