package inventory;

import constants.AppConstants;

/**
 * Specific representation of Book items that
 * inherit base functionality from the abstract AItem class.
 */
public class Books extends AItem {

    /**
     * Create a new book item from super class.
     * Used for adding new book item to the database.
     * @param price price of this item.
     * @param quantity this items current quantity.
     * @param name the name of this item (type).
     * @param description description of this item.
     */
    public Books(Double price, Integer quantity, String name, String description){
        super(price, quantity, name, description, AppConstants.BOOKS);
    }
}
