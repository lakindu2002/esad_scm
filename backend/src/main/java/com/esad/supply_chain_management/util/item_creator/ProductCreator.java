package com.esad.supply_chain_management.util.item_creator;

import com.esad.supply_chain_management.dto.ItemDTO;
import com.esad.supply_chain_management.dto.ProductDTO;
import com.esad.supply_chain_management.exceptions.ResourceNotCreatedException;
import com.esad.supply_chain_management.model.Product;
import com.esad.supply_chain_management.model.RawMaterial;
import com.esad.supply_chain_management.model.RawMaterialProducts;
import com.esad.supply_chain_management.repository.ProductRepository;
import com.esad.supply_chain_management.repository.RawMaterialRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Product creator that is used to create products.
 */
@Service(value = "productCreator")
public class ProductCreator extends ItemCreator {
    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;
    private ModelMapper mapper;

    // inject all requried dependencies via Spring IOC COntainer.
    @Autowired
    public ProductCreator(ProductRepository productRepository, RawMaterialRepository rawMaterialRepository, ModelMapper mapper) {
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
        this.mapper = mapper;
    }

    @Override
    public ItemDTO createItem(ItemDTO itemToCreate, HashMap<String, Object> optionalArgs) throws ResourceNotCreatedException {
        // validate item comes into create item due to template method steps., so can directly add.
        if (!(itemToCreate instanceof ProductDTO productToCreate)) {
            throw new ResourceNotCreatedException("The item is not a valid instance of Product");
        }
        if (productToCreate.getRawMaterials().size() == 0) {
            throw new ResourceNotCreatedException("The raw materials are not provided");
        }
        Product productToSave = mapper.map(productToCreate, Product.class);
        // update quantity in raw material.
        List<RawMaterialProducts> materialsUsed = productToSave.getRawMaterials();

        List<RawMaterial> updatedQuantityMaterialList = materialsUsed.stream().map((rawMaterialProducts -> {
            RawMaterial materialUsed = rawMaterialProducts.getRawMaterial();
            // use quantity will update available stock with the consumed stock to create the product.
            materialUsed.useQuantity(rawMaterialProducts.getQuantityOfRawMaterialUsed());
            rawMaterialProducts.setProduct(productToSave);
            rawMaterialProducts.setRawMaterial(materialUsed);
            return materialUsed;
        })).collect(Collectors.toList());
        rawMaterialRepository.saveAll(updatedQuantityMaterialList);
        Product createdProduct = productRepository.save(productToSave);
        return mapper.map(createdProduct, ProductDTO.class);
    }
}
