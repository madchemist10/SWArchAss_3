package user;

import cart.Cart;
import inventory.Item;

import java.util.ArrayList;

/**
 * User object representation to contain the user
 * specific data and previous purchases.
 */
public class User {

    /** This user's username. Used to logon.*/
    private String username;
    /** This user's shipping address. Where the items can
     * be shipped.*/
    private String shippingAddress;
    /** This user's current cart, maintains all items
     * located in this cart.*/
    private Cart cart;
    /** This user's credit card for purchasing items.*/
    private String creditCard;
    /** This user's list of previous purchases.*/
    private ArrayList<Cart> previousPurchases;

    /**
     * Create a new user from a given username.
     * Instantiate a new cart.
     * @param username this user's username.
     * @param previousPurchases previous purchases made by this user.
     */
    public User(String username, ArrayList<Cart> previousPurchases){
        this.username = username;
        this.previousPurchases = previousPurchases;
        this.cart = new Cart();
    }

    /**
     * Retrieve this user's username.
     * @return this user's username.
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Retrieve this user's shipping address.
     * @return this user's shipping address.
     */
    public String getShippingAddress(){
        return this.shippingAddress;
    }

    /**
     * Retrieve this user's current cart.
     * @return this user's current cart.
     */
    public Cart getCart(){
        return this.cart;
    }

    /**
     * Add an item to this user's cart.
     * @param item item to add to this cart.
     * @param quantity quantity of this item to add.
     */
    public void addToCart(Item item, Integer quantity){
        this.cart.addItem(item, quantity);
    }

    /**
     * Remove an item from this user's cart.
     * @param item item to remove from this cart.
     * @param quantity quantity of this item to remove.
     */
    public void removeFromCart(Item item, Integer quantity){
        this.cart.removeItem(item, quantity);
    }

    /**
     * Retrieve this user's credit card number.
     * @return this uer's credit card number.
     */
    public String getCreditCard(){
        return this.creditCard;
    }

    /**
     * Set this user's credit card number.
     * @param creditCard new credit card number.
     */
    public void setCreditCard(String creditCard){
        this.creditCard = creditCard;
    }

    /**
     * Set this user's shipping address.
     * @param shippingAddress new shipping address.
     */
    public void setShippingAddress(String shippingAddress){
        this.shippingAddress = shippingAddress;
    }

    /**
     * Retrieve this user's previous purchases.
     * @return list of previous purchases.
     */
    public ArrayList<Cart> getPreviousPurchases(){
        return this.previousPurchases;
    }

    /**
     * Purchase the current cart by adding this
     * cart to this user's previous purchases.
     */
    public void purchaseCart(){
        this.previousPurchases.add(this.cart);
        this.cart = new Cart(); //refresh the cart to start over.
    }

    /**
     * Format used is {USERNAME, SHIPPING_ADDRESS, CREDIT_CARD}
     */
    public String[] getDatabaseEntryFormat(){
        return new String[] {
                this.getUsername(),
                this.getShippingAddress(),
                this.getCreditCard()
        };
    }

}
