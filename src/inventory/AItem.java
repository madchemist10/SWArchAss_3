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
     * Format used is {ID, Name, Description, Quantity, Price}
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
     * {@inheritDoc}
     */
    @Override
    public String displayItem(){
        String display = "";
        for(String str: getDatabaseEntryFormat()){
            display += str + ",";
        }
        return display.substring(0, display.length()-1);
    }

    /**
     * Helper method to get a list of items in the
     * @return formatted string separated by commas.
     */
    public static String getItemHeadersDBFormat(String[] list){
        String elements = "";
        for(String element: list){
            elements+="'"+element+"',";
        }
        /*Remove the last comma as we do not need it.*/
        return elements.substring(0, elements.length()-1);
    }
}
