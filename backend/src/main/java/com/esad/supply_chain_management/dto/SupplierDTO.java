package com.esad.supply_chain_management.dto;

import java.util.List;

public class SupplierDTO {
    private Long id;

    private String name;

    private String email;

    private List<ItemDTO> managingRawMaterials;

    public SupplierDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ItemDTO> getManagingRawMaterials() {
        return managingRawMaterials;
    }

    public void setManagingRawMaterials(List<ItemDTO> managingRawMaterials) {
        this.managingRawMaterials = managingRawMaterials;
    }
}
