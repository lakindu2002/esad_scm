package com.esad.supply_chain_management.util.raw_material_sorter;

import com.esad.supply_chain_management.dto.RawMaterialDTO;
import com.esad.supply_chain_management.repository.RawMaterialRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AscendingSorter implements ISort {
    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private ModelMapper mapper;


    /**
     * The sorting function that will be implemented based on the type.
     *
     * @return The sorted raw materials
     */
    @Override
    public List<RawMaterialDTO> sort() {
        return rawMaterialRepository
                .findAll(Sort.by(Sort.Direction.ASC, "thresholdQuantity"))
                .stream().map((item) -> mapper.map(item, RawMaterialDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Returns the name of the sorting strategy
     *
     * @return The sorting strategy.
     */
    @Override
    public StrategyName getSortStrategyType() {
        return StrategyName.ASCENDING;
    }
}
