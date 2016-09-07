package inventory;

/**
 * Abstract representation of AItem that is extended by any
 * specific implementation of items.
 */
public abstract class AItem implements Item {

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
    AItem(Double price, Integer quantity, String name, String description){
        this.price = price;
        this.quantity = quantity;
        AItem.id++;  //used to ensure unique identifier.
        this.name = name;
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getPrice(){
        return this.price;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getQuantity(){
        return this.quantity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getId(){
        return AItem.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName(){
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription(){
        return this.description;
    }
}
