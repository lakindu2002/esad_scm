package com.esad.supply_chain_management.model;

import javax.persistence.*;
import java.util.List;

@Entity(name = "raw_material")
public class RawMaterial extends Item {
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Supplier supplier;

    @OneToMany(mappedBy = "rawMaterial", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<RawMaterialProducts> productsList;

    public RawMaterial() {
        super();
    }

    public List<RawMaterialProducts> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<RawMaterialProducts> productsList) {
        this.productsList = productsList;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "RawMaterial{" +
                "supplier=" + supplier +
                '}';
    }
}
