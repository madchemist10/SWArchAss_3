package inventory;

/**
 * Specific representation of HouseHold items that
 * inherit base functionality from the abstract AItem class.
 */
public class HouseholdItem extends AItem {

    public HouseholdItem(Double price, Integer quantity, String name, String description){
        super(price, quantity, name, description);
    }
}
