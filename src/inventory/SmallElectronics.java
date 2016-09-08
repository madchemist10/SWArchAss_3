package inventory;

import constants.AppConstants;

/**
 * Specific representation of Small Electronic items that
 * inherit base functionality from the abstract AItem class.
 */
public class SmallElectronics extends AItem {

    public SmallElectronics(Double price, Integer quantity, String name, String description){
        super(price, quantity, name, description, AppConstants.SMALL_ELECTRONICS);
    }
}
