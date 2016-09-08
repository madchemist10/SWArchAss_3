package application;

import cart.Cart;
import inventory.Item;
import user.User;

import java.util.ArrayList;

/**
 * This application is responsible for translating
 * the gui actions to the rest of the system. Retrieval and
 * addition of items to and from the database is done through
 * the dataInteraction object stored in this class.
 */
public class Application {

    private Interaction dataInteraction;
    private User currentUser;
    private String username;

    public Application(){
        this.dataInteraction = new Interaction();
    }

    public void login(String username){
        this.username = username;
        currentUser = dataInteraction.getUser(username);
    }

    public void logout(){
        currentUser = null;
    }

    public String getUsername(){
        return this.username;
    }

    public User getCurrentUser(){
        return this.currentUser;
    }

    /**
     * Retrieve a list of the current user's previous purchases.
     * @return list of previous purchases.
     */
    public ArrayList<Cart> viewPreviousPurchases(){
        return currentUser.getPreviousPurchases();
    }

    private void confirmPurchase(){
        currentUser.purchaseCart();
    }

    /**
     * Add an item to this user's cart.
     * @param item item to add to this cart.
     * @param quantity quantity of this item to add.
     */
    public void addItemToCart(Item item, Integer quantity){
        currentUser.addToCart(item, quantity);
    }

    /**
     * Remove an item from this user's cart.
     * @param item item to remove from this cart.
     * @param quantity quantity of this item to remove.
     */
    public void removeItemToCart(Item item, Integer quantity){
        currentUser.removeFromCart(item, quantity);
    }

    /**
     * Retrieve a list of all available items from the database.
     * @return list of items from database.
     */
    public ArrayList<Item> displayAvailableItems(){
        return dataInteraction.getItemList();
    }
}
