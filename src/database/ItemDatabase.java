package database;

import constants.AppConstants;
import inventory.Item;

import java.util.ArrayList;
/**
 */
public class ItemDatabase {

    private DatabaseConn itemDBConn;

    public ItemDatabase(){
        this.itemDBConn = new DatabaseConn(AppConstants.ITEM_DATABASE);
    }

    public void addItem(Item item){

    }

    public Item getItem(Integer id){
        return null;
    }

    public ArrayList<Item> getAllItems(){
        return null;
    }
}
