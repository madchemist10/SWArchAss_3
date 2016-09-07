package gui;

import application.Application;
import constants.GUIConstants;
import inventory.Item;

import java.util.ArrayList;
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
    }

    /**
     * Output a line of text to the user followed by newline char.
     * @param str string to display to the user.
     */
    private static void output(String str){
        System.out.println(str);
    }

    private static void displayMainMenu(){

    }

    private static void handleMainMenuDecision(int index){
        switch(index){
            /*Display all items in database.*/
            case 0:
                displayItems(app.displayAvailableItems());
                break;
            /*Display user's cart.*/
            case 1:
                break;
        }
    }

    private static void displayItems(ArrayList<Item> items){
        for(Item item: items){
            String itemName = item.getName();
            String description = item.getDescription();
            Double itemPrice = item.getPrice();
        }
    }
}
