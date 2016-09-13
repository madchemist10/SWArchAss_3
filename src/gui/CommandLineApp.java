package gui;

import application.Application;
import application.Interaction;
import cart.Cart;
import constants.AppConstants;
import constants.GUIConstants;
import database.ItemDatabase;
import inventory.Item;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is the user interaction with the system through a command line application.
 */
public class CommandLineApp {
    private static Application app;

    public static void main(String[] args) {
        app = new Application();
        Interaction interaction = new Interaction();
        /*If the application has been given a command line
        * argument, the first element must be the username.*/
        if(args.length > 0){
            app.login(args[0]);
        } else {
            /*Ask the user for their username.*/
            output(GUIConstants.ASK_FOR_USERNAME);
        }

        String userName = userInput();
        ArrayList<String[]> userList = interaction.getUserList();
        for(String[] userDBName: userList){
            if(userDBName[0].equals(userName)){
                app.login(userName);
               /*Display the user options for either search
                * for an item to add to cart, or to view
                * the current cart.
                */
                while (true) {
                    displayMainMenu();
                    /*Get user choice*/
                    String userInput = userInput();
                    if (userInput == null || userInput.equalsIgnoreCase(GUIConstants.EXIT)) {
                        app.logout();   //log the user out on exit
                        return;
                    }
                    handleMainMenuDecision(userInput);
                }
            }
        }
    }

    /**
     * Used at beginning of new menu to separate from previous menu.
     */
    private static void menuSeparator(){
        output("---------------------------------");
    }

    /**
     * Output a line of text to the user followed by newline char.
     * @param str string to display to the user.
     */
    private static void output(String str){
        System.out.println(str);
    }

    /**
     * Retrieve user input for a given task.
     * @return String representation of the user's input.
     */
    private static String userInput(){
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    private static void displayMainMenu(){
        menuSeparator();
        output("Welcome to your online shopping site.");
        output("0. Search items.");
        output(String.format("1. Display %s's cart.",app.getUsername()));
        output("Exit.");
    }

    /**
     * Handle the user's decision from the main menu.
     * @param index String of the user's choice 0-1
     */
    private static void handleMainMenuDecision(String index){
        switch(index){
            /*Display all items in database.*/
            case "0":
                displayItems(app.displayAvailableItems(), true);
                /*Shop until user types back.*/
                while(true){
                    if (!(shop())){
                        break;
                    }
                }
                break;
            /*Display user's cart.*/
            case "1":
                displayCart();
                break;
        }
    }

    /**
     * Display all items available for a given category.
     * Must ask user for the category.
     * @param items list of items to display.
     * @param askUser boolean flag to determine if the user should be asked
     *                for the category to display.
     */
    private static void displayItems(ArrayList<Item> items, boolean askUser){
        String[][] data = new String[items.size()+1][];
        data[0] = AppConstants.ITEM_DISPLAY_HEADERS;
        int counter = 1;
        /*Ask user for type of item to search through.*/
        if(askUser){
            /*For the Category case.*/
            String itemType;
            output(GUIConstants.ASK_FOR_ITEM_TYPE);
            while(true) {
                displayCategoryDecision();
                /*Get user choice*/
                String userInput = userInput();
                if(userInput.equalsIgnoreCase(GUIConstants.BACK)){  //exit condition
                    return; //go back to main menu.
                }
                itemType = handleCategoryDecision(userInput);
                /*If the return value from handling is not the same as the user's input,
                * then we have correctly handled the input. Continue to displaying the data.*/
                if(!itemType.equals(userInput)){
                    break;
                }
            }
            for(Item item: items){
                if(item.getItemType().equals(itemType)) {
                    data[counter] = item.displayItem().split(",");
                    counter++;
                }
            }
        } else {
            /*For the User Cart case.*/
            for (Item item : items) {
                data[counter] = item.displayItem().split(",");
                counter++;
            }
        }
        printTable(getProperStringArray(data));
    }

    /**
     * All the use to shop, which is adding items to cart.
     * @return true if continue shopping,
     *          false if stop shopping.
     */
    private static boolean shop(){
        menuSeparator();
        output("To add item to cart, enter '{id},{quantity}'");
        output("Back to return to previous menu.");
        String userInput = userInput();
        if(userInput.equalsIgnoreCase("back")){
            return false;
        }
        try {
            String[] splitUserInput = userInput.split(",");
            String id = splitUserInput[0];
            String quantity = splitUserInput[1];
            addItem(id, quantity);
        } catch(Exception e){
            //do nothing, user inputted incorrect format.
            //let loop ask again.
        }
        return true;
    }

    /**
     * Helper method for shopping to add item to user's cart.
     * @param id id of item to add to user's cart.
     * @param quantity quantity of the item to add to the user's cart.
     */
    private static void addItem(String id, String quantity){
        /*'add,{id},{quantity}'*/
        String formattedUpdate = "add,"+id+","+quantity;
        try {
            handleCartUpdate(formattedUpdate);
        }catch(Exception e){
            //do nothing, should never be reached.
        }
    }

    /**
     * Menu for giving the user options for categories of
     * items to view.
     */
    private static void displayCategoryDecision(){
        menuSeparator();
        output("Choose your category:");
        output("0. Books");
        output("1. Children Toys");
        output("2. Household Items");
        output("3. Small Electronics");
        output("Back.");
    }

    /**
     * Handling of the decision from the user of which decision
     * is given.
     * @param index user choice of 0-3
     * @return String representation of the category chosen.
     */
    private static String handleCategoryDecision(String index){
        switch(index){
            case "0":
                return AppConstants.BOOKS;
            case "1":
                return AppConstants.CHILDREN_TOYS;
            case "2":
                return AppConstants.HOUSEHOLD_ITEM;
            case "3":
                return AppConstants.SMALL_ELECTRONICS;
        }
        return index;
    }

    /**
     * Display the user's cart to the user.
     */
    private static void displayUserCart(){
        output(""+app.getUsername()+"'s Cart.");
        displayItems(app.getCurrentUser().getCart().getItems(), false);
    }

    /**
     * Get user input for the displayed cart and act on
     * the input.
     */
    private static void displayCart(){
        displayUserCart();
        while(true) {
            displayCartDecision();
                /*Get user choice*/
            String userInput = userInput();
            if(userInput.equalsIgnoreCase(GUIConstants.BACK)){  //exit condition
                return; //go back to cart menu.
            }
            handleCartDecision(userInput);
        }
    }

    /**
     * Display to the user what options are available when
     * looking at the cart.
     */
    private static void displayCartDecision(){
        menuSeparator();
        output("User Cart");
        output("0. View History.");
        output("1. Purchase Cart.");
        output("2. Update Cart.");
        output("Back.");
    }

    /**
     * Handle user's choice for what to do when looking at the cart.
     * @param index user's choice
     */
    private static void handleCartDecision(String index){
        switch(index){
            case "0":
                output("Previous Purchases >>");
                viewHistory();
                break;
            case "1":
                output("Are you sure you want to purchase your cart? >>");
                confirmPurchase();
                break;
            case "2":
                output("Update Cart >>");
                updateCart();
                break;
        }
    }

    /**
     * Ask the user to confirm to purchase the cart.
     */
    private static void confirmPurchase(){
        while(true){
            String userInput = userInput();
            switch(userInput){
                case "Y":
                case "y":
                    /*Purchase the cart if the user confirmed.*/
                    app.confirmPurchase();
                    return;
                case "N":
                case "n":
                    /*Cart not purchased, return to cart decisions.*/
                    return;
            }
        }
    }

    /**
     * View the history of the current logged in user.
     */
    private static void viewHistory(){
        ArrayList<Cart> previousPurchases = app.viewPreviousPurchases();
        for(Cart cart: previousPurchases){
            String[] cartArray = cart.toStringArray();
            Integer numOfItems = 0;
            for(Item item: cart.getItems()){
                numOfItems+=item.getQuantity();
            }
            String cartStr = "TotalPrice: "+cart.getTotalPrice()+" || TotalQuantity:"+numOfItems+" || ";
            for(String aCartArray : cartArray) {
                cartStr += aCartArray + " || ";
            }
            output(cartStr);
        }
    }

    /**
     * Update the cart and present to the user the
     * means to do so. Give the user the format
     * to input data.
     */
    private static void updateCart(){
        menuSeparator();
        displayUserCart();
        output("Update item in form of [{'add' or 'rem'},{id},{quantity}");
        output("Example: 'add,0,2'");
        try {
            handleCartUpdate(userInput());
        }catch(Exception e){
            output("Incorrect format for update. [{id},{quantity}]");
            //do nothing, use gave incorrect format.
        }
    }

    /**
     * Process the string of the user input for cart updates.
     * Update the cart accordingly.
     * @param update formatted string for updating cart.
     * @throws Exception if parsing fails due to user input format
     *      incorrect, throw Exception to be handled by calling method.
     */
    private static void handleCartUpdate(String update) throws Exception{
        String[] cartUpdate = update.split(",");
        String cmd = cartUpdate[0];
        ItemDatabase itemDB = new ItemDatabase();
        Integer id = Integer.parseInt(cartUpdate[1]);
        Item item = itemDB.getItem(id);
        if(item == null){
            output("Item does not exist.");
            return;
        }
        Integer quantity = Integer.parseInt(cartUpdate[2]);
        if(quantity <= 0 || quantity > item.getQuantity()){
            output("Invalid quantity given.");
            return;
        }
        if(cmd.equalsIgnoreCase("add")){
            app.addItemToCart(item,quantity);
            output("Item: " + id + " added with quantity: " + quantity);
        } else if(cmd.equalsIgnoreCase("rem")){
            if(app.getCurrentUser().getCart().itemExistsInCart(id)) {
                app.removeItemToCart(item, quantity);
                output("Item: " + id + " removed with quantity: " + quantity);
            } else{
                output("Item requested to remove is not in cart.");
                return;
            }
        }
        output("New total is: "+app.getCurrentUser().getCart().getTotalPrice());
    }

    /**
     * Print table of data.
     * http://stackoverflow.com/questions/275338/java-print-a-2d-string-array-as-a-right-justified-table
     * @param table data to be in table.
     */
    private static void printTable(String[][] table) {
        // Find out what the maximum number of columns is in any row
        int maxColumns = 0;
        for (String[] aTable : table) {
            maxColumns = Math.max(aTable.length, maxColumns);
        }

        // Find the maximum length of a string in each column
        int[] lengths = new int[maxColumns];
        for (String[] aTable : table) {
            for (int j = 0; j < aTable.length; j++) {
                lengths[j] = Math.max(aTable[j].length(), lengths[j]);
            }
        }

        // Generate a format string for each column
        String[] formats = new String[lengths.length];
        for (int i = 0; i < lengths.length; i++) {
            formats[i] = "%1$" + lengths[i] + "s"
                    + (i + 1 == lengths.length ? "\n" : " ");
        }

        // Print 'em out
        for (String[] aTable : table) {
            for (int j = 0; j < aTable.length; j++) {
                System.out.printf(formats[j], aTable[j]);
            }
        }
    }

    private static String[][] getProperStringArray(String[][] strArray){
        ArrayList<String[]> tempList = new ArrayList<>();
        for(String[] str: strArray){
            if(str == null){
                break;
            }
            tempList.add(str);
        }
        String[][] properStringArray = new String[tempList.size()][];
        for(int i = 0; i < tempList.size(); i++){
            properStringArray[i] = tempList.get(i);
        }
        return properStringArray;
    }
}
