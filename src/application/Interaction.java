package application;

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

    public void updateItemList(){

    }

    public ArrayList<Item> getItemList(){
        return null;
    }

    public ArrayList<User> getUserList(){
        return null;
    }
}
