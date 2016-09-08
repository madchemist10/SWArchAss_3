package database;

import constants.AppConstants;
import inventory.Books;
import inventory.Item;
import user.User;

import java.util.ArrayList;

/**
 * This is a utility class to generate the database
 * with dummy data.
 */
public class DatabaseCreation {

    public static void main(String[] args) {
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
        return itemList;
    }

    private static ArrayList<User> generateUserList(){
        ArrayList<User> userList = new ArrayList<>();
        int counter = 0;
        String userName = "User_";
        while(counter < 5){
            User newUser = new User(userName+counter,null);
            userList.add(newUser);
            counter++;
        }
        return userList;
    }
}
