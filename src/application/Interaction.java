package application;

import cart.Cart;
import database.ItemDatabase;
import database.UserDatabase;
import inventory.Item;
import user.User;

import java.util.ArrayList;

/**
 * Handles the interaction between the application and database
 * userDatabase and itemDatabase objects used to populate cart
 * and userList arrays.
 */
public class Interaction {

    /**Reference to the user database connection.*/
    private UserDatabase userDatabase;
    /**Reference to the item database connection.*/
    private ItemDatabase itemDatabase;

    /**
     * Establish a new database interaction so that the application
     * can execute queries on the user and item databases.
     */
    public Interaction(){
        this.userDatabase = new UserDatabase();
        this.itemDatabase = new ItemDatabase();
    }

    /**
     * Update the given user in the database.
     * The shipping address or credit card information
     * could have changed.
     * @param user User to update in the database.
     */
    void updateUserList(User user){
        userDatabase.updateUser(user);
    }

    /**
     * Update the item database from when a cart has been purchased.
     * Update each item in the database that has been purchased in the cart.
     * @param cart User cart that has been purchased.
     */
    void updateItemList(Cart cart){
        for(Item item: cart.getItems()){
            itemDatabase.updateItem(item);
        }
    }

    /**
     * Retrieve the item list of all items in the database.
     * @return List of all items.
     */
    ArrayList<Item> getItemList(){
        return itemDatabase.getAllItems();
    }

    /**
     * Retrieve a list of all username is the database.
     * Used to verify in the commandLineApp that a user exists before
     * attempting to login.
     * @return List of users in the form of [{USERNAME}]
     */
    public ArrayList<String[]> getUserList() { return userDatabase.getAllUsers(); }

    /**
     * Retrieve a user from the database from username.
     * @param username of the user to be retrieve.
     * @return User object representation of the user.
     */
    User getUser(String username){
        return userDatabase.getUser(username);
    }

    /**
     * Helper method to get a list of comma separated strings.
     * Used for database reads and writes, quote the entries for
     * security.
     * @return formatted string separated by commas.
     */
    public static String getDBFormat(String[] list){
        String elements = "";
        for(String element: list){
            elements+=escapeString(element)+",";
        }
        /*Remove the last comma as we do not need it.*/
        return elements.substring(0, elements.length()-1);
    }

    /**
     * Generate a comma separated list from a given string array.
     * @param list string array of elements to be comma separated.
     * @return formatted String of "x,x,x,x,....,x"
     */
    public static String getCommaSeparatedList(String[] list){
        String elements = "";
        for(String element: list){
            elements += element+",";
        }
        /*Remove the last comma as we do not need it.*/
        return elements.substring(0, elements.length()-1);
    }

    /**
     * Generate an escape string from a given string.
     * @param str string that needs to be escaped.
     * @return escaped string.
     */
    public static String escapeString(String str){
        return "'"+str+"'";
    }
}
