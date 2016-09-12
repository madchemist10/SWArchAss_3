package application;

import cart.Cart;
import database.ItemDatabase;
import database.UserDatabase;
import inventory.Item;
import user.User;

import java.util.ArrayList;

/**
 */
public class Interaction {

    private UserDatabase userDatabase;
    private ItemDatabase itemDatabase;

    public Interaction(){
        this.userDatabase = new UserDatabase();
        this.itemDatabase = new ItemDatabase();
    }

    public void updateUserList(){

    }

    public void updateItemList(Cart cart){
        for(Item item: cart.getItems()){
            itemDatabase.updateItem(item);
        }
    }

    ArrayList<Item> getItemList(){
        return itemDatabase.getAllItems();
    }

    public ArrayList<String[]> getUserList() { return userDatabase.getAllUsers(); }

    User getUser(String username){
        return userDatabase.getUser(username);
    }

    /**
     * Helper method to get a list of comma separated strings.
     * @return formatted string separated by commas.
     */
    public static String getDBFormat(String[] list){
        String elements = "";
        for(String element: list){
            elements+="'"+element+"',";
        }
        /*Remove the last comma as we do not need it.*/
        return elements.substring(0, elements.length()-1);
    }

    public static String getCommaSeparatedList(String[] list){
        String elements = "";
        for(String element: list){
            elements += element+",";
        }
        /*Remove the last comma as we do not need it.*/
        return elements.substring(0, elements.length()-1);
    }
}
