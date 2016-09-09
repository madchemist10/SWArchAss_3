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

    User getUser(String username){
        return userDatabase.getUser(username);
    }
}
