package com.esad.supply_chain_management.service;

import com.esad.supply_chain_management.dto.ProductDTO;
import com.esad.supply_chain_management.exceptions.ResourceNotFoundException;
import com.esad.supply_chain_management.model.Product;
import com.esad.supply_chain_management.repository.ProductRepository;
import com.esad.supply_chain_management.repository.RawMaterialRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private RawMaterialRepository rawMaterialRepository;
    private ProductRepository productRepository;
    private ModelMapper mapper;

    @Autowired
    public ProductServiceImpl(RawMaterialRepository rawMaterialRepository, ProductRepository productRepository, ModelMapper mapper) {
        this.rawMaterialRepository = rawMaterialRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    /**
     * Returns a list of all the products in the system
     *
     * @return list of all products
     */
    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> allProductsInDb = productRepository.findAll();
        return allProductsInDb.stream().map((product -> mapper.map(product, ProductDTO.class))).collect(Collectors.toList());
    }

    /**
     * Updates the products based on the patch attributes passed
     *
     * @param patchAttr The attributes to updated
     * @param id        The Id to of the product to update
     * @return The updated product
     * @throws ResourceNotFoundException thrown when product is not found
     */
    @Override
    public ProductDTO updateProduct(ProductDTO patchAttr, Long id) throws ResourceNotFoundException {
        // quantity used is not updated as once manufactured, no point in updating it again.
        // if new stock needed, will be added as new entry under different id.
        Optional<Product> dbResp = productRepository.findById(id);
        if (dbResp.isEmpty()) {
            throw new ResourceNotFoundException("The product does not exist");
        }
        Product productToUpdate = dbResp.get();
        if (Objects.isNull(patchAttr.getName())) {
            productToUpdate.setName(patchAttr.getName());
        }
        if (patchAttr.getQuantity() != 0) {
            productToUpdate.setQuantity(patchAttr.getQuantity());
        }
        if (patchAttr.getPrice() != 0) {
            productToUpdate.setPrice(patchAttr.getPrice());
        }
        if (patchAttr.getThresholdQuantity() != 0) {
            productToUpdate.setThresholdQuantity(patchAttr.getThresholdQuantity());
        }
        Product updatedProduct = productRepository.save(productToUpdate);
        return mapper.map(updatedProduct, ProductDTO.class);
    }
}
