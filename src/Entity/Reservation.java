package Entity;

import java.util.Date;
import java.text.SimpleDateFormat;

import Enums.ReservationStatus;
import Enums.RoomTypes;

/***
 * Represents a Reservation
 * Walk in customer will form a reservation on the spot
 * 
 * @version 1.0
 * @since 2022-04-17
 */
public class Reservation extends Entities {
    private String reservationID;
    private String guestID;
    private String roomID;
    private Date checkIn;
    private Date checkOut;
    private int childNo;
    private int adultNo;
    private ReservationStatus reservationStatus;
    private RoomTypes roomType;
    private Creditcard card;

    /**
     * Constructor with attributes as parameters
     * 
     * @param guestID
     * @param checkIn
     * @param checkOut
     * @param childNo
     * @param adultNo
     */
    public Reservation(String guestID, Date checkIn, Date checkOut, int childNo, int adultNo) {
        this.guestID = guestID;
        this.roomID = null;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.childNo = childNo;
        this.adultNo = adultNo;
        this.card = null;
    }

    /**
     * @return String Return reservation ID
     */
    public String getID() {
        return reservationID;
    }

    /**
     * @param reservationID Set Reservation ID
     */
    public void setID(String reservationID) {
        this.reservationID = reservationID;
    }

    /**
     * @return String Return guest ID
     */
    public String getGuestID() {
        return guestID;
    }

    /**
     * @param guestID Set guest ID
     */
    public void setGuestID(String guestID) {
        this.guestID = guestID;
    }

    /**
     * @return String Return Room ID
     */
    public String getRoomID() {
        return roomID;
    }

    /**
     * Room ID is only assign upon check in
     * 
     * @param roomID Set room ID
     */
    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    /**
     * @return Date Return date of check in
     */
    public Date getCheckIn() {
        return checkIn;
    }

    /**
     * @param checkIn Set date of check
     */
    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    /**
     * @return Date Return check out date
     */
    public Date getCheckOut() {
        return checkOut;
    }

    /**
     * @param checkOut Set check out date
     */
    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    /**
     * @return int Return number of children
     */
    public int getChildNo() {
        return childNo;
    }

    /**
     * @param childNo Set number of children
     */
    public void setChildNo(int childNo) {
        this.childNo = childNo;
    }

    /**
     * @return int Return number of adults
     */
    public int getAdultNo() {
        return adultNo;
    }

    /**
     * @param adultNo Set number of adults
     */
    public void setAdultNo(int adultNo) {
        this.adultNo = adultNo;
    }

    /**
     * @return ReservationStatus Return reservation status
     */
    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    /**
     * @param reservationStatus Set reservation status
     */
    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    /**
     * @return RoomTypes Return room type
     */
    public RoomTypes getRoomType() {
        return roomType;
    }

    /**
     * @param roomType Set room type
     */
    public void setRoomType(RoomTypes roomType) {
        this.roomType = roomType;
    }

    /**
     * @return Creditcard Return credit card object
     */
    public Creditcard getCreditcard() {
        return card;
    }

    /**
     * @param card Set credit card object
     */
    public void setCreditcard(Creditcard card) {
        this.card = card;
    }

    /**
     * Set credit card object with its parameters
     * 
     * @param cardNumber
     * @param expDate
     * @param CVV
     * @param type
     * @param cardName
     */
    public void setCreditcard(String cardNumber, Date expDate, int CVV, int type, String cardName) {
        this.card = new Creditcard(cardNumber, expDate, CVV, type, cardName);
    }

    /**
     * @return String Return formatted string of reservation details
     */
    @Override

    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm:ss a");
        return String.format("%14s\t%14s\t%10s\t%10s\t%10s\t%10s\t%10s", this.reservationID, this.guestID, this.roomID,
                this.roomType, this.reservationStatus, sdf.format(this.checkIn), sdf.format(this.checkOut));

    }

}
