package inventory;

import constants.AppConstants;

/**
 * Specific representation of Children toy items that
 * inherit base functionality from the abstract AItem class.
 */
public class ChildrenToys extends AItem {

    /**
     * Create a new Children toy item from super class.
     * Used for adding new Children toy item to the database.
     * @param price price of this item.
     * @param quantity this items current quantity.
     * @param name the name of this item (type).
     * @param description description of this item.
     */
    public ChildrenToys(Double price, Integer quantity, String name, String description){
        super(price, quantity, name, description, AppConstants.CHILDREN_TOYS);
    }
}
