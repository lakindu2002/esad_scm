package com.esad.supply_chain_management.util.raw_material_sorter;

import com.esad.supply_chain_management.dto.RawMaterialDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class SortStrategy {
    private final Map<StrategyName, ISort> theStrategiesAvailable;
    private ISort sorter;

    // due to IOC, strategies will get created by spring due to @Component
    // hence map will get all instanced of ISort created by spring.
    @Autowired
    private SortStrategy(Set<ISort> definedStrategies) {
        theStrategiesAvailable = new HashMap<>();
        // add each strategy to a hashmap with key as type and value as actual object
        definedStrategies.forEach((strategy) -> {
            theStrategiesAvailable.put(strategy.getSortStrategyType(), strategy);
        });
    }

    // used to set the strategy in the runtime based on the strategy name
    public void setSorter(StrategyName strategyToUse) {
        this.sorter = theStrategiesAvailable.get(strategyToUse);
    }

    // the strategy consumer method.
    public List<RawMaterialDTO> performSort() {
        return sorter.sort();
    }
}
