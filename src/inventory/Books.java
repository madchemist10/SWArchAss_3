package inventory;

/**
 * Specific representation of Book items that
 * inherit base functionality from the abstract Item class.
 */
public class Books extends Item{

    public Books(Double price, Integer quantity, String name, String description){
        super(price, quantity, name, description);
    }
}
