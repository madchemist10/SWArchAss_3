package gui;

import application.Application;
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

    public static void main(String[] args) {
        app = new Application();
        /*If the application has been given a command line
        * argument, the first element must be the username.*/
        if(args.length > 0){
            app.login(args[0]);
        } else {
            /*Ask the user for their username.*/
            output(GUIConstants.ASK_FOR_USERNAME);
        }

        /*Display the user options for either search
        * for an item to add to cart, or to view
        * the current cart.*/
        while(true) {
            displayMainMenu();
            /*Get user choice*/
            String userInput = userInput();
            if(userInput == null || userInput.equalsIgnoreCase(GUIConstants.EXIT)){
                return;
            }
            handleMainMenuDecision(userInput);
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
        output("0. Display All items.");
        output(String.format("1. Display %s's cart.",app.getUsername()));
        output("Exit.");
    }

    private static void handleMainMenuDecision(String index){
        switch(index){
            /*Display all items in database.*/
            case "0":
                displayItems(app.displayAvailableItems(), true);
                break;
            /*Display user's cart.*/
            case "1":
                displayItems(app.getCurrentUser().getCart().getItems(), false);
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
                if(userInput.equalsIgnoreCase(GUIConstants.BACK)){
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

    private static void displayCategoryDecision(){
        output("Choose your category:");
        output("0. Books");
        output("1. Children Toys");
        output("2. Household Items");
        output("3. Small Electronics");
        output("Back.");
    }

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
}
