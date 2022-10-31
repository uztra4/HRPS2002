package UI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import Mediator.CheckInOut;

/***
 * Represents a Check in check out UI
 * 
 * @version 1.0
 * @since 2022-04-17
 */
public class CheckInOutUI extends StandardUI {
    /**
     * The Instance of this Controller
     */
    private static CheckInOutUI instance = null;

    /**
     * Constructor
     */
    private CheckInOutUI() {
        sc = new Scanner(System.in);
    }

    /**
     * Returns the CheckInOutUI instance and creates an instance if it does not
     * 
     * @return CheckInOutUI
     */
    public static CheckInOutUI getInstance() {
        if (instance == null)
            instance = new CheckInOutUI();
        return instance;
    }

    /**
     * Print selection menu and return number of selections available
     * 
     * @return int
     */
    public int showSelection() {
        System.out.println(" Check In/Out options avaiable: ");
        System.out.println("1) Check In");
        System.out.println("2) Check Out");
        System.out.println("3) Make Payment");
        System.out.println("4) Change assigned room");
        System.out.println("5) Return to MainUI");

        return 5;
    }

    /**
     * Select next prompt based on user iuput
     * 
     */
    public void mainMenu() {
        do {
            qSize = showSelection();
            choice = getUserChoice(qSize);
            String reservationID;
            switch (choice) {
                case 1: // checkIn
                    System.out.println("Have Reservations?(Y/N)");
                    String select = getUserYN();
                    if (select.compareTo("N") == 0) {
                        System.out.println("Please create Reservation first.");
                        System.out.println("Are you a new Guest? (Y/N)");
                        select = getUserYN();
                        if (select.compareTo("Y") == 0) {
                            System.out.println("Please create Guest account first.");
                            GuestUI.getInstance().create();
                        }
                        ReservationUI.getInstance().create();
                    }
                    System.out.println("Enter reservation ID: ");
                    reservationID = getUserString();
                    CheckInOut.getInstance().checkIn(reservationID);
                    break;
                case 2: // checkOut
                    checkOut();
                    break;
                case 3: // payment
                    payment();
                    break;
                case 4:
                	System.out.println("Enter reservation ID: ");
                	reservationID = getUserString();
                	System.out.println("Enter room ID of new room: ");
                	String roomID = getUserString();
                	CheckInOut.getInstance().changeRoom(reservationID, roomID);
                    break;
                case 5:
                	return;
            }
        } while (choice < qSize);
    }

    /**
     * Check out the room
     * Prompts for user input based on discount that can be applied
     * to Room and Order separately
     */
    private void checkOut() {
        String ID, select;
        System.out.println("Enter room ID: ");
        ID = getUserString();

        float roomDiscount = 0;
        float orderDiscount = 0;

        // Check for room discounts
        System.out.println("Any Room Discounts? (Y/N)");
        select = getUserYN();
        if (select.compareTo("Y") == 0) {
            System.out.println("Room Discount (%): ");
            roomDiscount = (float) getUserChoice(100) / 100;
        }

        // Check for order discounts
        System.out.println("Any Order Discounts? (Y/N)");
        select = getUserYN();
        if (select.compareTo("Y") == 0) {
            System.out.println("Order Discount (%): ");
            orderDiscount = (float) getUserChoice(100) / 100;
        }

        // Run checkout
        CheckInOut.getInstance().checkOut(ID, roomDiscount, orderDiscount);
    }

    /**
     * Prompts for payment by cash or card
     * Assign card to reservation if haven't
     */
    private void payment() {
        String ID;
        System.out.println("Enter reservation ID: ");
        ID = getUserString();
        System.out.println("Payment by?");
        System.out.println("1) Cash");
        System.out.println("2) Credit/Debit Card");
        choice = getUserChoice(2);
        if (choice == 2) {
            System.out.println("Enter Creditcard Number: ");
            String cardNumber = getUserString();
            System.out.println("Enter Creditcard Expiry Date (MM/yy): ");
            Date expDate = getValidDate(getUserString());
            expDate = validCreditcardDate(expDate);
            System.out.println("Enter Creditcard CVV: ");
            int CVV = getUserChoice(999);
            System.out.println("Enter Creditcard Type: ");
            System.out.println("1) VISA");
            System.out.println("2) MASTER");
            System.out.println("3) AMEX");
            int type = getUserChoice(3);
            System.out.println("Enter Creditcard Registered Name: ");
            String cardName = getUserString();
            CheckInOut.getInstance().setReservationCreditcard(ID, cardNumber, expDate, CVV, type, cardName);
        }
        CheckInOut.getInstance().payment(ID);
        System.out.println("Payment Completed");
    }

    /**
     * Error check from date input
     * 
     * @param dateInString
     * @return Date
     */
    private Date getValidDate(String dateInString) {
        SimpleDateFormat sdfrmt = new SimpleDateFormat("MM/yy");
        sdfrmt.setLenient(false);
        Date javaDate = null;
        while (javaDate == null) {
            try {
                javaDate = sdfrmt.parse(dateInString);
            } catch (ParseException e) {
                System.out.println(dateInString + " is Invalid Date format\nEnter date: ");
                System.out.println("Enter date (MM/yy): ");
                dateInString = getUserString();
            }
        }

        return javaDate;
    }

    /**
     * Error check for credit card expiry
     * 
     * @param expDate
     * @return Date
     */
    private Date validCreditcardDate(Date expDate) {
        Date today = new Date();
        while (expDate.before(today)) {
            System.out.println("Card expiry must not be before today");
            System.out.println("Enter Creditcard Expiry Date (MM/yy): ");
            String expDateString = getUserString();
            expDate = getValidDate(expDateString);
        }
        return expDate;
    }
}