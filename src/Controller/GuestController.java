package Controller;

import java.util.ArrayList;
import java.util.Date;

import Database.SerializeDB;
import Entity.Creditcard;
import Entity.Entities;
import Entity.Guest;

/***
 * Represents a Guest Controller
 * 
 * @version 1.0
 * @since 2022-04-17
 */

public class GuestController extends SerializeDB implements IController, IStorage {
    /**
     * The Instance of this Controller
     */
    private static GuestController instance = null;
    /**
     * The collection of guest
     */
    private ArrayList<Guest> guestList;

    /**
     * Constructor
     */
    private GuestController() {
        guestList = new ArrayList<>();
    }

    /**
     * Returns the GuestController instance and creates an instance if it does not exist
     * 
     * @return GuestController instance
     */
    public static GuestController getInstance() {
        if (instance == null) {
            instance = new GuestController();
        }
        return instance;
    }

    /**
     * Return Guest object if guestID matches
     * 
     * @param guestID	ID of desired guest
     * @return guest	Guest object with corresponding guestID
     */
    public Guest checkExistence(String guestID) {
        System.out.println("Checking whether guestID exists...");
        for (Guest guest : guestList) {
            if (guest.getID().equals(guestID)) {
                System.out.println(guestID);
                return guest;
            }
        }
        return null;
    }

    /**
     * Downcast to Guest and add to list of Guests
     * 
     * @param entities
     */
    public void create(Object entities) {

        Guest guest = (Guest) entities;
        guestList.add(guest);
        storeData();
    }

    /**
     * Print all GuestIDs
     */
    public void read() {
        for (Guest guest : guestList) {
            System.out.println(guest.getID());
        }
    }

    /**
     * Delete single Guest Object from list of Guests
     * 
     * @param entities
     */
    public void delete(Object entities) {

        Guest toBeDeleted = (Guest) entities;
        guestList.remove(toBeDeleted);

        storeData();
    }

    /**
     * Update field of Guest with input values
     * 
     * @param entities entities is Guest
     * @param choice   choice from UI
     * @param value    input from user to be pass to setters
     */
    public void update(Object entities, int choice, String value) {

        Guest toBeUpdated = (Guest) entities;
        switch (choice) {
            case 1: // guestID:
                toBeUpdated.setGuestID(value);
                break;
            case 2: // guestName:
                toBeUpdated.setGuestName(value);
                break;
            case 3: // address:
                toBeUpdated.setAddress(value);
                break;
            case 4: // contact:
                toBeUpdated.setContact(value);
                break;
            case 5: // country:
                toBeUpdated.setCountry(value);
                break;
            case 6: // gender:
                toBeUpdated.setGender(value.charAt(0));
                break;
            case 7: // nationality:
                toBeUpdated.setNationality(value);
                break;
            default:
                break;
        }

        System.out.println(toBeUpdated.toString());
        storeData();
    }

    /**
     * Update Guest Creditcard details with setter
     * 
     * @param entities   entities is Guest
     * @param cardNumber
     * @param expiryDate
     * @param CVC
     * @param type
     * @param cardName
     */
    public void updateCreditcard(Object entities, String cardNumber, Date expiryDate, int CVC, int type,
            String cardName) {
        Guest toBeUpdated = (Guest) entities;
        toBeUpdated.setCard(new Creditcard(cardNumber, expiryDate, CVC, type, cardName));
    }

    /**
     * Store list of Guests into serializable file
     */
    public void storeData() {
        super.storeData("Guest.ser", guestList);

    }

    /**
     * Loads list of Guests from serializable file
     */
    public void loadData() {
        ArrayList<Entities> data = super.loadData("Guest.ser");
        guestList.clear();
        for (Entities guest : data)
            guestList.add((Guest) guest);
    }

}
