package database;

import application.Interaction;
import cart.Cart;
import constants.AppConstants;
import user.User;
import java.util.ArrayList;

/**
 * This class is responsible for retrieving users
 * from the user database as well as persisting any
 * data associated with users.
 */
public class UserDatabase {

    /**
     * Constructor used only for instantiation.
     */
    public UserDatabase(){
        //do nothing
    }

    /**
     * Add a user to the database.
     * @param user the user to add.
     */
    void addUser(User user){
        String[] userEntry = user.getDatabaseEntryFormat();
        String values = Interaction.getDBFormat(userEntry);
        String insert = Interaction.getDBFormat(AppConstants.USER_HEADERS);
        String statement = DBStatementBuilder.insertStatement(AppConstants.USER_TABLE, insert) +
                DBStatementBuilder.valueStatement(values);
        DatabaseConn userDBConn = new DatabaseConn(AppConstants.USER_DATABASE);
        userDBConn.insertIntoTable(statement);
    }

    /**
     * Update a user's attributes [Shipping Address, CreditCard].
     * Update is based on the username of the user.
     * @param user user to update in the database.
     */
    public void updateUser(User user){
        /*Update User Set [Column = Data],... Where USERNAME = {username}*/
        String statement = DBStatementBuilder.updateStatement(AppConstants.USER_TABLE);
        String setStatement = "";
        for(int i = 0; i < AppConstants.USER_HEADERS.length; i++){
            boolean itemAdded = false;
            switch(AppConstants.USER_HEADERS[i]){
                case AppConstants.USERNAME:
                    break;
                case AppConstants.SHIPPING_ADDRESS:
                    setStatement+=AppConstants.SHIPPING_ADDRESS+"="+Interaction.escapeString(user.getShippingAddress());
                    itemAdded = true;
                    break;
                case AppConstants.CREDIT_CARD:
                    setStatement+=AppConstants.CREDIT_CARD+"="+Interaction.escapeString(user.getCreditCard());
                    itemAdded = true;
                    break;
            }
            if(itemAdded && i != AppConstants.USER_HEADERS.length-1) {
                setStatement += ",";
            }
            /*If more parameters of an user are to be updated, need to add commas after
            * each parameter to update.*/
        }
        statement += DBStatementBuilder.setStatement(setStatement);
        statement += DBStatementBuilder.whereStatement(AppConstants.USERNAME+"="+Interaction.escapeString(user.getUsername()));
        DatabaseConn userDBConn = new DatabaseConn(AppConstants.USER_DATABASE);
        userDBConn.updateTableEntry(statement);
    }

    /**
     * Retrieve a list of all users.
     * Used for making sure that the username of
     * the user trying to login is in the database (valid user)
     * @return list of string arrays (each with username as only element)
     */
    public ArrayList<String[]> getAllUsers(){
        /*Select * from User*/
        String statement = DBStatementBuilder.selectStatement(AppConstants.USERNAME) +
                DBStatementBuilder.fromStatement(AppConstants.USER_TABLE);
        DatabaseConn userDBConn = new DatabaseConn(AppConstants.USER_DATABASE);
        ArrayList<String[]> returnVal = userDBConn.selectFromTable(statement);
        if(returnVal.size() == 0){
            return null;
        }
        return returnVal;
    }

    /**
     * Add a given cart to the database for a given username.
     * @param cart cart to add to the database, cart has been
     *             purchased at this point.
     * @param username user's username in the database,
     *                 key to retrieve this as a previous purchase
     *                 later on new login.
     */
    public void addCartToDB(Cart cart, String username){
        /*Format of {---{ID},{QUANTITY}---,---{ID},{QUANTITY}---,...}*/
        String[] cartDBEntry = cart.getCartDatabaseEntry();
        if(cartDBEntry.length == 0){
            //no items in cart
            return;
        }
        /*Format of {'..---{ID},{QUANTITY}---,---{ID},{QUANTITY}---,---...---..]}*/
        String items = Interaction.getCommaSeparatedList(cartDBEntry);
        String[] cartEntry = new String[]{username,items};
        String insert = Interaction.getDBFormat(AppConstants.PURCHASE_HEADERS);
        String values = Interaction.getDBFormat(cartEntry);
        String statement = DBStatementBuilder.insertStatement(AppConstants.USER_PURCHASE_TABLE, insert) +
                DBStatementBuilder.valueStatement(values);
        DatabaseConn userDBConn = new DatabaseConn(AppConstants.USER_DATABASE);
        userDBConn.insertIntoTable(statement);
    }

    /**
     * Retrieve a user by their username.
     * @param username user's username in the database.
     * @return User object from the user's persisted data.
     */
    public User getUser(String username){
        String where = AppConstants.USERNAME + " = " + Interaction.escapeString(username);
        /*Select * from USER Where USERNAME = '{username}'*/
        String statement = DBStatementBuilder.selectStatement("*") +
                DBStatementBuilder.fromStatement(AppConstants.USER_TABLE) +
                DBStatementBuilder.whereStatement(where);
        DatabaseConn userDBConn = new DatabaseConn(AppConstants.USER_DATABASE);
        ArrayList<String[]> returnVal = userDBConn.selectFromTable(statement);
        if(returnVal.size() == 0){
            return null;
        }
        String[] userEntry = returnVal.get(0);
        return createUserFromArray(userEntry);
    }

    /**
     * Helper method to create an user from an user database entry.
     * @param userEntry string array of the elements of the item.
     * @return User created from string array.
     */
    private static User createUserFromArray(String[] userEntry){
        String username = userEntry[0];
        String shippingAddress = userEntry[1];
        String creditCard = userEntry[2];
        User user = new User(username, getUserPreviousPurchases(username));
        user.setShippingAddress(shippingAddress);
        user.setCreditCard(creditCard);
        return user;
    }

    /**
     * Retrieve all the user's previous purchases.
     * @param username user to retrieve carts from database.
     * @return List of carts associated with the given user.
     */
    private static ArrayList<Cart> getUserPreviousPurchases(String username){
        String where = AppConstants.USERNAME + " = " + Interaction.escapeString(username);
        /*Select * from USER_PURCHASE Where USERNAME = '{username}'*/
        String statement = DBStatementBuilder.selectStatement("*") +
                DBStatementBuilder.fromStatement(AppConstants.USER_PURCHASE_TABLE) +
                DBStatementBuilder.whereStatement(where);
        DatabaseConn userDBConn = new DatabaseConn(AppConstants.USER_DATABASE);
        ArrayList<String[]> returnVal = userDBConn.selectFromTable(statement);
        /*Given in the form of List{[---ID,QUANTITY---,...],...}*/
        if(returnVal.size() == 0){
            return null;
        }
        ArrayList<Cart> userPurchases = new ArrayList<>();
        /*For each cart in the return value from the sql query.
        * In the form of a String[] where the strings are ---ID,QUANTITY---,...*/
        for(String[] userCarts: returnVal){
            String[][] parsedCart = parseCart(userCarts[1]);
            Cart userCart = createCartFromArray(parsedCart);
            userPurchases.add(userCart);
        }
        return userPurchases;
    }

    /**
     * Generate a cart that has been given in the form of:
     * [[{ID},{QUANTITY}],...[{ID},{QUANTITY}]]
     * @param userCart string[][] of items in the cart of their
     *                 ID and QUANTITY.
     * @return created cart from the items.
     */
    private static Cart createCartFromArray(String[][] userCart){
        ItemDatabase itemDB = new ItemDatabase();
        Cart cart = new Cart();
        for (String[] anUserCart: userCart) {
            Integer id = Integer.parseInt(anUserCart[0]);
            Integer quantity = Integer.parseInt(anUserCart[1]);
            cart.addItem(itemDB.getItem(id), quantity);
        }
        return cart;
    }

    /**
     * Receive a formatted item in the form of:
     * ---{ID},{QUANTITY}---
     * @param item string representation of database item.
     * @return [{ID},{QUANTITY}]
     */
    private static String[] parseItem(String item){
        item = item.replace("---","");
        return item.split(",");
    }

    /**
     * Receive a formatted cart in the form of:
     * ---{ID},{QUANTITY}---,---{ID},{QUANTITY}---,...
     * @param cart string representation of a database cart.
     * @return [[{ID},{QUANTITY}],...]
     */
    private static String[][] parseCart(String cart){
        String[] items = cart.split("---,---");
        String[][] parsedCart = new String[items.length][2];
        for(int i = 0; i < items.length; i++){
            parsedCart[i] = parseItem(items[i]);
        }
        return parsedCart;
    }
}
