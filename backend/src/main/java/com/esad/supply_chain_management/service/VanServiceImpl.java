package com.esad.supply_chain_management.service;

import com.esad.supply_chain_management.dto.VanDTO;
import com.esad.supply_chain_management.exceptions.ResourceExistsException;
import com.esad.supply_chain_management.exceptions.ResourceNotFoundException;
import com.esad.supply_chain_management.model.Van;
import com.esad.supply_chain_management.repository.VanRepository;
import com.esad.supply_chain_management.repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VanServiceImpl implements VanService {
    private VehicleRepository vehicleRepository;
    private VanRepository vanRepository;
    private ModelMapper mapper;

    @Autowired
    public VanServiceImpl(VehicleRepository vehicleRepository, VanRepository vanRepository, ModelMapper mapper) {
        this.vehicleRepository = vehicleRepository;
        this.vanRepository = vanRepository;
        this.mapper = mapper;
    }

    /**
     * Creates a van if the license and chassis number is unique
     *
     * @param createAttr The van attributes
     * @return The created van
     * @throws ResourceExistsException thrown if license plate or chassis number exists
     */
    @Override
    public VanDTO createVan(VanDTO createAttr) throws ResourceExistsException {
        String licensePlate = createAttr.getLicensePlate().toLowerCase(Locale.ROOT);
        String chassisNumber = createAttr.getChassisNumber().toLowerCase(Locale.ROOT);

        if (vehicleRepository.findByChassisNumber(chassisNumber).isPresent()) {
            throw new ResourceExistsException("Vehicle with same chassis number exists");
        }

        if (vehicleRepository.findByLicensePlate(licensePlate).isPresent()) {
            throw new ResourceExistsException("Vehicle with same license plate exists");
        }

        Van vanToPersist = mapper.map(createAttr, Van.class);
        vanToPersist.setLicensePlate(licensePlate);
        vanToPersist.setChassisNumber(chassisNumber);

        Van persistedVan = vanRepository.save(vanToPersist);
        return mapper.map(persistedVan, VanDTO.class);
    }

    /**
     * Returns a list of all the vans present in the system
     *
     * @return The list of vans
     */
    @Override
    public List<VanDTO> getAllVans() {
        return vanRepository
                .findAll()
                .stream()
                .map((van -> mapper.map(van, VanDTO.class)))
                .collect(Collectors.toList());
    }

    /**
     * Updates the van based on the values passed.
     *
     * @param patchAttr The values to patch
     * @param id        The id of the van
     * @return The updated van
     * @throws ResourceNotFoundException thrown if the van does not exist
     */
    @Override
    public VanDTO updateVan(VanDTO patchAttr, Long id) throws ResourceNotFoundException {
        Van vanToUpdate = vanRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Van does not exist"));

        if (patchAttr.getMaxPackageCapacity() != 0) {
            vanToUpdate.setMaxPackageCapacity(patchAttr.getMaxPackageCapacity());
        }

        if (!Objects.isNull(patchAttr.getName())) {
            vanToUpdate.setName(patchAttr.getName().trim());
        }


        Van updatedVan = vanRepository.save(vanToUpdate);
        return mapper.map(updatedVan, VanDTO.class);
    }
}
