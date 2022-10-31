package UI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.Scanner;

import Controller.GuestController;
import Controller.ReservationController;
import Entity.Reservation;
import Enums.ReservationStatus;
import Enums.RoomTypes;
import Mediator.CheckInOut;

/***
 * Represents a ReservationUI
 * 
 * @version 1.0
 * @since 2022-04-17
 */
public class ReservationUI extends StandardUI implements ControllerUI {
    /**
     * The Instance of this Controller
     */
    private static ReservationUI instance = null;
    Scanner sc;
    int choice, qSize;

    /**
     * Constructor
     */
    private ReservationUI() {
        sc = new Scanner(System.in);
    }

    /**
     * Returns the ReservationUI instance and creates an instance if it does not
     * 
     * @return ReservationUI
     */
    public static ReservationUI getInstance() {
        if (instance == null) {
            instance = new ReservationUI();
        }
        return instance;
    }

    /**
     * Print selection menu and return number of selections available
     * 
     * @return int
     */
    public int showSelection() {
        System.out.println("Reservation options avaiable: ");
        System.out.println("1) Add Reservations");
        System.out.println("2) View Reservations");
        System.out.println("3) Update Reservation Info");
        System.out.println("4) View Reservation Info");
        System.out.println("5) Cancel Reservation");
        System.out.println("6) Return to MainUI");

        return 6;
    }

    /**
     * Select next prompt based on user iuput
     * 
     */
    public void mainMenu() {
        do {
            qSize = showSelection();
            choice = getUserChoice(qSize);

            switch (choice) {
                case 1:
                    System.out.println("Are you a new Guest? (Y/N)");
                    String select = getUserYN();
                    if (select.compareTo("Y") == 0) {
                        System.out.println("Please create Guest account first.");
                        GuestUI.getInstance().create();
                    }
                    create();
                    break;
                case 2:
                    readOneDets();
                    //ReservationController.getInstance().read();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    viewRInfo();
                    break;
                case 5:
                    delete();
                    break;
                case 6:
                    break;
            }

        } while (choice < qSize);
    }

    /**
     * Only create Reservation if guest exist
     * Create Reservation object with inputs
     */
    public void create() {

        System.out.println("Enter Guest ID: ");
        String guestID = getUserString();
        if (GuestController.getInstance().checkExistence(guestID) == null) {
            System.out.println("Invalid Guest ID");
            return;
        }

        // RoomID to be filled via checkin
        System.out.println("Enter Check-in day (dd/MM/yy): ");
        String checkInString = getUserString() + " 09:00 AM";
        Date checkInDate = validCheckIn(dateValid(checkInString));

        if (ReservationController.getInstance().checkExistence(guestID + checkInDate) != null) {
            System.out.println("Reservation already exist!");
            return;
        }

        System.out.println("Enter Check-out day (dd/MM/yy):");
        String checkOutString = getUserString() + " 12:00 PM";
        Date checkOutDate = validCheckOut(dateValid(checkOutString), checkInDate);

        System.out.println("Enter number of children: ");
        int numOfChild = sc.nextInt();

        System.out.println("Enter number of adults: ");
        int numOfAdults = sc.nextInt();

        Reservation rawReservation = new Reservation(guestID, checkInDate,
                checkOutDate, numOfChild,
                numOfAdults);

        int checkAvailability;
        RoomTypes roomType = RoomTypes.SINGLE; // Pre-set as single to avoid errors
        while (true) {
            System.out.println(
                    "1) Single\n"
                            + "2) Double\n"
                            + "3) Standard\n"
                            + "4) Deluxe\n"
                            + "5) Suite\n"
                            + "6) Cancel create\n"
                            + "Select Room Type: ");
            choice = getUserChoice(9);
            switch (choice) {
                case 1:
                    roomType = RoomTypes.SINGLE;
                    break;
                case 2:
                    roomType = RoomTypes.DOUBLE;
                    break;
                case 3:
                    roomType = RoomTypes.STANDARD;
                    break;
                case 4:
                    roomType = RoomTypes.DELUXE;
                    break;
                case 5:
                    roomType = RoomTypes.SUITE;
                    break;
                case 6:
                    return;
                default:
                    break;
            }
            checkAvailability = CheckInOut.getInstance().numAvailability(rawReservation.getCheckIn(), roomType);
            if (checkAvailability <= 0) {
                System.out.println("Room type not available!");
                System.out.println("Put on waitlist? (Y/N)");
                String select = getUserYN();
                if (select.compareTo("Y") == 0) {
                    rawReservation.setReservationStatus(ReservationStatus.WAITLIST);
                    break;
                }
            } else {
                rawReservation.setReservationStatus(ReservationStatus.CONFIRM);
                break;
            }
        }

        rawReservation.setRoomType(roomType);

        ReservationController.getInstance().create(rawReservation);

        System.out.println(
                "================================================================================================================================");
        System.out.println(
                " Reservation ID      Guest ID      Room ID        Room Type         Status          Check-in Date          Check-out Date      ");
        System.out.println(
                "================================================================================================================================");
        System.out.println(rawReservation.toString().replace("[", "").replace("]", ""));
        System.out.println(
                "================================================================================================================================");
        System.out.println("Reservation " + rawReservation.getID() + " has been created");

    }

    /**
     * Check for Reservation existence and print details of specific reservation
     */
    public void readOneDets() {

        System.out.println("Enter ur Reservation ID: ");
        String reservationID = getUserString();

        Reservation reserveRead = ReservationController.getInstance().checkExistence(reservationID);
        if (reserveRead != null) {
            System.out.format("\033[1mViewing reservation %s\033[0m\n", reserveRead.getID());
            System.out.println(
                    "================================================================================================================================");
            System.out.println(
                    " Reservation ID      Guest ID      Room ID        Room Type         Status          Check-in Date          Check-out Date      ");
            System.out.println(
                    "================================================================================================================================");
            System.out.println(reserveRead.toString().replace("[", "").replace("]", ""));
            System.out.println(
                    "================================================================================================================================");
        } else
            System.out.println("Reservation does not exist");

    }

    /**
     * Show all reservation info by reservation status
     */

    public void viewRInfo()
    {
        do
        {
            System.out.println("1) Print All Confirmed Reservations");
            System.out.println("2) Print All Check In Reservations");
            System.out.println("3) Print All Check out Reservations");
            System.out.println("4) Print All Waitlist Reservations");
            System.out.println("5) Print All Expired Reservations");
            System.out.println("6) Print All Reservations (without expired and waitlist)");
            System.out.println("7) Print All Reservations (without expired)");
            System.out.println("8) Print by Date");
            System.out.println("9) Back to Reservation UI");

            choice = getUserChoice(9);
            switch(choice)
            {
                case 1:
                    viewConfirmed();
                    break;
                case 2:
                    viewCheckin();
                    break;
                case 3:
                    viewCompleted();
                    break;
                case 4:
                    viewWaitlist();
                    break;
                case 5:
                    viewExpired();
                    break;
                case 6:
                    viewAllnoExpirednWaitlist();
                    break;
                case 7:
                    viewAllnoExpired();
                    break;
                case 8:
                    viewbyDate();
                    break;
                case 9:
                    return;
                default:
                    break;
            }
        } while (choice < 10);

    }

    /**
     * Show all Reservation in a list except expired
     */
    public void viewAllnoExpired() {
        
        
        System.out.println("\033[1mReservation Statistics Summary\033[0m\n");
        System.out.format("Confirmed: %d \tChecked in: %d\tChecked out: %d\t Waitlist: %d\t Expired: %d\n", numofStatus(1), numofStatus(2), numofStatus(3), numofStatus(4), numofStatus(5));
        System.out.println("\033[1mViewing all reservations\033[0m");
        System.out.println(
                "================================================================================================================================");
        System.out.println(
                " Reservation ID      Guest ID      Room ID        Room Type         Status          Check-in Date          Check-out Date      ");
        System.out.println(
                "================================================================================================================================");
        for (Reservation reservations : ReservationController.getInstance().getReservationList())
            if (reservations.getReservationStatus() != ReservationStatus.EXPIRED) //task: print but dont show expired
                System.out.println(reservations.toString().replace("[", "").replace("]", ""));
        System.out.println(
                "================================================================================================================================");
    }
        /**
     * Show all Reservation in a list except expired
     */
    public void viewAllnoExpirednWaitlist() {
        
        
        System.out.println("\033[1mReservation Statistics Summary\033[0m");
        System.out.format("Confirmed: %d \tChecked in: %d\tChecked out: %d\t Waitlist: %d\t Expired: %d\n", numofStatus(1), numofStatus(2), numofStatus(3), numofStatus(4), numofStatus(5));
        System.out.println("\033[1mViewing all reservations\033[0m");
        System.out.println(
                "================================================================================================================================");
        System.out.println(
                " Reservation ID      Guest ID      Room ID        Room Type         Status          Check-in Date          Check-out Date      ");
        System.out.println(
                "================================================================================================================================");
        for (Reservation reservations : ReservationController.getInstance().getReservationList())
            if (reservations.getReservationStatus() != ReservationStatus.EXPIRED && reservations.getReservationStatus() != ReservationStatus.WAITLIST) //task: print but dont show waitlist and expired
                System.out.println(reservations.toString().replace("[", "").replace("]", ""));
        System.out.println(
                "================================================================================================================================");
    }
    
    /**
     * Print all confirmed reservations
     */
    public void viewConfirmed()
    {
        System.out.println(
            "================================================================================================================================");
        System.out.println(
                " Reservation ID      Guest ID      Room ID        Room Type         Status          Check-in Date          Check-out Date      ");
        System.out.println(
                "================================================================================================================================");
        for (Reservation reservations : ReservationController.getInstance().getReservationList())
            if (reservations.getReservationStatus() == ReservationStatus.CONFIRM) //task: print but dont show waitlist
                System.out.println(reservations.toString().replace("[", "").replace("]", ""));
        System.out.println(
                "================================================================================================================================");
    }

    /**
     * Print all checkin reservations
     */
    public void viewCheckin()
    {
        System.out.println(
            "================================================================================================================================");
        System.out.println(
                " Reservation ID      Guest ID      Room ID        Room Type         Status          Check-in Date          Check-out Date      ");
        System.out.println(
                "================================================================================================================================");
        for (Reservation reservations : ReservationController.getInstance().getReservationList())
            if (reservations.getReservationStatus() == ReservationStatus.CHECKIN) 
                System.out.println(reservations.toString().replace("[", "").replace("]", ""));
        System.out.println(
                "================================================================================================================================");
    }

    /**
     * Print all completed reservations
     */
    public void viewCompleted()
    {
        System.out.println(
            "================================================================================================================================");
        System.out.println(
                " Reservation ID      Guest ID      Room ID        Room Type         Status          Check-in Date          Check-out Date      ");
        System.out.println(
                "================================================================================================================================");
        for (Reservation reservations : ReservationController.getInstance().getReservationList())
            if (reservations.getReservationStatus() == ReservationStatus.COMPLETED) 
                System.out.println(reservations.toString().replace("[", "").replace("]", ""));
        System.out.println(
                "================================================================================================================================");
    }

    /**
     * Print all expired reservations
     */
    public void viewExpired()
    {
        System.out.println(
            "================================================================================================================================");
        System.out.println(
                " Reservation ID      Guest ID      Room ID        Room Type         Status          Check-in Date          Check-out Date      ");
        System.out.println(
                "================================================================================================================================");
        for (Reservation reservations : ReservationController.getInstance().getReservationList())
            if (reservations.getReservationStatus() == ReservationStatus.EXPIRED)
                System.out.println(reservations.toString().replace("[", "").replace("]", ""));
        System.out.println(
                "================================================================================================================================");
    }

    /**
     * Print all waitlist reservations
     */
    public void viewWaitlist()
    {
        System.out.println(
            "================================================================================================================================");
        System.out.println(
                " Reservation ID      Guest ID      Room ID        Room Type         Status          Check-in Date          Check-out Date      ");
        System.out.println(
                "================================================================================================================================");
        for (Reservation reservations : ReservationController.getInstance().getReservationList())
            if (reservations.getReservationStatus() == ReservationStatus.WAITLIST) 
                System.out.println(reservations.toString().replace("[", "").replace("]", ""));
        System.out.println(
                "================================================================================================================================");
    }
    
    /**
     * Print all ongoing reservations by date
     */
    public void viewbyDate()
    {
        System.out.println("Enter date: ");
        String input = getUserString() + " 12:00 AM";
        Date date = dateValid(input);
        System.out.println(
            "================================================================================================================================");
        System.out.println(
                " Reservation ID      Guest ID      Room ID        Room Type         Status          Check-in Date          Check-out Date      ");
        System.out.println(
                "================================================================================================================================");
        for (Reservation reservations : ReservationController.getInstance().getReservationList())
        {
            if (removeTime(date).equals(removeTime(reservations.getCheckIn()))) 
                System.out.println(reservations.toString().replace("[", "").replace("]", ""));
        }
        System.out.println(
                "================================================================================================================================");
    }

    /**
     * Calculate number of reservation status
     */
    public int numofStatus(int i)
    {
        int count = 0;
        switch(i)
        {
            case 1: //confirm
                for (Reservation reservations : ReservationController.getInstance().getReservationList())
                {
                    if (reservations.getReservationStatus() == ReservationStatus.CONFIRM) 
                        count += 1;
                }
                break;
            case 2://check in
                for (Reservation reservations : ReservationController.getInstance().getReservationList())
                {
                    if (reservations.getReservationStatus() == ReservationStatus.CHECKIN) 
                        count += 1;
                }
                break;
            case 3: //completed
                for (Reservation reservations : ReservationController.getInstance().getReservationList())
                {
                    if (reservations.getReservationStatus() == ReservationStatus.COMPLETED) 
                        count += 1;
                }
                break;
            case 4: //waitlist
                for (Reservation reservations : ReservationController.getInstance().getReservationList())
                {
                    if (reservations.getReservationStatus() == ReservationStatus.WAITLIST) 
                        count += 1;
                }
                break;
            case 5: //expired
                for (Reservation reservations : ReservationController.getInstance().getReservationList())
                {
                    if (reservations.getReservationStatus() == ReservationStatus.EXPIRED) 
                        count += 1;
                }
                break;
            default:
                break;
        }
        return count;
    }

    /**
     * Check for reservation existence and prompts for arguments for update
     */
    public void update() {
        System.out.println("Enter your Reservation ID: ");
        String reservationID = getUserString();

        Reservation toBeUpdated = ReservationController.getInstance().checkExistence(reservationID);
        if (toBeUpdated == null) {
            System.out.println("Reservation does not exist");
        } else {
            System.out.println("What do u want to update?");
            System.out.println("1) Guest ID");
            System.out.println("2) Room ID");
            System.out.println("3) Check In Date (dd/MM/yy)");
            System.out.println("4) Check Out Date (dd/MM/yy)");
            System.out.println("5) Number of Child(s)");
            System.out.println("6) Number of Adult(s)");
            System.out.println("7) Reservation Status");
            System.out.println("8) Room Type");

            choice = getUserChoice(8);
            int selection;
            String content;

            if (choice == 3) {
                System.out.println("Enter Check-in day (dd/MM/yy): ");
                String checkInString = getUserString() + " 09:00 AM";
                Date checkInDate = validCheckIn(dateValid(checkInString));
                content = new SimpleDateFormat("dd/MM/yy hh:mm a").format(checkInDate);
            } else if (choice == 4) {
                Date checkInDate = toBeUpdated.getCheckIn();
                System.out.println("Enter Check-out day (dd/MM/yy):");
                String checkOutString = getUserString() + " 12:00 PM";
                Date checkOutDate = validCheckOut(dateValid(checkOutString), checkInDate);
                content = new SimpleDateFormat("dd/MM/yy hh:mm a").format(checkOutDate);
            } else if (choice == 7) {
                System.out.println(
                        "1) Confirmed\n"
                                + "2) Checked In\n"
                                + "3) Expired\n"
                                + "4) Completed\n"
                                + "5) Waitlist\n"
                                + "Select Status: ");
                selection = getUserChoice(5);
                content = String.valueOf(selection);
            } else if (choice == 8) {
                int checkAvailability;
                RoomTypes roomType = RoomTypes.SINGLE; // Pre-set as single to avoid errors
                while (true) {
                    System.out.println(
                            "1) Single\n"
                                    + "2) Double\n"
                                    + "3) Deluxe\n"
                                    + "4) Suite\n"
                                    + "5) Cancel reservation\n"
                                    + "Select Room Type: ");
                    selection = getUserChoice(5);
                    switch (choice) {
                        case 1:
                            roomType = RoomTypes.SINGLE;
                            break;
                        case 2:
                            roomType = RoomTypes.DOUBLE;
                            break;
                        case 3:
                            roomType = RoomTypes.DELUXE;
                            break;
                        case 4:
                            roomType = RoomTypes.SUITE;
                            break;
                        case 5:
                            return;
                        default:
                            break;
                    }
                    checkAvailability = CheckInOut.getInstance().numAvailability(toBeUpdated.getCheckIn(), roomType);
                    if (checkAvailability <= 0) {
                        System.out.println("Room type not available!");
                        System.out.println("Put on waitlist? (Y/N)");
                        String select = getUserYN();
                        if (select.compareTo("Y") == 0) {
                            ReservationController.getInstance().update(toBeUpdated, 7, "5");
                            break;
                        }
                    } else {
                        ReservationController.getInstance().update(toBeUpdated, 7, "1");
                        break;
                    }
                }
                content = String.valueOf(selection);
            } else {
                System.out.println("Enter the relevant details: ");
                content = getUserString();
            }

            ReservationController.getInstance().update(toBeUpdated, choice, content);

            // System.out.println(toBeUpdated);
        }
    }

    /**
     * Check for Reservation existence and
     * pass reservation bbject to controller for delete
     */
    public void delete() {
        System.out.println("Enter your Reservation ID: ");
        String reservationID = getUserString();
        Reservation toBeDeleted = ReservationController.getInstance().checkExistence(reservationID);
        if (toBeDeleted == null) {
            System.out.println("Reservation does not exist");
        }
        else if (ReservationController.getInstance().checkExistence(reservationID).getReservationStatus() == ReservationStatus.CHECKIN)
        {
            System.out.println("Reservation cannot be cancelled, guests has checked in.");
        }
        else {
            ReservationController.getInstance().delete(toBeDeleted);
            System.out.println("Reservation Cancelled.");
        }
    }

    /**
     * Check for validity of date and prompts for correct input
     * 
     * @param dateInString Date from user string input
     * @return Date
     */
    private Date dateValid(String dateInString) {
        SimpleDateFormat sdfrmt = new SimpleDateFormat("dd/MM/yy hh:mm a");
        int i = dateInString.length();
        String time = dateInString.substring(i - 9, i);
        sdfrmt.setLenient(false);
        Date javaDate = null;
        while (javaDate == null) {
            try {
                javaDate = sdfrmt.parse(dateInString);
            } catch (ParseException e) {
                System.out.println(dateInString + " is Invalid Date format\nEnter date: ");
                System.out.println("Enter date (dd/MM/yy): ");
                dateInString = getUserString() + time;
            }
        }

        return javaDate;
    }

    /**
     * Check for valid check in on the day where guest visits
     * 
     * @param checkInDate
     * @return Date
     */
    private Date validCheckIn(Date checkInDate) {
        Date today = new Date();
        while (checkInDate.before(removeTime(today))) {
            System.out.println("Check-in day must not be before today");
            System.out.println("Enter Check-in day (dd/MM/yy): ");
            String checkInString = getUserString() + " 09:00 AM";
            checkInDate = dateValid(checkInString);
        }
        return checkInDate;
    }

    /**
     * Check for valid check out date entered, must be after check in date
     * 
     * @param checkOutDate
     * @param checkInDate
     * @return Date
     */
    private Date validCheckOut(Date checkOutDate, Date checkInDate) {
        while (checkOutDate.before(checkInDate) || checkOutDate.equals(checkInDate)) {
            System.out.println("Check-out day must be after Check-in day");
            System.out.println("Enter Check-out day (dd/MM/yy): ");
            String checkOutString = getUserString() + " 12:00 PM";
            checkOutDate = dateValid(checkOutString);
        }
        return checkOutDate;
    }

    /**
     * To remove time from Date
     * 
     * @param date
     * @return Date
     */
    private Date removeTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

}
