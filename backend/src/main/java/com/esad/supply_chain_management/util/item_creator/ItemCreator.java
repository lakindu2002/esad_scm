package com.esad.supply_chain_management.util.item_creator;

import com.esad.supply_chain_management.dto.ItemDTO;
import com.esad.supply_chain_management.exceptions.ResourceNotCreatedException;
import com.esad.supply_chain_management.exceptions.ResourceNotFoundException;

import java.util.HashMap;

/**
 * The abstract class that defined the template properties for the Template Pattern
 * This is used when a raw material or product is created in the system.
 */
public abstract class ItemCreator {
    /**
     * A method that validates the provided inputs when creating the item
     *
     * @param itemToCreate The item to create
     * @return A boolean indicating if object is validated or not
     * @throws ResourceNotCreatedException Exception thrown is there are validation errors.
     */
    public boolean validateItem(ItemDTO itemToCreate) throws ResourceNotCreatedException {
        if (itemToCreate.getPrice() == 0) {
            throw new ResourceNotCreatedException("The price cannot be 0");
        }
        if (itemToCreate.getQuantity() == 0) {
            throw new ResourceNotCreatedException("The quantity cannot be 0 upon creation");
        }
        if (itemToCreate.getThresholdQuantity() == 0) {
            throw new ResourceNotCreatedException("The threshold quantity cannot be 0 upon creation");
        }
        return true;
    }

    /**
     * The template method marked `final` to prevent overriding its implementation
     *
     * @param itemToCreate The item to create
     * @param optionalArgs the optional arguements to be passed along the chain
     * @return An item
     * @throws ResourceNotCreatedException Thrown when resource not created
     * @throws ResourceNotFoundException   Thrown when resources are found
     */
    public final ItemDTO processItemCreation(ItemDTO itemToCreate, HashMap<String, Object> optionalArgs) throws ResourceNotCreatedException, ResourceNotFoundException {
        boolean validated = validateItem(itemToCreate); // first validate
        if (!validated) {
            // if validation fails, throw error
            throw new ResourceNotCreatedException("The validation failed");
        }
        // else create and return item.
        return createItem(itemToCreate, optionalArgs);
    }

    // abstract method to create item as this implementation will change based on the type of item being created.
    public abstract ItemDTO createItem(ItemDTO itemToCreate, HashMap<String, Object> optionalArgs) throws ResourceNotCreatedException, ResourceNotFoundException;
}
