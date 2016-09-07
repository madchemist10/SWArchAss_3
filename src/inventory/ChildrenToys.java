package inventory;

/**
 * Specific representation of Children toy items that
 * inherit base functionality from the abstract Item class.
 */
public class ChildrenToys extends Item{

    public ChildrenToys(Double price, Integer quantity, String name, String description){
        super(price, quantity, name, description);
    }
}
