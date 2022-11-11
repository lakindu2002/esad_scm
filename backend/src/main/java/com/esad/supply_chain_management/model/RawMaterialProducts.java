package com.esad.supply_chain_management.model;

import javax.persistence.*;

@Entity
public class RawMaterialProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "material_id", nullable = false)
    private RawMaterial rawMaterial;

    @Column
    private int quantityOfRawMaterialUsed;

    public RawMaterialProducts() {
    }

    public int getQuantityOfRawMaterialUsed() {
        return quantityOfRawMaterialUsed;
    }

    public void setQuantityOfRawMaterialUsed(int quantityOfRawMaterialUsed) {
        this.quantityOfRawMaterialUsed = quantityOfRawMaterialUsed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public RawMaterial getRawMaterial() {
        return rawMaterial;
    }

    public void setRawMaterial(RawMaterial rawMaterial) {
        this.rawMaterial = rawMaterial;
    }
}
