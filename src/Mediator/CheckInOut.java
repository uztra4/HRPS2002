package Mediator;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import Controller.OrderController;
import Controller.ReservationController;
import Controller.RoomController;
import Entity.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import Enums.RoomTypes;
import Enums.ReservationStatus;
import Enums.RoomStatus;

import java.util.List;
import java.util.Map;

/***
 * Represents a CheckInOut mediator
 * 
 * @version 1.0
 * @since 2022-04-17
 */

public class CheckInOut {
	/**
     * The Instance of this Mediator
     */
	private static CheckInOut instance = null;

	/**
     * Constructor
     */
	private CheckInOut() {
	}

	/**
     * Returns the CheckInOut instance and creates an instance if it does not exist
     * 
     * @return CheckInOut instance
     */
	public static CheckInOut getInstance() {
		if (instance == null)
			instance = new CheckInOut();
		return instance;
	}

	/**
     * Run checkout process communicating across RoomController and ReservationController
     * 
     * Room > Update status to vacant and clear registered guestID
     * Reservation > Update status to completed and set checkout date to current time
     * 
     * Send reservation object and discount rates to printReceipt behaviour for receipt printing
     * 
     * @param roomID	ID of room to be checked-out
     * @param roomDiscount	User input for discounts on room
     * @param orderDiscount	User input for discounts on orders
     */
	public void checkOut(String roomID, float roomDiscount, float orderDiscount) {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm a");
		
		// Check validity of check-out
		Reservation reservation = ReservationController.getInstance().getCheckInReservation(roomID);
		if (validCheckOut(reservation) == false)
			return;

		// Change reservation status to completed
		ReservationController.getInstance().update(reservation, 7, "4");
		
		// set check out date to NOW
		ReservationController.getInstance().update(reservation, 4, sdf.format(now));

		// Change room status to vacant and clear guestID
		Room room = RoomController.getInstance().checkExistence(reservation.getRoomID());
		RoomController.getInstance().update(room, 2, null);
		RoomController.getInstance().update(room, 9, "1");

		// Check-out confirmation
		System.out.println("Check-Out Successful");
		System.out.println("Reservation ID: " + reservation.getID());
		System.out.println("Assigned Room: " + reservation.getRoomID());

		// Get payment details (Room payment, order payments)
		printReceipt(reservation, roomDiscount, orderDiscount);
	}

	/**
     * Run checkin process communicating across RoomController and ReservationController
     * 
     * Room > Update status to occupied and register guestID
     * Reservation > Update status to checkin, set checkin date to current time, and update roomID to assigned room
     * 
     * Reservation is assigned to first available room during checkin
     * 
     * @param reservationID	ID of reservation to be checked-in
     */
	public void checkIn(String reservationID) {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm a");
		Reservation reservation = ReservationController.getInstance().checkExistence(reservationID);
		if (validCheckIn(reservation) == false)
			return;

		// Assign room
		Room room = assignRoom(reservation);
		if (room == null)
			return;
		ReservationController.getInstance().update(reservation, 2, room.getRoomID());

		// Change reservation status to checked-in
		ReservationController.getInstance().update(reservation, 7, "2");

		// set check in date to NOW
		ReservationController.getInstance().update(reservation, 3, sdf.format(now));

		// Change room status to occupied and set guestID
		RoomController.getInstance().update(room, 2, reservation.getGuestID());
		RoomController.getInstance().update(room, 9, "2");

		// Check-In confirmation
		System.out.println("Check-In Successful");
		System.out.println("Reservation ID: " + reservation.getID());
		System.out.println("Assigned Room: " + room.getRoomID());
	}
	
	/**
     * Run change room process
     * - Check for validity of change room request
     * - Transfer all existing orders to new room
     * - Update reservation with roomID and new room type
     * - Clear old room guestID and set status to occupied
     * - Set new room guestID and set status to occupied
     * 
     * @param reservationID	ID of reservation to change room
     * @param roomID ID of room to be changed to
     */
	public void changeRoom(String reservationID, String roomID) {
		Reservation reservation = ReservationController.getInstance().checkExistence(reservationID);
		Room room = RoomController.getInstance().checkExistence(roomID);
		
		if (validRoomChange(reservation, room) == false) {
			System.out.println("Room Change failed");
			return;
		}
		
		Room oldRoom = RoomController.getInstance().checkExistence(reservation.getRoomID());
		
		String oldRoomID = reservation.getRoomID();
		
		//Transfer all orders
		List<Order> orderList = OrderController.getInstance().retrieveOrdersOfRoom(oldRoomID);
		if (orderList != null)
			for (Order order : orderList)
				OrderController.getInstance().update(order, 1, roomID);
		
		//Update reservation roomID and roomType
		ReservationController.getInstance().update(reservation, 2, roomID);
		String roomType = null;
		switch (room.getRoomType()) {
		case SINGLE:
			roomType = "1";
			break;
		case DOUBLE:
			roomType = "2";
			break;
		case DELUXE:
			roomType = "3";
			break;
		case SUITE:
			roomType = "4";
			break;
		case STANDARD:
			roomType = "5";
			break;
		}
		ReservationController.getInstance().update(reservation, 8, roomType);
		
		//Update room details
		RoomController.getInstance().update(oldRoom, 2, null);
		RoomController.getInstance().update(oldRoom, 9, "1");
		RoomController.getInstance().update(room, 2, reservation.getGuestID());
		RoomController.getInstance().update(room, 9, "2");
		
		System.out.println("Room Change successful");
	}

	/**
     * Check for number of available rooms of specified roomType at a specified date
     * 
     * Number of available rooms =
     * 		Total number of rooms
     * 		- Number of confirmed guest with booking where dateCheck falls between checkin and checkout date
     * 		- Number of checked in guest with bookings where dateCheck falls before checkout date
     * 
     * @param dateCheck	Date to check for availability
     * @param roomType	Room Type to check for availability
     * @return int Number of available rooms at given date
     */
	public int numAvailability(Date dateCheck, RoomTypes roomType) {
		//Retrieve list of rooms of specified roomType
		Map<RoomTypes, List<Room>> roomList = RoomController.getInstance().splitRoomByType();
		
		//Retrieve list of reservations split by status
		Map<ReservationStatus, List<Reservation>> reservationList = ReservationController.getInstance()
				.splitReservationByStatus();
		
		//Counter for number of reservations which fall under criteria
		int reservationCount = 0;
		dateCheck = removeTime(dateCheck);
		Date checkIn;
		Date checkOut;
		for (Reservation reservation : reservationList.get(ReservationStatus.CONFIRM)) {
			if (reservation.getRoomType() == roomType) {
				checkIn = removeTime(reservation.getCheckIn());
				checkOut = removeTime(reservation.getCheckOut());
				if (dateCheck.equals(checkIn))
					reservationCount++;
				else if (dateCheck.after(checkIn) && dateCheck.before(checkOut))
					reservationCount++;
			}
		}
		for (Reservation reservation : reservationList.get(ReservationStatus.CHECKIN)) {
			checkOut = removeTime(reservation.getCheckOut());
			if (reservation.getRoomType() == roomType) {
				if (dateCheck.before(checkOut))
					reservationCount++;
			}
		}

		// Returns difference of number of rooms and number of reservations under criteria
		return roomList.get(roomType).size() - reservationCount;
	}

	/**
     * Calculate and print receipt of a reservation
     * 
     * - Weekends bookings (Fri-Sat & Sat-Sun) cost 10% more
     * - GST rate = 7%
     * - Service Charge rate = 10%
     * - Room and order discounts are applied separately to allow for further expansion
     * 
     * @param reservation Reservation Object to calculate price
     * @param roomDiscount	Discounts on room cost
     * @param orderDiscount	Discounts on orders cost
     */
	public void printReceipt(Reservation reservation, float roomDiscount, float orderDiscount) {
		String roomID = reservation.getRoomID();

		// Retrieve orders from room
		ArrayList<Order> roomOrders = OrderController.getInstance().retrieveOrdersOfRoom(roomID);

		// Get price of room
		double roomPrice = RoomController.getInstance().checkExistence(roomID).getRoomPrice();

		// Calculate number of weekends and weekdays
		Date checkIn = removeTime(reservation.getCheckIn());
		Date checkOut = removeTime(reservation.getCheckOut());
		long days = TimeUnit.DAYS.convert(checkOut.getTime() - checkIn.getTime(), TimeUnit.MILLISECONDS);
		long weekends = countWeekends(reservation.getCheckIn(), reservation.getCheckOut());
		long weekdays = days - weekends;

		// Calculate room cost (Weekends cost 10% more)
		double roomCost = roomPrice * weekdays + roomPrice * weekends * 1.1;
		double discountRoomCost = roomCost * (1 - roomDiscount);

		// Calculate total order costs and view receipt
		double orderCost = 0;
		if (roomOrders != null)
			for (Order order : roomOrders)
				orderCost += order.viewOrder();
		double discountOrderCost = orderCost * (1 - orderDiscount);

		// Calculate subTotal and total cost
		double subTotal = discountRoomCost + discountOrderCost;
		double GST = 0.07;
		double serviceCharge = 0.10;
		double total = subTotal * (1 + GST + serviceCharge);

		// Print receipt
		Date thisDate = new Date();
		System.out.println();
		System.out.println("============================");
		System.out.println("    Outstanding Payments    ");
		System.out.println("============================");
		System.out.println(thisDate);
		System.out.println("Room");
		System.out.println("  - Weekdays: " + weekdays);
		System.out.println("  - Weekends: " + weekends);
		System.out.println("  - Discount: " + roomDiscount * 100 + "%");
		System.out.printf("  - Total cost: $%.2f\n", discountRoomCost);
		System.out.println("Room Service");
		System.out.println("  - Discount: " + orderDiscount * 100 + "%");
		System.out.printf("  - Total cost: $%.2f\n", discountOrderCost);
		System.out.printf("SubTotal : $%.2f", subTotal);
		System.out.println();
		System.out.printf("GST      : $%.2f", subTotal * GST);
		System.out.println();
		System.out.printf("Service  : $%.2f", subTotal * serviceCharge);
		System.out.println();
		System.out.printf("Total    : $%.2f", total);
		System.out.println();
		System.out.println("============================");
		System.out.println();
	}

	/**
     * Change all order status of a given reservation to paid
     * 
     * @param reservationID which Orders will be changed to paid
     */
	public void payment(String reservationID) {
		Reservation reservation = ReservationController.getInstance().checkExistence(reservationID);
		if (reservation == null) {
			System.out.println("Invalid Reservation ID");
			return;
		}
		ArrayList<Order> roomOrders = OrderController.getInstance().retrieveOrdersOfRoom(reservation.getRoomID());
		// update orders to paid
		if (roomOrders != null)
			for (Order order : roomOrders)
				OrderController.getInstance().update(order, 3, "4");
	}

	/**
     * Set creditcard details used for payment of a reservation
     * 
     * @param reservationID
     * @param cardNumber
     * @param expiryDate
     * @param CVC
     * @param type
     * @param cardName
     */
	public void setReservationCreditcard(String reservationID, String cardNumber, Date expiryDate, int CVV, int type,
			String cardName) {
		Reservation reservation = ReservationController.getInstance().checkExistence(reservationID);
		if (reservation == null)
			return;
		ReservationController.getInstance().updateCreditcard(reservation, cardNumber, expiryDate, CVV, type, cardName);
	}

	/**
     * Sets time of input date to 00:00:00
     * 
     * @param date	Date to set time
     * @return Date	after setting time to 00:00:00
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
     * Count number of weekends between given dates
     * - For purpose of hotel weekends are Friday and Saturday
     * 
     * @param start	Starting date to check from
     * @param end	Ending date to check to
     * @return long	Number of weekends between given dates
     */
	private long countWeekends(Date start, Date end) {
		long days = TimeUnit.DAYS.convert(end.getTime() - start.getTime(), TimeUnit.MILLISECONDS);
		long weekends = 2 * (int) (days / 7);
		days = days - 7 * (int) (days / 7);
		Calendar c = Calendar.getInstance();
		c.setTime(start);
		while (days > 0) {
			int day = c.get(Calendar.DAY_OF_WEEK);
			if (day == Calendar.FRIDAY || day == Calendar.SATURDAY)
				weekends++;
			days--;
			c.add(Calendar.DATE, 1);
		}
		return weekends;
	}

	/**
     * Check if reservation is invalid for checkin
     * - reservation object is null
     * - reservation checkin date is not current date
     * - reservation status is not confirm status
     * 
     * @param reservation	Reservation object to check validity
     * @return boolean	true - valid, false - invalid
     */
	private boolean validCheckIn(Reservation reservation) {
		if (reservation == null) {
			System.out.println("Invalid Reservation ID");
			return false;
		}
		Date thisDate = new Date();
		thisDate = removeTime(thisDate);
		// System.out.println("today: " + thisDate + "checkin: " +
		// removeTime(reservation.getCheckIn()));
		if (!thisDate.equals(removeTime(reservation.getCheckIn()))) {
			System.out.println("Check In date does not match");
			return false;
		}
		if (reservation.getReservationStatus() != ReservationStatus.CONFIRM) {
			System.out.println("Reservation cannot be checked-in");
			return false;
		}
		return true;
	}

	/**
     * Check if reservation is invalid for checkout
     * - reservation object is null
     * - reservation status is not checkin status
     * 
     * @param reservation	Reservation object to check validity
     * @return boolean	true - valid, false - invalid
     */
	private boolean validCheckOut(Reservation reservation) {
		if (reservation == null) {
			System.out.println("No reservation found (Please enter room ID)");
			return false;
		}
		if (reservation.getReservationStatus() != ReservationStatus.CHECKIN) {
			System.out.println("Reservation cannot be checked-out");
			return false;
		}

		/*Date thisDate = new Date();
		thisDate = removeTime(thisDate);
		Date checkOut = removeTime(reservation.getCheckOut());
		if (!thisDate.equals(checkOut)) {
			System.out.println("Check Out date does not match");
			return false;
		}*/
		
		return true;
	}
	
	/**
     * Check if change room request is invalid
     * - Reservation object is null
     * - Room object is null
     * - Change date is not checkin date (can only change on checkin date)
     * - New room is not vacant
     * 
     * @param reservation	Reservation object to check validity
     * @return boolean	true - valid, false - invalid
     */
	private boolean validRoomChange(Reservation reservation, Room room) {
		Date today = new Date();
		today = removeTime(today);
		
		
		if (reservation == null) {
			System.out.println("Invalid reservation ID");
			return false;
		}
		if (room == null) {
			System.out.println("Invalid room ID");
			return false;
		}
		Date checkIn = removeTime(reservation.getCheckIn());
		if (today.equals(checkIn)==false) {
			System.out.println("Can only change room on Check-In day");
			return false;
		}
		if (room.getRoomStatus()!=RoomStatus.VACANT) {
			System.out.println("Room is not vacant");
			return false;
		}
		if (reservation.getReservationStatus()!=ReservationStatus.CHECKIN) {
			System.out.println("Reservation not Checked-In");
			return false;
		}
		
		return true;
	}

	/**
     * Return first vacant room of reservation roomType
     * No updates to room and reservation details in this function
     * 
     * @param reservation to assign room to
     * @return Room	room assigned to reservation
     */
	private Room assignRoom(Reservation reservation) {
		RoomTypes roomType = reservation.getRoomType();
		List<Room> vacantRooms = RoomController.getInstance().generateOccupancyReport().get(roomType);
		if (vacantRooms.size() == 0) { // if not rooms available
			System.out.println("Room is not ready");
			return null;
		}
		return vacantRooms.get(0);
	}
}
