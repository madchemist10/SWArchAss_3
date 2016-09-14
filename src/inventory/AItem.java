package inventory;

/**
 * Abstract representation of AItem that is extended by any
 * specific implementation of items.
 */
abstract class AItem implements Item {
    /** Current price of this item.*/
    private Double price;
    /** Current quantity of this item either in
     * database or in cart.*/
    private Integer quantity;
    /** Current items unique identification number.*/
    private Integer id;
    /** Current item's name to be displayed to the user.*/
    private String name;
    /** Current description assigned to this item to be shown
     * to the user.*/
    private String description;
    /** Type of item contained in this object.*/
    private String itemType;

    /**
     * Create a new item from a subclass.
     * Used for adding new item to the database.
     * @param price price of this item.
     * @param quantity this items current quantity.
     * @param name the name of this item (type).
     * @param description description of this item.
     * @param itemType the type of item that is to be created.
     */
    AItem(Double price, Integer quantity, String name, String description, String itemType){
        this.price = price;
        this.quantity = quantity;
        this.name = name;
        this.description = description;
        this.itemType = itemType;
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
        return this.id;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String getItemType(){
        return this.itemType;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setID(Integer id){
        this.id = id;
    }

    /**
     * Returns a string object of item data for database storage
     * Format used is {ID, Name, Description, Quantity, Price, ItemType}
     * {@inheritDoc}
     */
    @Override
    public String[] getDatabaseEntryFormat(){
        return new String[]{
                this.getId().toString(),
                this.getName(),
                this.getDescription(),
                this.getQuantity().toString(),
                this.getPrice().toString(),
                this.getItemType()};
    }

    /**
     * Returns an array of elements to display to the user.
     * @return String[] of this item to display.
     */
    private String[] getDisplayItem(){
        return new String[]{
                this.getId().toString(),
                this.getName(),
                this.getQuantity().toString(),
                this.getPrice().toString()
        };
    }

    /**
     * Returns a comma separated list of item data
     * {@inheritDoc}
     */
    @Override
    public String displayItem(){
        String display = "";
        for(String str: getDisplayItem()){
            display += str + ",";
        }
        return display.substring(0, display.length()-1);
    }
}
