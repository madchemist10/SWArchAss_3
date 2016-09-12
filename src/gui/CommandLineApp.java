package gui;

import application.Application;
import application.Interaction;
import user.User;
import constants.AppConstants;
import constants.GUIConstants;
import inventory.Item;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is the user interaction with the system through a command line application.
 */
public class CommandLineApp {
    private static Application app;
    private static Interaction interaction;

    public static void main(String[] args) {
        app = new Application();
        interaction = new Interaction();
        ArrayList<String[]> userList = new ArrayList<>();
        /*If the application has been given a command line
        * argument, the first element must be the username.*/
        if(args.length > 0){
            app.login(args[0]);
        } else {
            /*Ask the user for their username.*/
            output(GUIConstants.ASK_FOR_USERNAME);
        }

        String userName = userInput();
        userList = interaction.getUserList();
        if (userList == null)
        {
            output("Username not found.");
        }
        else if (userList.contains(new String[] {userName}))
        {
           /*Display the user options for either search
            * for an item to add to cart, or to view
            * the current cart.
            */
            while(true) {
                displayMainMenu();
                /*Get user choice*/
                String userInput = userInput();
                if(userInput == null || userInput.equalsIgnoreCase(GUIConstants.EXIT)){
                    app.logout();   //log the user out on exit
                    return;
                }
                handleMainMenuDecision(userInput);
            }
        }
    }

    /**
     * Output a line of text to the user followed by newline char.
     * @param str string to display to the user.
     */
    private static void output(String str){
        System.out.println(str);
    }

    private static String userInput(){
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    private static void displayMainMenu(){
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
        String itemType = null;
        /*Ask user for type of item to search through.*/
        if(askUser){
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
        }
        for(Item item: items){
            if(itemType != null && item.getItemType().equals(itemType)) {
                output(item.displayItem());
            }
        }
    }

    /**
     * Menu for giving the user options for categories of
     * items to view.
     */
    private static void displayCategoryDecision(){
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

    private static void displayCart(){
        output(""+app.getUsername()+"'s Cart.");
        displayItems(app.getCurrentUser().getCart().getItems(), false);
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
        output("0. Purchase Cart.");
        output("Back.");
    }

    /**
     * Handle user's choice for what to do when looking at the cart.
     * @param index user's choice
     */
    private static void handleCartDecision(String index){
        switch(index){
            case "0":
                output("Are you sure you want to purchase your cart? >>");
                confirmPurchase();
                break;
            case "1":
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
                    app.getCurrentUser().purchaseCart();
                    return;
                case "N":
                case "n":
                    /*Cart not purchased, return to cart decisions.*/
                    return;
            }
        }
    }
}
