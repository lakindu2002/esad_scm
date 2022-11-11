package com.esad.supply_chain_management.dto;

import java.util.List;

public class RawMaterialDTO extends ItemDTO {
    private SupplierDTO supplier;

    public SupplierDTO getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDTO supplier) {
        this.supplier = supplier;
    }

    public RawMaterialDTO() {
        super();
    }
}
