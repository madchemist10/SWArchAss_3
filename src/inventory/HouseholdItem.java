package inventory;

import constants.AppConstants;

/**
 * Specific representation of HouseHold items that
 * inherit base functionality from the abstract AItem class.
 */
public class HouseholdItem extends AItem {

    /**
     * Create a new HouseHold item from super class.
     * Used for adding new HouseHold item to the database.
     * @param price price of this item.
     * @param quantity this items current quantity.
     * @param name the name of this item (type).
     * @param description description of this item.
     */
    public HouseholdItem(Double price, Integer quantity, String name, String description){
        super(price, quantity, name, description, AppConstants.HOUSEHOLD_ITEM);
    }
}
