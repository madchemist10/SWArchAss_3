package cart;

import inventory.Item;

import java.util.ArrayList;

/**
 * Representation of a user's cart.
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
     * @param item item to add to this cart.
     * @param quantity quantity of this item to add.
     */
    public void addItem(Item item, Integer quantity){
        /*Update the quantity of an item.*/
        item.setQuantity(quantity);

        /*Update the running total of this cart.*/
        updateTotal(item.getPrice());

        /*If the item is already in the list,
        * early return from method.*/
        if(items.contains(item)){
            return;
        }
        items.add(item);
    }

    /**
     * Remove an item from the list if the new
     * quantity is 0. Otherwise, just update the
     * quantity.
     * @param item item to remove from this cart.
     * @param quantity quantity of this item to remove.
     */
    public void removeItem(Item item, Integer quantity){
        /*Update the quantity of an item.*/
        item.setQuantity(quantity);

        /*If the quantity of the item after update is
        * equal to 0, remove it from this carts list.*/
        if(item.getQuantity() == 0){
            items.remove(item);
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

    private String[] generateCartDatabaseEntry(String[][] cart){
        String[] cartEntry = new String[cart.length];
        for(int i = 0; i < cart.length; i++){
            String str = "";
            /*{ID},{QUANTITY}*/
            for(int j = 0; j < cart[i].length; j++){
                str+=cart[i][j];
                if(j != cart[i].length-1){
                    str+=",";
                }
            }
            cartEntry[i] = str;
        }
        return cartEntry;
    }
}
