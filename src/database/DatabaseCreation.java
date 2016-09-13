package database;

import cart.Cart;
import constants.AppConstants;
import inventory.*;
import user.User;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * This is a utility class to generate the database
 * with dummy data.
 */
public class DatabaseCreation {

    /**
     * Generate a new database for both items and user data.
     * @param args not used.
     */
    public static void main(String[] args) {
        removeOldDatabaseFiles();
        //establish connection
        DatabaseConn itemDataConn = new DatabaseConn(AppConstants.ITEM_DATABASE);
        DatabaseConn userDataConn = new DatabaseConn(AppConstants.USER_DATABASE);

        //create item table............................
        String createItemTable =
                DBStatementBuilder.createTableStatement(AppConstants.ITEM_TABLE)+
                "(";
        for(String str: AppConstants.ITEM_HEADERS){
            createItemTable+=DBStatementBuilder.createTableTextColumn(str)+",";
        }
        createItemTable = createItemTable.substring(0,createItemTable.length()-1);
        createItemTable+=")";
        itemDataConn.makeTable(createItemTable);

        //create user table..............................
        String createUserTable =
                DBStatementBuilder.createTableStatement(AppConstants.USER_TABLE)+
                        "(";
        for(String str: AppConstants.USER_HEADERS){
            createUserTable+=DBStatementBuilder.createTableTextColumn(str)+",";
        }
        createUserTable = createUserTable.substring(0,createUserTable.length()-1);
        createUserTable+=")";
        userDataConn.makeTable(createUserTable);

        //create user purchase table..............................
        userDataConn = new DatabaseConn(AppConstants.USER_DATABASE);
        String createUserPurchaseTable =
                DBStatementBuilder.createTableStatement(AppConstants.USER_PURCHASE_TABLE)+
                        "(";
        for(String str: AppConstants.PURCHASE_HEADERS){
            createUserPurchaseTable+=DBStatementBuilder.createTableTextColumn(str)+",";
        }
        createUserPurchaseTable = createUserPurchaseTable.substring(0,createUserPurchaseTable.length()-1);
        createUserPurchaseTable+=")";
        userDataConn.makeTable(createUserPurchaseTable);

        //add new items to the databases........................
        ItemDatabase itemDatabase = new ItemDatabase();
        UserDatabase userDatabase = new UserDatabase();

        for(Item item: generateItemList()){
            itemDatabase.addItem(item);
        }

        for(User user: generateUserList()){
            userDatabase.addUser(user);
        }
    }

    /** Generates items in database
     * IDs of 0-4 (inclusive) are books
     * IDs of 5-9 (inclusive) are toys
     * IDs of 10-14 (inclusive) are household items
     * IDs of 15-19 (inclusive) are small electronics
     * @return List of items that are generated.
    * */
    private static ArrayList<Item> generateItemList(){
        ArrayList<Item> itemList = new ArrayList<>();
        int id = 0;
        double price = 3.0;
        int quantity = 1;
        while(id < 5){
            Books newBook = new Books(price++,quantity++,"Book_"+id,"Test Book Description ID:"+id);
            newBook.setID(id);
            itemList.add(newBook);
            id++;
        }
        while(id >= 5 && id < 10)
        {
            ChildrenToys toy = new ChildrenToys(price++, quantity++, "ChildrensToy_" + id, "Test Childrens Toy #:" + id);
            toy.setID(id);
            itemList.add(toy);
            id++;
        }
        while(id >= 10 && id < 15)
        {
            HouseholdItem householdItem = new HouseholdItem(price++, quantity++, "HouseholdItem_" + id, "Test Household Item #:" + id);
            householdItem.setID(id);
            itemList.add(householdItem);
            id++;
        }
        while(id >= 15 && id < 20)
        {
            SmallElectronics electronic = new SmallElectronics(price++, quantity++, "SmallElectronic_" + id, "Test Small Electronic #:" + id);
            electronic.setID(id);
            itemList.add(electronic);
            id++;
        }
        return itemList;
    }
    
    /**
     * Generate a list of users and their carts from the Database
     * @return List of users.
     */
    private static ArrayList<User> generateUserList(){
        UserDatabase userDB = new UserDatabase();
        ArrayList<User> userList = new ArrayList<>();
        int counter = 0;
        String userName = "User_";
        while(counter < 5){
            ArrayList<Cart> cartList = generateCartList();
            User newUser = new User(userName+counter,cartList);
            for(Cart cart: cartList){
                userDB.addCartToDB(cart,newUser.getUsername());
            }
            userList.add(newUser);
            counter++;
        }
        return userList;
    }
    
    /**
     * Generates cart objects to add to a cartList for use by userList
     * @return List of Carts.
     */
    private static ArrayList<Cart> generateCartList(){
        ItemDatabase itemDB = new ItemDatabase();
        ArrayList<Cart> cartList = new ArrayList<>();
        int counter = 0;
        while(counter < 3){
            Cart cart = new Cart();
            cart.addItem(itemDB.getItem(counter),1);
            cart.addItem(itemDB.getItem(counter+1),1);
            cart.addItem(itemDB.getItem(counter+2),1);
            cartList.add(cart);
            counter++;
        }
        return cartList;
    }

    /**
     * Remove old database files for new ones to be created.
     * This keeps old files from being generated with duplicate data.
     */
    private static void removeOldDatabaseFiles(){
        try {
            Files.delete(Paths.get(AppConstants.ITEM_DATABASE));
            Files.delete(Paths.get(AppConstants.USER_DATABASE));
        }catch(Exception e){
            //do nothing.
        }
    }
}
