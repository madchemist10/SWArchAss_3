package inventory;

/**
 * Abstract representation of Item that is extended by any
 * specific implementation of items.
 */
public abstract class Item {
    /** Current price of this item.*/
    private Double price;
    /** Current quantity of this item either in
     * database or in cart.*/
    private Integer quantity;
    /** Current items unique identification number.*/
    private static Integer id = 0;
    /** Current item's name to be displayed to the user.*/
    private String name;
    /** Current description assigned to this item to be shown
     * to the user.*/
    private String description;

    /**
     * Create a new item from a subclass.
     * Used for adding new item to the database.
     * @param price price of this item.
     * @param quantity this items current quantity.
     * @param name the name of this item (type).
     * @param description description of this item.
     */
    protected Item(Double price, Integer quantity, String name, String description){
        this.price = price;
        this.quantity = quantity;
        Item.id++;  //used to ensure unique identifier.
        this.name = name;
        this.description = description;
    }

    /**
     * Retrieve this item's price.
     * @return Double representation of this item's price.
     */
    protected Double getPrice(){
        return this.price;
    }

    /**
     * Retrieve this item's quantity.
     * @return Integer representation of this item's quantity.
     */
    protected Integer getQuantity(){
        return this.quantity;
    }

    /**
     * Assign new quantity to this item
     * @param quantity new quantity of this item.
     */
    protected void setQuantity(Integer quantity){
        this.quantity = quantity;
    }

    /**
     * Retrieve this item's unique id.
     * @return Integer representation of this item's unique id.
     */
    protected Integer getId(){
        return Item.id;
    }

    /**
     * Retrieve this item's name.
     * @return String representation of this item's name.
     */
    protected String getName(){
        return this.name;
    }

    /**
     * Retrieve this item's description.
     * @return String representation of this item's description.
     */
    protected String getDescription(){
        return this.description;
    }
}
