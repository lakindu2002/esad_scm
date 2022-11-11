package com.esad.supply_chain_management.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "supplier_name")
    private String name;

    @Column(name = "supplier_email")
    private String email;


    @OneToMany(fetch = FetchType.LAZY)
    // item table has supplier id that references this tables "id"
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    private List<Item> managingRawMaterials;

    public Supplier() {
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

    public List<Item> getManagingRawMaterials() {
        return managingRawMaterials;
    }

    public void setManagingRawMaterials(List<Item> managingRawMaterials) {
        this.managingRawMaterials = managingRawMaterials;
    }


    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", managingRawMaterials=" + managingRawMaterials +
                '}';
    }
}
