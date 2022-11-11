package com.esad.supply_chain_management.dto;

import java.util.List;

public class ProductDTO extends ItemDTO {

    private List<RawMaterialProductsDTO> rawMaterials;

    public List<RawMaterialProductsDTO> getRawMaterials() {
        return rawMaterials;
    }

    public void setRawMaterials(List<RawMaterialProductsDTO> rawMaterials) {
        this.rawMaterials = rawMaterials;
    }

    public ProductDTO() {
        super();
    }
}
