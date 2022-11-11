package com.esad.supply_chain_management.service;

import com.esad.supply_chain_management.dto.CarDTO;
import com.esad.supply_chain_management.exceptions.ResourceExistsException;
import com.esad.supply_chain_management.exceptions.ResourceNotFoundException;
import com.esad.supply_chain_management.model.Car;
import com.esad.supply_chain_management.repository.CarRepository;
import com.esad.supply_chain_management.repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private CarRepository carRepository;
    private VehicleRepository vehicleRepository;
    private ModelMapper mapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, VehicleRepository vehicleRepository, ModelMapper mapper) {
        this.carRepository = carRepository;
        this.vehicleRepository = vehicleRepository;
        this.mapper = mapper;
    }

    /**
     * Creates a car if the license and chassis number is unique
     *
     * @param createAttributes The car attributes
     * @return The created car
     * @throws ResourceExistsException thrown if license plate or chassis number exists
     */
    @Override
    public CarDTO createCar(CarDTO createAttributes) throws ResourceExistsException {
        String licensePlate = createAttributes.getLicensePlate().toLowerCase(Locale.ROOT);
        String chassisNumber = createAttributes.getChassisNumber().toLowerCase(Locale.ROOT);

        if (vehicleRepository.findByChassisNumber(chassisNumber).isPresent()) {
            throw new ResourceExistsException("Vehicle with same chassis number exists");
        }

        if (vehicleRepository.findByLicensePlate(licensePlate).isPresent()) {
            throw new ResourceExistsException("Vehicle with same license plate exists");
        }

        Car carToPersist = mapper.map(createAttributes, Car.class);
        carToPersist.setLicensePlate(licensePlate);
        carToPersist.setChassisNumber(chassisNumber);

        Car persistedCar = carRepository.save(carToPersist);
        return mapper.map(persistedCar, CarDTO.class);
    }

    /**
     * Returns a list of all the cars present in the system
     *
     * @return The list of cars
     */
    @Override
    public List<CarDTO> getAllCars() {
        List<Car> allCarsInDb = carRepository.findAll();
        return allCarsInDb.stream().map(car -> mapper.map(car, CarDTO.class)).collect(Collectors.toList());
    }

    /**
     * Updates the car based on the values passed.
     *
     * @param patchAttr The values to patch
     * @param id        The id of the car
     * @return The updated car
     * @throws ResourceNotFoundException thrown if the car does not exist
     */
    @Override
    public CarDTO updateCar(CarDTO patchAttr, Long id) throws ResourceNotFoundException {
        Car carToUpdate = carRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Car does not exist"));

        if (patchAttr.getMaxPackageCapacity() != 0) {
            carToUpdate.setMaxPackageCapacity(patchAttr.getMaxPackageCapacity());
        }

        if (!Objects.isNull(patchAttr.getName())) {
            carToUpdate.setName(patchAttr.getName().trim());
        }

        Car updatedCar = carRepository.save(carToUpdate);
        return mapper.map(updatedCar, CarDTO.class);
    }
}
