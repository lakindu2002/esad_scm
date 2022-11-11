package com.esad.supply_chain_management.dto;

public class RawMaterialProductsDTO {
    private Long id;
    private RawMaterialDTO rawMaterial;
    private int quantityOfRawMaterialUsed;

    public RawMaterialProductsDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RawMaterialDTO getRawMaterial() {
        return rawMaterial;
    }

    public void setRawMaterial(RawMaterialDTO rawMaterial) {
        this.rawMaterial = rawMaterial;
    }

    public int getQuantityOfRawMaterialUsed() {
        return quantityOfRawMaterialUsed;
    }

    public void setQuantityOfRawMaterialUsed(int quantityOfRawMaterialUsed) {
        this.quantityOfRawMaterialUsed = quantityOfRawMaterialUsed;
    }
}
