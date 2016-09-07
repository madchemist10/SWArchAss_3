package inventory;

/**
 * Specific representation of Book items that
 * inherit base functionality from the abstract AItem class.
 */
public class Books extends AItem {

    public Books(Double price, Integer quantity, String name, String description){
        super(price, quantity, name, description);
    }
}
