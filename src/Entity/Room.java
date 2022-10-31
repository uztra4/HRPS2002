package Entity;

import Enums.BedTypes;
import Enums.RoomStatus;
import Enums.RoomTypes;
import Enums.RoomView;

/***
 * Represents a Room
 * 
 * @version 1.0
 * @since 2022-04-17
 */
public class Room extends Entities {
	/**
	 * 4 digit room number. first 2 digits represent level,
	 * last 2 digits are door number
	 */
	private String roomID;

	private String guestID;
	private double roomPrice;
	private RoomTypes roomType;
	private BedTypes bedType;
	private boolean WiFi;
	private RoomView view;
	private boolean smoke;
	private RoomStatus roomStatus;

	/**
	 * Constructor with attribute as parameters
	 * 
	 * @param roomID
	 * @param roomPrice
	 * @param roomType
	 * @param bedType
	 * @param WiFi
	 * @param view
	 * @param smoke
	 */
	public Room(String roomID, double roomPrice, RoomTypes roomType, BedTypes bedType, boolean WiFi,
			RoomView view,
			boolean smoke) {
		this.roomID = roomID;
		this.guestID = null;
		this.roomPrice = roomPrice;
		this.roomType = roomType;
		this.bedType = bedType;
		this.WiFi = WiFi;
		this.view = view;
		this.smoke = smoke;
		this.roomStatus = RoomStatus.VACANT;
	}

	/**
	 * @param roomID set room ID
	 */
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	/**
	 * @return String Return room ID
	 */
	public String getRoomID() {
		return roomID;
	}

	/**
	 * @param guestID Set guest ID
	 */
	public void setGuestID(String guestID) {
		this.guestID = guestID;
	}

	/**
	 * @return String Return guest ID
	 */
	public String getGuestID() {
		return guestID;
	}

	/**
	 * @param roomPrice Set price of room
	 */
	public void setRoomPrice(double roomPrice) {
		this.roomPrice = roomPrice;
	}

	/**
	 * @return double Return price of room
	 */
	public double getRoomPrice() {
		return roomPrice;
	}

	/**
	 * @param roomType Set room type
	 */
	public void setRoomType(RoomTypes roomType) {
		this.roomType = roomType;
	}

	/**
	 * @return RoomTypes Return room type
	 */
	public RoomTypes getRoomType() {
		return roomType;
	}

	/**
	 * @param bedType Set bed type
	 */
	public void setBedType(BedTypes bedType) {
		this.bedType = bedType;
	}

	/**
	 * @return BedTypes Return bed type
	 */
	public BedTypes getBedType() {
		return bedType;
	}

	/**
	 * @param WiFi Set existence of WiFi
	 */
	public void setWiFi(boolean WiFi) {
		this.WiFi = WiFi;
	}

	/**
	 * @return boolean Return existence of WiFi
	 */
	public boolean getWiFi() {
		return WiFi;
	}

	/**
	 * @param view Set view of room
	 */
	public void setView(RoomView view) {
		this.view = view;
	}

	/**
	 * @return RoomView Return view of room
	 */
	public RoomView getView() {
		return view;
	}

	/**
	 * @param smoke Set whether smoking is allowed
	 */
	public void setSmoke(boolean smoke) {
		this.smoke = smoke;
	}

	/**
	 * @return boolean Return whether smoking is allowed
	 */
	public boolean getSmoke() {
		return smoke;
	}

	/**
	 * @param roomStatus Set room status
	 */
	public void setRoomStatus(RoomStatus roomStatus) {
		this.roomStatus = roomStatus;
	}

	/**
	 * @return RoomStatus Return room status
	 */
	public RoomStatus getRoomStatus() {
		return roomStatus;
	}

	/**
	 * @return String Return formatted String of room details
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		result.append("Room ID: " + this.roomID + newLine);
		result.append("Guest ID: " + this.guestID + newLine);
		result.append("Room Price: $" + this.roomPrice + newLine);
		result.append("Room Type: " + this.roomType + newLine);
		result.append("Bed Type: " + this.bedType + newLine);
		String YN = (this.WiFi) ? "Y" : "N";
		result.append("WiFi: " + YN + newLine);
		result.append("View: " + this.view + newLine);
		YN = (this.smoke) ? "Y" : "N";
		result.append("Smoke: " + this.smoke + newLine);
		result.append("Room Status: " + this.roomStatus);

		return result.toString();
	}

	/**
	 * @return String another formatted string of room details
	 */
	public String tohoriString()
	{
		String wifi = (this.WiFi) ? "Y" : "N";
		String smoke = (this.smoke) ? "Y" : "N";
		return String.format("%8s\t%8s\t%10s\t%10s\t%10s\t%10s\t%10s\t%8s\t%10s", this.roomID, this.guestID, this.roomPrice, this.roomType, this.bedType, wifi, this.view, smoke, this.roomStatus);
	}

}
