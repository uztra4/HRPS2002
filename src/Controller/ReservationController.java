package Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Database.SerializeDB;
import Entity.Entities;
import Entity.Reservation;
import Enums.ReservationStatus;
import Enums.RoomTypes;
import Mediator.CheckInOut;

/***
 * Represents a Reservation Controller
 * 
 * @version 1.0
 * @since 2022-04-17
 */

public class ReservationController extends SerializeDB implements IController, IStorage {
	/**
     * The Instance of this Controller
     */
	private static ReservationController instance = null;
    /**
     * The collection of reservations
     */
    public ArrayList<Reservation> reservationList;

    /**
     * Constructor
     */
    private ReservationController() {
        reservationList = new ArrayList<>();
    }

    /**
     * Returns the ReservationController instance and creates an instance if it does not exist
     * 
     * @return ReservationController instance
     */
    public static ReservationController getInstance() {
        if (instance == null) {
            instance = new ReservationController();
        }
        return instance;
    }

    /**
     * Return Reservation object if reservationID matches
     * Update reservation status as expired if status is confirmed or waitlist, and checkin date is after current date
     * Update reservation status to confirms if status is in waitlist and room is available
     * 
     * @param reservationID ID of desired reservation
     * @return toBeReturned Reservation Object with corresponding reservationID 
     */
    public Reservation checkExistence(String reservationID) {
        Date thisDate = new Date();
        thisDate = removeTime(thisDate);
        // SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
        // System.out.println("Time now is " + formatter.format(thisDate));

        Reservation toBeReturned = null;

        for (Reservation reservation : reservationList) {
            // Check expiration of reservation
            if ((reservation.getReservationStatus() == ReservationStatus.WAITLIST
                    || reservation.getReservationStatus() == ReservationStatus.CONFIRM)
                    && thisDate.compareTo(removeTime(reservation.getCheckIn())) > 0) {
                //System.out.println("Current date is past check in time");
                reservation.setReservationStatus(ReservationStatus.EXPIRED);
            }

            // Check waitlist for confirmation
            if (reservation.getReservationStatus() == ReservationStatus.WAITLIST) {
                if (CheckInOut.getInstance().numAvailability(reservation.getCheckIn(), reservation.getRoomType()) > 0)
                    reservation.setReservationStatus(ReservationStatus.CONFIRM);
            }

            if (reservation.getID().equals(reservationID)) {
                toBeReturned = reservation;
            }
        }

        return toBeReturned;
    }

    
    /**
     * Downcast to Reservation and add to list of Reservations
     * Set reservationID as checkInDate + guestID
     * 
     * @param entities Reservation object to be added to list
     */
    public void create(Object entities) {

        Reservation newReservation = (Reservation) entities;

        String checkInString = new SimpleDateFormat("ddMMyy").format(newReservation.getCheckIn());

        String reservationID = checkInString + newReservation.getGuestID();
        newReservation.setID(reservationID);
        // newReservation.setReservationStatus(ReservationStatus.CONFIRM);

        reservationList.add(newReservation);
        // System.out.println("Reservation ID generated: " + reservationID);

        storeData();
    }

    /**
     * Print all reservation ID
     */
    public void read() {
        for (Reservation reservation : reservationList) {
            System.out.println(reservation.getID());
        }
    }

    /**
     * Delete reservation from list of Reservations
     * Check for reservations with waitlist status and update to confirm status
     * 
     * @param entities	Reservation object to be deleted from list
     */
    public void delete(Object entities) {

        Reservation toBeDeleted = (Reservation) entities;
        reservationList.remove(toBeDeleted);
        // System.out.println("reservation removed from list in reservation
        // controller");
        for (Reservation reservation : reservationList) {
            // Check waitlist for confirmation
            if (reservation.getReservationStatus() == ReservationStatus.WAITLIST) {
                if (CheckInOut.getInstance().numAvailability(reservation.getCheckIn(), reservation.getRoomType()) > 0) {
                	reservation.setReservationStatus(ReservationStatus.CONFIRM);
                	break;
                }
            }
        }
        storeData();
    }

    /**
     * Update field of Reservation with input values
     * 
     * @param entities Reservation object to be updated
     * @param choice   choice from UI
     * @param value    input from user to be pass to setters
     */
    public void update(Object entities, int choice, String value) {
        Reservation toBeUpdated = (Reservation) entities;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm a");
        Date date;
        switch (choice) {
            case 1: // guestID
                toBeUpdated.setGuestID(value);
                break;
            case 2: // roomID
                toBeUpdated.setRoomID(value);
                break;
            case 3: // checkIn Date
                try {
                    date = new SimpleDateFormat("dd/MM/yy hh:mm a").parse(value);
                    System.out.println(sdf.format(date));
                    toBeUpdated.setCheckIn(date);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 4: // checkOut Date
                try {
                    date = new SimpleDateFormat("dd/MM/yy hh:mm a").parse(value);
                    System.out.println(sdf.format(date));

                    toBeUpdated.setCheckOut(date);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 5: // childNo
                try {
                    int numOfChild = Integer.parseInt(value);
                    toBeUpdated.setChildNo(numOfChild);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 6: // adultNo
                try {
                    int numOfAdults = Integer.parseInt(value);
                    toBeUpdated.setAdultNo(numOfAdults);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 7: // reservationStatus
                toBeUpdated.setReservationStatus(generateStatus(value));
                break;
            case 8: // roomType
                RoomTypes roomType = RoomTypes.SINGLE;
                switch (value.charAt(0)) {
                    case '1':
                        roomType = RoomTypes.SINGLE;
                        break;
                    case '2':
                        roomType = RoomTypes.DOUBLE;
                        break;
                    case '3':
                        roomType = RoomTypes.DELUXE;
                        break;
                    case '4':
                        roomType = RoomTypes.SUITE;
                        break;
                }
                toBeUpdated.setRoomType(roomType);
                break;
            default:
                break;
        }

        storeData();
    }

    /**
     * Return reservation status given string input
     * 
     * @param value	String input to be converted to ReservationStatus Enum
     * @return ReservationStatus Enum assigned to input
     */
    private ReservationStatus generateStatus(String value) {
        int choice = Integer.parseInt(value);
        switch (choice) {
            case 1:
                return ReservationStatus.CONFIRM;
            case 2:
                return ReservationStatus.CHECKIN;
            case 3:
                return ReservationStatus.EXPIRED;
            case 4:
                return ReservationStatus.COMPLETED;
            case 5:
                return ReservationStatus.WAITLIST;
            default:
                return null;
        }
    }

    /**
     * Splits reservations into separate lists according to status
     * 
     * @return Map list of reservation split by reservationStatus
     */
    public Map<ReservationStatus, List<Reservation>> splitReservationByStatus() {
        Map<ReservationStatus, List<Reservation>> reservationByStatus = new HashMap<>();

        ArrayList<Reservation> confirmStatus = new ArrayList<Reservation>();
        ArrayList<Reservation> checkinStatus = new ArrayList<Reservation>();
        ArrayList<Reservation> expiredStatus = new ArrayList<Reservation>();
        ArrayList<Reservation> completedStatus = new ArrayList<Reservation>();
        ArrayList<Reservation> waitlistStatus = new ArrayList<Reservation>();

        for (Reservation reservation : reservationList) {
            if (reservation.getReservationStatus() == ReservationStatus.CONFIRM) { // single
                confirmStatus.add(reservation);
            }
            if (reservation.getReservationStatus() == ReservationStatus.CHECKIN) { // single
                checkinStatus.add(reservation);
            }
            if (reservation.getReservationStatus() == ReservationStatus.EXPIRED) { // single
                expiredStatus.add(reservation);
            }
            if (reservation.getReservationStatus() == ReservationStatus.COMPLETED) { // single
                completedStatus.add(reservation);
            }
            if (reservation.getReservationStatus() == ReservationStatus.WAITLIST) { // single
                waitlistStatus.add(reservation);
            }
        }

        reservationByStatus.put(ReservationStatus.CONFIRM, confirmStatus);
        reservationByStatus.put(ReservationStatus.CHECKIN, checkinStatus);
        reservationByStatus.put(ReservationStatus.EXPIRED, expiredStatus);
        reservationByStatus.put(ReservationStatus.COMPLETED, completedStatus);
        reservationByStatus.put(ReservationStatus.WAITLIST, waitlistStatus);

        return reservationByStatus;
    }

    /**
     * Sets time of input date to 00:00:00
     * 
     * @param date	Date with time to is to be set
     * @return Date	Date with time set to 00:00:00
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

    /**
     * Return Reservation Object from list of reservations that are checked in
     * Search using registered roomID
     * 
     * @param roomID ID of room assigned to a checked-in reservation
     * @return reservation Reservation object with the assigned roomID
     */
    public Reservation getCheckInReservation(String roomID) {
        List<Reservation> checkedInList = splitReservationByStatus().get(ReservationStatus.CHECKIN);
        for (Reservation reservation : checkedInList) {
            if (roomID.compareTo(reservation.getRoomID()) == 0)
                return reservation;
        }
        return null;
    }

    /**
     * Update Reservation Creditcard details with setter
     * 
     * @param entities   Reservation to set creditcard details
     * @param cardNumber
     * @param expiryDate
     * @param CVC
     * @param type
     * @param cardName
     */
    public void updateCreditcard(Object entities, String cardNumber, Date expiryDate, int CVV, int type,
            String cardName) {
        Reservation toBeUpdated = (Reservation) entities;
        toBeUpdated.setCreditcard(cardNumber, expiryDate, CVV, type, cardName);
    }

    /**
     * Return list of reservations
     * 
     * return ArrayList of Reservation
     */
    public ArrayList<Reservation> getReservationList() {
        return this.reservationList;
    }

    /**
     * Store list of Reservations into serializable file
     */
    public void storeData() {
        super.storeData("Reservation.ser", reservationList);

    }

    /**
     * Loads list of Reservations from serializable file
     */
    public void loadData() {
        ArrayList<Entities> data = super.loadData("Reservation.ser");
        reservationList.clear();
        for (Entities reservation : data)
            reservationList.add((Reservation) reservation);
    }
}
