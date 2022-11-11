package com.esad.supply_chain_management.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Product extends Item {

    @OneToMany(mappedBy = "product", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<RawMaterialProducts> rawMaterials;

    public List<RawMaterialProducts> getRawMaterials() {
        return rawMaterials;
    }

    public void setRawMaterials(List<RawMaterialProducts> rawMaterials) {
        this.rawMaterials = rawMaterials;
    }

    public Product() {
        super();
    }
}
