package com.esad.supply_chain_management.dto;

public abstract class VehicleDTO {
    private Long id;

    private String licensePlate;

    private String chassisNumber;

    private int maxPackageCapacity;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VehicleDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public int getMaxPackageCapacity() {
        return maxPackageCapacity;
    }

    public void setMaxPackageCapacity(int maxPackageCapacity) {
        this.maxPackageCapacity = maxPackageCapacity;
    }
}
