package com.esad.supply_chain_management.service;

import com.esad.supply_chain_management.dto.TruckDTO;
import com.esad.supply_chain_management.exceptions.ResourceExistsException;
import com.esad.supply_chain_management.exceptions.ResourceNotFoundException;
import com.esad.supply_chain_management.model.Truck;
import com.esad.supply_chain_management.repository.TruckRepository;
import com.esad.supply_chain_management.repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TruckServiceImpl implements TruckService {
    private VehicleRepository vehicleRepository;
    private TruckRepository truckRepository;
    private ModelMapper mapper;

    @Autowired
    public TruckServiceImpl(VehicleRepository vehicleRepository, TruckRepository truckRepository, ModelMapper mapper) {
        this.vehicleRepository = vehicleRepository;
        this.truckRepository = truckRepository;
        this.mapper = mapper;
    }

    /**
     * Creates a truck if the license and chassis number is unique
     *
     * @param createTruck The truck attributes
     * @return The created truck
     * @throws ResourceExistsException thrown if license plate or chassis number exists
     */
    @Override
    public TruckDTO createTruck(TruckDTO createTruck) throws ResourceExistsException {
        String licensePlate = createTruck.getLicensePlate().toLowerCase(Locale.ROOT);
        String chassisNumber = createTruck.getChassisNumber().toLowerCase(Locale.ROOT);

        if (vehicleRepository.findByChassisNumber(chassisNumber).isPresent()) {
            throw new ResourceExistsException("Vehicle with same chassis number exists");
        }

        if (vehicleRepository.findByLicensePlate(licensePlate).isPresent()) {
            throw new ResourceExistsException("Vehicle with same license plate exists");
        }

        Truck truckToPersist = mapper.map(createTruck, Truck.class);
        truckToPersist.setLicensePlate(licensePlate);
        truckToPersist.setChassisNumber(chassisNumber);

        Truck persistedTruck = truckRepository.save(truckToPersist);
        return mapper.map(persistedTruck, TruckDTO.class);
    }

    /**
     * Returns a list of all the trucks present in the system
     *
     * @return The list of trucks
     */
    @Override
    public List<TruckDTO> getAllTrucks() {
        return truckRepository
                .findAll()
                .stream()
                .map((truck -> mapper.map(truck, TruckDTO.class)))
                .collect(Collectors.toList());
    }

    /**
     * Updates the truck based on the values passed.
     *
     * @param patchAttr The values to patch
     * @param id        The id of the truck
     * @return The updated truck
     * @throws ResourceNotFoundException thrown if the truck does not exist
     */
    @Override
    public TruckDTO updateTruck(TruckDTO patchAttr, Long id) throws ResourceNotFoundException {
        Truck truckToUpdate = truckRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Truck does not exist"));

        if (patchAttr.getMaxPackageCapacity() != 0) {
            truckToUpdate.setMaxPackageCapacity(patchAttr.getMaxPackageCapacity());
        }

        if (!Objects.isNull(patchAttr.getName())) {
            truckToUpdate.setName(patchAttr.getName().trim());
        }

        Truck updatedTruck = truckRepository.save(truckToUpdate);
        return mapper.map(updatedTruck, TruckDTO.class);
    }
}
