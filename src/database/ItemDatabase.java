package database;

import constants.AppConstants;
import inventory.Item;

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
}
