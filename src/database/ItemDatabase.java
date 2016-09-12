package database;

import application.Interaction;
import constants.AppConstants;
import inventory.*;

import java.util.ArrayList;

/**
 * This class is responsible for retrieving items from
 * the item database.
 */
public class ItemDatabase {
    /**
     * Constructor used only for instantiation.
     */
    public ItemDatabase(){
        //nothing to be done.
    }

    /**
     * Add an item to the database.
     * @param item the item to add.
     */
    void addItem(Item item){
        String[] itemEntry = item.getDatabaseEntryFormat();
        String values = Interaction.getDBFormat(itemEntry);
        String insert = Interaction.getDBFormat(AppConstants.ITEM_HEADERS);
        String statement = DBStatementBuilder.insertStatement(AppConstants.ITEM_TABLE, insert) +
                DBStatementBuilder.valueStatement(values);
        DatabaseConn itemDBConn = new DatabaseConn(AppConstants.ITEM_DATABASE);
        itemDBConn.insertIntoTable(statement);
    }

    /**
     * Retrieve a specific item from the database.
     * @param id id of the specific item to retrieve.
     * @return Item object of the retrieved item.
     */
    public Item getItem(Integer id){
        String ID = id.toString();
        String where = AppConstants.ID + " = '" + ID + "'";
        /*Select * from ITEM Where ID = '{ID}'*/
        String statement = DBStatementBuilder.selectStatement("*") +
                DBStatementBuilder.fromStatement(AppConstants.ITEM_TABLE) +
                DBStatementBuilder.whereStatement(where);
        DatabaseConn itemDBConn = new DatabaseConn(AppConstants.ITEM_DATABASE);
        ArrayList<String[]> returnVal = itemDBConn.selectFromTable(statement);
        if(returnVal.size() == 0){
                return null;
        }
        String[] itemEntry = returnVal.get(0);
        return createItemFromArray(itemEntry);
    }

    /**
     * Retrieve all items from the database.
     * @return list of all items from the database.
     */
    public ArrayList<Item> getAllItems(){
        /*Select * from Item*/
        String statement = DBStatementBuilder.selectStatement("*") +
                DBStatementBuilder.fromStatement(AppConstants.ITEM_TABLE);
        DatabaseConn itemDBConn = new DatabaseConn(AppConstants.ITEM_DATABASE);
        ArrayList<String[]> returnVal = itemDBConn.selectFromTable(statement);
        if(returnVal.size() == 0){
            return null;
        }
        ArrayList<Item> itemList = new ArrayList<>();
        for(String[] itemEntry: returnVal){
            itemList.add(createItemFromArray(itemEntry));
        }
        return itemList;
    }

    /**
     * Update an item in the database with the item's new data.
     * This is called when a cart is purchased. Item given is
     * an item from the cart. The quantity in this item
     * is the quantity that is ordered. This item's quantity
     * must be subtracted from current quantity.
     * @param item new item to update entry in database.
     */
    public void updateItem(Item item){
        /*Update Item Set [Column = Data],... Where Id = {Id}*/
        String statement = DBStatementBuilder.updateStatement(AppConstants.ITEM_TABLE);
        String setStatement = "";
        String[] itemDatabaseEntry = item.getDatabaseEntryFormat();
        /*Although at this time, only one item is pulled from array,
        * used for loop for modularity if other functionality is implemented.*/
        for(int i = 0; i < AppConstants.ITEM_HEADERS.length; i++){
            switch(AppConstants.ITEM_HEADERS[i]){
                case AppConstants.ID:
                    break;
                case AppConstants.NAME:
                    break;
                case AppConstants.DESCRIPTION:
                    break;
                case AppConstants.QUANTITY:
                    int currentQuantityOrdered = Integer.parseInt(itemDatabaseEntry[i]);
                    Item databaseItem = getItem(item.getId());
                    int newItemQuantity = databaseItem.getQuantity()-currentQuantityOrdered;
                    setStatement+=AppConstants.QUANTITY+"='"+newItemQuantity+"'";
                    break;
                case AppConstants.PRICE:
                    break;
                case AppConstants.ITEM_TYPE:
                    break;
            }
            /*If more parameters of an item are to be updated, need to add commas after
            * each parameter to update.*/
        }
        statement += DBStatementBuilder.setStatement(setStatement);
        statement += DBStatementBuilder.whereStatement(AppConstants.ID+"='"+item.getId()+"'");
        DatabaseConn itemDBConn = new DatabaseConn(AppConstants.ITEM_DATABASE);
        itemDBConn.updateTableEntry(statement);
    }

    /**
     * Helper method to create an item from an item database entry.
     * @param itemEntry string array of the elements of the item.
     * @return Item created from string array.
     */
    private static Item createItemFromArray(String[] itemEntry){
        Integer id = Integer.parseInt(itemEntry[0]);
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
}
