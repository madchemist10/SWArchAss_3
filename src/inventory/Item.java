package inventory;

/**
 * Interface for storing data into array list
 * in carts. Allows an interface for accessing data from
 * different Item object.
 */
public interface Item {

    /**
     * Retrieve this item's price.
     * @return Double representation of this item's price.
     */
    Double getPrice();

    /**
     * Retrieve this item's quantity.
     * @return Integer representation of this item's quantity.
     */
    Integer getQuantity();

    /**
     * Assign new quantity to this item
     * @param quantity new quantity of this item.
     */
    void setQuantity(Integer quantity);

    /**
     * Retrieve this item's unique id.
     * @return Integer representation of this item's unique id.
     */
    Integer getId();

    /**
     * Retrieve this item's name.
     * @return String representation of this item's name.
     */
    String getName();

    /**
     * Retrieve this item's description.
     * @return String representation of this item's description.
     */
    String getDescription();
}
