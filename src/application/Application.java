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

    /**Reference to the Interaction class so that the application
     * can interface with the database.*/
    private Interaction dataInteraction;
    /**Reference to the current user that is logged in.*/
    private User currentUser;
    /**Reference to the username of the current user.*/
    private String username;

    /**
     * Create a new application.
     * Create a new instance of database interaction.
     */
    public Application(){
        this.dataInteraction = new Interaction();
    }

    /**
     * Allow a user to log into the application.
     * Store the user's username.
     * Look to the database for retrieving a given user.
     * @param username User's username to login to this application.
     */
    public void login(String username){
        this.username = username;
        currentUser = dataInteraction.getUser(username);
    }

    /**
     * Called when the user has terminated the application by commandline.
     * Used to save state for the user's persistent data.
     */
    public void logout(){
        currentUser.logout();
        currentUser = null;
    }

    /**
     * Retrieve the username of the current logged in user.
     * @return logged in username.
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Retrieve the current logged in user object.
     * @return User object of the logged in user.
     */
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

    /**
     * The user's purchase has been confirmed.
     * Update the database with any changed data associated with
     * the user's object representation.
     * Update the database with the newly purchased items.
     * Alert the current user object that the cart has been purchased.
     */
    public void confirmPurchase(){
        dataInteraction.updateUserList(getCurrentUser());
        dataInteraction.updateItemList(currentUser.getCart());
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
