package inventory;

import constants.AppConstants;

/**
 * Specific representation of Small Electronic items that
 * inherit base functionality from the abstract AItem class.
 */
public class SmallElectronics extends AItem {

    /**
     * Create a new Small Electronic item from super class.
     * Used for adding new Small Electronic item to the database.
     * @param price price of this item.
     * @param quantity this items current quantity.
     * @param name the name of this item (type).
     * @param description description of this item.
     */
    public SmallElectronics(Double price, Integer quantity, String name, String description){
        super(price, quantity, name, description, AppConstants.SMALL_ELECTRONICS);
    }
}
