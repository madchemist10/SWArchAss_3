package database;

import application.Interaction;
import cart.Cart;
import constants.AppConstants;
import user.User;
import java.util.ArrayList;

/**
 */
public class UserDatabase {
    public UserDatabase(){
        //do nothing
    }

    void addUser(User user){
        String[] userEntry = user.getDatabaseEntryFormat();
        String values = Interaction.getHeadersDBFormat(userEntry);
        String insert = Interaction.getHeadersDBFormat(AppConstants.USER_HEADERS);
        String statement = DBStatementBuilder.insertStatement(AppConstants.USER_TABLE, insert) +
                DBStatementBuilder.valueStatement(values);
        DatabaseConn userDBConn = new DatabaseConn(AppConstants.USER_DATABASE);
        userDBConn.insertIntoTable(statement);
    }

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

    public User getUser(String username){
        String where = AppConstants.USERNAME + " = '" + username + "'";
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
        String where = AppConstants.USERNAME + " = '" + username + "'";
        /*Select * from USER_PURCHASE Where USERNAME = '{username}'*/
        String statement = DBStatementBuilder.selectStatement("*") +
                DBStatementBuilder.fromStatement(AppConstants.USER_PURCHASE_TABLE) +
                DBStatementBuilder.whereStatement(where);
        DatabaseConn userDBConn = new DatabaseConn(AppConstants.USER_DATABASE);
        ArrayList<String[]> returnVal = userDBConn.selectFromTable(statement);
        /*Given in the form of List{[ID,QUANTITY],...[ID,QUANTITY]}*/
        if(returnVal.size() == 0){
            return null;
        }
        ArrayList<Cart> userPurchases = new ArrayList<>();
        /*For each cart in the return value from the sql query.
        * In the form of a String[] where the strings are {ID,QUANTITY}*/
        for(String[] userCarts: returnVal){
            /*create a placeholder for the user carts to be stored
            * in the form of [[{ID},{QUANTITY}],...[]].
            * This holds the items in each cart.*/
            String[][] cart = new String[userCarts.length][2];
            /*For each item in the cart. Store the parsed
            * cart into the above placeholder cart.*/
            for(int i = 0; i < userCarts.length; i++){
                cart[i] = parseItemEntry(userCarts[i]);
            }
            /*Generate the cart from the placeholder array.*/
            userPurchases.add(createCartFromArray(cart));
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
     * Helper method to parse a cart's entry from the sql
     * return query.
     * @param itemEntry Comma separated item entry.
     * @return String[] of an item in a cart.
     */
    private static String[] parseItemEntry(String itemEntry){
        return itemEntry.split(",");
    }
}
