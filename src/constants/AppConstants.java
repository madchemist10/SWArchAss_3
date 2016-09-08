package constants;

/**
 * AppConstants used through this application.
 */
public interface AppConstants {

    /*Database constants.*/
    /** Name used for the item database.*/
    String ITEM_DATABASE = "ItemDB.sqlite";
    /** Name used for the user database.*/
    String USER_DATABASE = "UserDB.sqlite";
    /** Name used for the item table.*/
    String ITEM_TABLE = "ITEM";
    /** Name used for the user table.*/
    String USER_TABLE = "USER";
    /** Name used for the user's previous purchase table.*/
    String USER_PURCHASE_TABLE = "USER_PURCHASE";

    /*Item table headers.*/
    String ID = "ID";
    String NAME = "Name";
    String DESCRIPTION = "Description";
    String QUANTITY = "Quantity";
    String PRICE = "Price";
    String ITEM_TYPE = "Type";
    String[] ITEM_HEADERS = {ID, NAME, DESCRIPTION, QUANTITY, PRICE, ITEM_TYPE};

    /*Item types*/
    String BOOKS = "BOOKS";
    String CHILDREN_TOYS = "CHILDREN_TOYS";
    String HOUSEHOLD_ITEM = "HOUSEHOLD_ITEM";
    String SMALL_ELECTRONICS = "SMALL_ELECTRONICS";

    /*User table headers.*/
    String USERNAME = "Username";
    String CREDIT_CARD = "CreditCard";
    String SHIPPING_ADDRESS = "ShippingAddress";
    String[] USER_HEADERS = {USERNAME, CREDIT_CARD, SHIPPING_ADDRESS};

    /*User purchase headers.*/
    String ITEM_ID_LIST = "ItemIDList";
    String[] PURCHASE_HEADERS = {USERNAME, ITEM_ID_LIST};
}
