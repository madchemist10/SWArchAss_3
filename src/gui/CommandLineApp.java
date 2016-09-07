package gui;

import application.Application;

/**
 * This is the user interaction with the system through a command line application.
 */
public class CommandLineApp {

    public static void main(String[] args) {
        Application app = new Application();
        /*If the application has been given a command line
        * argument, the first element must be the username.*/
        if(args.length > 0){
            app.login(args[0]);
        } else {
            /*Ask the user for their username.*/
        }

        /*Display the user options for either search
        * for an item to add to cart, or to view
        * the current cart.*/
    }
}
