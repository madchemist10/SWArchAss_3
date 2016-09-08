package database;

import constants.AppConstants;
import inventory.*;

import java.util.ArrayList;

/**
 * This class is responsible for retrieving items from
 * the item database.
 */
public class ItemDatabase {

    /** Reference to the database connection for the
     * item database for retrieval and adding.*/
    private DatabaseConn itemDBConn;

    /**
     *
     */
    public ItemDatabase(){
        this.itemDBConn = new DatabaseConn(AppConstants.ITEM_DATABASE);
    }

    /**
     * Add an item to the database.
     * @param item the item to add.
     */
    public void addItem(Item item){
        String[] itemEntry = item.getDatabaseEntryFormat();
        String values = AItem.getItemHeadersDBFormat(itemEntry);
        String insert = AItem.getItemHeadersDBFormat(AppConstants.ITEM_HEADERS);
        String statement = DBStatementBuilder.insertStatement(AppConstants.ITEM_TABLE, insert) +
                DBStatementBuilder.valueStatement(values);
        this.itemDBConn.insertIntoTable(statement);
    }

    public Item getItem(Integer id){
        String ID = id.toString();
        String where = AppConstants.ID + " = '" + ID + "'";
        /*Select * from ITEM Where ID = '{ID}'*/
        String statement = DBStatementBuilder.selectStatement("*") +
                DBStatementBuilder.fromStatement(AppConstants.ITEM_TABLE) +
                DBStatementBuilder.whereStatement(where);
        ArrayList<String[]> returnVal = this.itemDBConn.selectFromTable(statement);
        if(returnVal.size() == 0){
                return null;
        }
        String[] itemEntry = returnVal.get(0);
        /*Last element is itemType*/
        String itemType = itemEntry[itemEntry.length-1];
        Double price = Double.parseDouble(itemEntry[4]);
        Integer quantity = Integer.parseInt(itemEntry[3]);
        String name = itemEntry[1];
        String description = itemEntry[2];
        Item item;
        switch(itemType){
            case AppConstants.BOOKS:
                item = new Books(price,quantity,name,description);
                item.setID(id);
                return item;
            case AppConstants.CHILDREN_TOYS:
                item = new ChildrenToys(price,quantity,name,description);
                item.setID(id);
                return item;
            case AppConstants.HOUSEHOLD_ITEM:
                item = new HouseholdItem(price,quantity,name,description);
                item.setID(id);
                return item;
            case AppConstants.SMALL_ELECTRONICS:
                item = new SmallElectronics(price,quantity,name,description);
                item.setID(id);
                return item;
        }
        return null;
    }

    public ArrayList<Item> getAllItems(){
        return null;
    }

    public void updateItem(Item item){
    }
}
