package com.esad.supply_chain_management.util.raw_material_sorter;

import com.esad.supply_chain_management.dto.RawMaterialDTO;

import java.util.List;

public interface ISort {
    /**
     * The sorting function that will be implemented based on the type.
     *
     * @return The sorted raw materials
     */
    List<RawMaterialDTO> sort();

    /**
     * Returns the name of the sorting strategy
     *
     * @return The sorting strategy.
     */
    public StrategyName getSortStrategyType();
}
