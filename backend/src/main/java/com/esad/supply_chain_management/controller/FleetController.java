package com.esad.supply_chain_management.controller;

import com.esad.supply_chain_management.dto.CarDTO;
import com.esad.supply_chain_management.dto.TruckDTO;
import com.esad.supply_chain_management.dto.VanDTO;
import com.esad.supply_chain_management.exceptions.ResourceExistsException;
import com.esad.supply_chain_management.exceptions.ResourceNotFoundException;
import com.esad.supply_chain_management.service.CarService;
import com.esad.supply_chain_management.service.TruckService;
import com.esad.supply_chain_management.service.VanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "/api/vehicles") // base endpoint as = "/api/vehicles" in this controller so all other endpoints will have this prepended.
public class FleetController {
    private CarService carService;
    private VanService vanService;
    private TruckService truckService;

    /**
     * Use of dependency injection via @Autowired to let the IOC Container inject instances.
     */
    @Autowired
    public FleetController(CarService carService, VanService vanService, TruckService truckService) {
        this.carService = carService;
        this.vanService = vanService;
        this.truckService = truckService;
    }

    @GetMapping("/cars")
    public ResponseEntity<List<CarDTO>> getAllCars() {
        // service method that retrieves all the cars in the database and returns with 200
        List<CarDTO> allCars = carService.getAllCars();
        return ResponseEntity.ok(allCars);
    }

    @GetMapping("/trucks")
    public ResponseEntity<List<TruckDTO>> getAllTrucks() {
        // service method that retrieves all the trucks in the database and returns with 200
        List<TruckDTO> allTrucks = truckService.getAllTrucks();
        return ResponseEntity.ok(allTrucks);
    }

    @GetMapping("/vans")
    public ResponseEntity<List<VanDTO>> getAllVans() {
        // service method that retrieves all the vanc in the database and returns with 200
        List<VanDTO> allVans = vanService.getAllVans();
        return ResponseEntity.ok(allVans);
    }

    @PostMapping("/cars/create")
    public ResponseEntity<CarDTO> createCar(@RequestBody CarDTO createAttributes) throws ResourceExistsException {
        // creates a car using the request body passed from client.
        CarDTO createdCar = carService.createCar(createAttributes);
        return ResponseEntity.ok(createdCar); // return created car with ID back to client.
    }

    @PostMapping("/vans/create")
    public ResponseEntity<VanDTO> createVan(@RequestBody VanDTO createAttributes) throws ResourceExistsException {
        // creates a van using request body
        VanDTO createdVan = vanService.createVan(createAttributes);
        return ResponseEntity.ok(createdVan); // return created van
    }

    @PostMapping("/trucks/create")
    public ResponseEntity<TruckDTO> createTruck(@RequestBody TruckDTO createAttributes) throws ResourceExistsException {
        TruckDTO createdTruck = truckService.createTruck(createAttributes); // creates a truck
        return ResponseEntity.ok(createdTruck); // return created truck
    }

    @PatchMapping("/cars/{id}")
    public ResponseEntity<CarDTO> updateCar(@RequestBody CarDTO patchAttr, @PathVariable("id") Long id) throws ResourceNotFoundException {
        CarDTO updatedCar = carService.updateCar(patchAttr, id); // update car by ID
        return ResponseEntity.ok(updatedCar); // return updated car from db
    }

    @PatchMapping("/vans/{id}")
    public ResponseEntity<VanDTO> updateVan(@RequestBody VanDTO patchAttr, @PathVariable("id") Long id) throws ResourceNotFoundException {
        VanDTO updatedVan = vanService.updateVan(patchAttr, id); // update van by id
        return ResponseEntity.ok(updatedVan); // return updated van by id
    }

    @PatchMapping("/trucks/{id}")
    public ResponseEntity<TruckDTO> updateTruck(@RequestBody TruckDTO patchAttr, @PathVariable("id") Long id) throws ResourceNotFoundException {
        TruckDTO updatedTruck = truckService.updateTruck(patchAttr, id); // update truck by id using attributes passed from client
        return ResponseEntity.ok(updatedTruck); // return updated van
    }
}
