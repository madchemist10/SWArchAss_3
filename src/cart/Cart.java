package cart;

import inventory.Item;

import java.util.ArrayList;

/**
 * Representation of a user's cart.
 * Maintains a list of items as well as total value
 * of the cart.
 */
public class Cart {

    /** List of items stored in this cart.*/
    private ArrayList<Item> items;
    /** Running total of the price of the items in
     * this cart.*/
    private Double totalPrice = 0.0;

    /**
     * Create a new empty cart.
     */
    public Cart(){
        this.items = new ArrayList<>();
    }

    /**
     * Retrieve the list of items stored in this
     * cart.
     * @return List of items.
     */
    public ArrayList<Item> getItems(){
        return this.items;
    }

    /**
     * Add a new item to the list of items
     * in this cart. Update the quantity and running
     * total for every item that is added, even if
     * it is the same item.
     * Assumed that the new quantity given is larger than previous.
     * @param item item to add to this cart.
     * @param quantity quantity of this item to add.
     */
    public void addItem(Item item, Integer quantity){

        /*Update the quantity of an item.*/
        item.setQuantity(quantity);

        /*If the item is in the list, update the quantity.*/
        for(Item thisItem: getItems()){
            if(thisItem.getId().equals(item.getId())){
                Integer deltaQuantity = quantity - thisItem.getQuantity();
                updateTotal(deltaQuantity*thisItem.getPrice());
                thisItem.setQuantity(quantity);
                return;
            }
        }

        /*Update the running total of this cart.
        * If the item is new, then updated full total.*/
        updateTotal(quantity * item.getPrice());

        /*Add item if not found in the list.*/
        items.add(item);
    }

    /**
     * Remove an item from the list if the new
     * quantity is 0. Otherwise, just update the
     * quantity.
     * Assumed that the new quantity given is less than previous.
     * Assumed that the item is in the cart already.
     *      (the id of the item must match)
     * @param item item to remove from this cart.
     * @param quantity quantity of this item to remove.
     */
    public void removeItem(Item item, Integer quantity){

        /*Update the quantity of an item.*/
        item.setQuantity(quantity);

        /*If the item is in the list, update the quantity.*/
        for(Item thisItem: getItems()){
            if(thisItem.getId().equals(item.getId())){
                Integer deltaQuantity = thisItem.getQuantity() - quantity;
                if(deltaQuantity > 0) {
                    updateTotal(-(deltaQuantity * thisItem.getPrice()));
                    thisItem.setQuantity(quantity);
                }
                /*If the quantity of the item after update is
                * equal to 0, remove it from this cart's list.*/
                if(item.getQuantity() == 0){
                    //remove all price values for removed item.
                    updateTotal(-(thisItem.getQuantity() * thisItem.getPrice()));
                    items.remove(thisItem);
                }
                return;
            }
        }
    }

    /**
     * Retrieve the total running price of
     * this cart.
     * @return Double representation of this cart's
     *      running total.
     */
    public Double getTotalPrice(){
        return this.totalPrice;
    }

    /**
     * Update the total running price of this cart.
     * @param price price to add this cart's total.
     */
    private void updateTotal(Double price){
        this.totalPrice += price;
    }

    /**
     * Retrieve this carts database entry in string[][] format.
     *[[{ID},{QUANTITY}],[{ID},{QUANTITY}],...,[{ID},{QUANTITY}]]
     * @return array of strings for this cart.
     */
    public String[] getCartDatabaseEntry(){
        ArrayList<String[]> itemList = new ArrayList<>();
        for(Item item: this.getItems()){
            /*[{ID},{QUANTITY}]*/
            itemList.add(new String[]{item.getId().toString(), item.getQuantity().toString()});
        }
        String[][] cart = new String[itemList.size()][2];
        /*[[{ID},{QUANTITY}],[{ID},{QUANTITY}],...,[{ID},{QUANTITY}]]*/
        for(int i = 0; i < itemList.size(); i++){
            cart[i] = itemList.get(i);
        }
        return generateCartDatabaseEntry(cart);
    }

    /**
     * Create a String[] where the items are comma separated.
     * @param cart string[][] of the cart of items.
     *             list of items where each item only has id and quantity.
     * @return [---{ID},{QUANTITY}---,---{ID},{QUANTITY}---,...]
     */
    private String[] generateCartDatabaseEntry(String[][] cart){
        String[] cartEntry = new String[cart.length];
        /*For each item.*/
        for(int i = 0; i < cart.length; i++){
            String temp = "---";
            /*---{ID},{QUANTITY}---*/
            for(int j = 0; j < cart[i].length; j++){
                temp+=cart[i][j];
                if(j != cart[i].length-1){
                    temp+=",";
                }
            }
            temp+="---";
            cartEntry[i] = temp;
        }
        return cartEntry;
    }

    /**
     * Convert this cart to a string array of items.
     * @return String[] of items.
     */
    public String[] toStringArray(){
        ArrayList<Item> items = this.getItems();
        String[] itemArray = new String[items.size()];
        for(int i = 0; i < items.size(); i++){
            itemArray[i] = items.get(i).displayItem();
        }
        return itemArray;
    }
}
