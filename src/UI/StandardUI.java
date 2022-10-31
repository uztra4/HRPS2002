package UI;

import java.util.Scanner;

/***
 * Implements logic for standard UI function
 * 
 * @version 1.0
 * @since 2022-04-17
 */
public abstract class StandardUI {
    int choice;
    int qSize;
    Scanner sc;

    /**
     * Constructor
     */
    public StandardUI() {
        sc = new Scanner(System.in);
    }

    /**
     * Abstract method for selection menu
     * 
     * @return int
     */
    public abstract int showSelection();

    /**
     * Abstrat method to for selection logic
     */
    public abstract void mainMenu();

    /**
     * Error checking function for user choice selection
     * 
     * @param n Range number of inputs
     * @return int User selection
     * 
     */
    public int getUserChoice(int n) {
        do {
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                sc.nextLine();
                if (choice <= 0 || choice > n)
                    System.out.println("Please input values between 1 to " + n + " only!");
                else
                    break;
            } else {
                System.out.println("Please input only integers!");
                choice = n + 1;
                sc.next();
            }
        } while (choice <= 0 || choice > n);

        return choice;
    }

    /**
     * Converts User input string to uppercase to avoid mismatch case
     * 
     * @return String return uppercase of input
     */
    public String getUserString() {
        String input = sc.nextLine().toUpperCase();
        return input;
    }

    /**
     * Error check user yes/no selection
     * 
     * @return String Return user selection
     */
    public String getUserYN() {
        String select = getUserString();
        while (!(select.compareToIgnoreCase("Y") == 0 || select.compareToIgnoreCase("N") == 0)) {
            System.out.println("Please enter only Y/N");
            System.out.println("Y/N? ");
            select = getUserString();
            System.out.println(select);
        }
        return select;
    }
}
