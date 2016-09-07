package inventory;

/**
 * Specific representation of HouseHold items that
 * inherit base functionality from the abstract Item class.
 */
public class HouseholdItem extends Item{

    public HouseholdItem(Double price, Integer quantity, String name, String description){
        super(price, quantity, name, description);
    }
}
