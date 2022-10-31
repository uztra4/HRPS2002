package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Database.SerializeDB;
import Entity.Entities;
import Entity.Room;
import Enums.BedTypes;
import Enums.RoomStatus;
import Enums.RoomTypes;
import Enums.RoomView;

/***
 * Represents a Room Controller
 * 
 * @version 1.0
 * @since 2022-04-17
 */

public class RoomController extends SerializeDB implements IController, IStorage {
	/**
     * The Instance of this Controller
     */
	private static RoomController instance = null;
	/**
     * The collection of rooms
     */
    private ArrayList<Room> roomList;

    /**
     * Constructor
     */
    private RoomController() {
        roomList = new ArrayList<>();
        //initHotel();
        //storeData();
    }
    
    /**
     * Returns the OrderController instance and creates an instance if it does not exist
     * 
     * @return OrderController instance
     */
    public static RoomController getInstance() {
        if (instance == null) {
            instance = new RoomController();
        }
        return instance;
    }

    /**
     * Initializes roomController with pre-determined rooms
     * 
     * DO NOT REMOVE, keep in case of data loss
     */
    private void initHotel() {
        Room r1 = new Room("02-01", 100.21, RoomTypes.SINGLE, BedTypes.SINGLE, false, RoomView.NIL, false);
        Room r2 = new Room("02-02", 125.21, RoomTypes.SINGLE, BedTypes.SINGLE, false, RoomView.NIL, true);
        Room r3 = new Room("02-03", 125.21, RoomTypes.SINGLE, BedTypes.SINGLE, true, RoomView.NIL, false);
        Room r4 = new Room("02-04", 150.21, RoomTypes.SINGLE, BedTypes.SINGLE, true, RoomView.NIL, true);
        Room r5 = new Room("02-05", 200.21, RoomTypes.DOUBLE, BedTypes.DOUBLE, false, RoomView.NIL, false);
        Room r6 = new Room("02-06", 225.21, RoomTypes.DOUBLE, BedTypes.DOUBLE, true, RoomView.NIL, false);
        Room r7 = new Room("02-07", 225.21, RoomTypes.DOUBLE, BedTypes.QUEEN, false, RoomView.NIL, false);
        Room r8 = new Room("02-08", 250.21, RoomTypes.DOUBLE, BedTypes.QUEEN, true, RoomView.NIL, false);
        Room r9 = new Room("03-01", 300.21, RoomTypes.DOUBLE, BedTypes.KING, true, RoomView.NIL, false);
        Room r10 = new Room("03-02", 350.21, RoomTypes.DOUBLE, BedTypes.KING, true, RoomView.NIL, true);
        Room r11 = new Room("03-03", 400.21, RoomTypes.DELUXE, BedTypes.DOUBLE, true, RoomView.NIL, false);
        Room r12 = new Room("03-04", 400.21, RoomTypes.DELUXE, BedTypes.DOUBLE, false, RoomView.NIL, true);
        Room r13 = new Room("03-05", 450.21, RoomTypes.DELUXE, BedTypes.QUEEN, true, RoomView.NIL, false);
        Room r14 = new Room("03-06", 450.21, RoomTypes.DELUXE, BedTypes.QUEEN, false, RoomView.NIL, true);
        Room r15 = new Room("03-07", 500.21, RoomTypes.DELUXE, BedTypes.KING, true, RoomView.NIL, true);
        Room r16 = new Room("03-08", 500.21, RoomTypes.DELUXE, BedTypes.KING, true, RoomView.NIL, true);
        Room r17 = new Room("04-01", 150.21, RoomTypes.SINGLE, BedTypes.SINGLE, false, RoomView.POOL, false);
        Room r18 = new Room("04-02", 175.21, RoomTypes.SINGLE, BedTypes.SINGLE, false, RoomView.POOL, true);
        Room r19 = new Room("04-03", 175.21, RoomTypes.SINGLE, BedTypes.SINGLE, true, RoomView.POOL, false);
        Room r20 = new Room("04-04", 200.21, RoomTypes.SINGLE, BedTypes.SINGLE, true, RoomView.POOL, true);
        Room r21 = new Room("04-05", 350.21, RoomTypes.DOUBLE, BedTypes.DOUBLE, true, RoomView.POOL, true);
        Room r22 = new Room("04-06", 350.21, RoomTypes.DOUBLE, BedTypes.QUEEN, true, RoomView.POOL, true);
        Room r23 = new Room("04-07", 400.21, RoomTypes.DOUBLE, BedTypes.KING, true, RoomView.POOL, true);
        Room r24 = new Room("04-08", 450.21, RoomTypes.DELUXE, BedTypes.DOUBLE, true, RoomView.POOL, true);
        Room r25 = new Room("05-01", 500.21, RoomTypes.DELUXE, BedTypes.QUEEN, true, RoomView.POOL, true);
        Room r26 = new Room("05-02", 500.21, RoomTypes.DELUXE, BedTypes.KING, true, RoomView.POOL, false);
        Room r27 = new Room("05-03", 550.21, RoomTypes.DELUXE, BedTypes.KING, true, RoomView.POOL, true);
        Room r28 = new Room("05-04", 550.21, RoomTypes.SUITE, BedTypes.DOUBLE, true, RoomView.POOL, false);
        Room r29 = new Room("05-05", 600.21, RoomTypes.SUITE, BedTypes.DOUBLE, true, RoomView.POOL, true);
        Room r30 = new Room("05-06", 600.21, RoomTypes.SUITE, BedTypes.QUEEN, true, RoomView.POOL, true);
        Room r31 = new Room("05-07", 550.21, RoomTypes.SUITE, BedTypes.KING, true, RoomView.POOL, false);
        Room r32 = new Room("05-08", 600.21, RoomTypes.SUITE, BedTypes.KING, true, RoomView.POOL, true);
        Room r33 = new Room("06-01", 150.21, RoomTypes.SINGLE, BedTypes.SINGLE, false, RoomView.CITY, false);
        Room r34 = new Room("06-02", 175.21, RoomTypes.SINGLE, BedTypes.SINGLE, false, RoomView.CITY, true);
        Room r35 = new Room("06-03", 175.21, RoomTypes.SINGLE, BedTypes.SINGLE, true, RoomView.CITY, false);
        Room r36 = new Room("06-04", 200.21, RoomTypes.SINGLE, BedTypes.SINGLE, true, RoomView.CITY, true);
        Room r37 = new Room("06-05", 325.21, RoomTypes.DOUBLE, BedTypes.DOUBLE, true, RoomView.CITY, false);
        Room r38 = new Room("06-06", 350.21, RoomTypes.DOUBLE, BedTypes.DOUBLE, true, RoomView.CITY, true);
        Room r39 = new Room("06-07", 350.21, RoomTypes.DOUBLE, BedTypes.QUEEN, true, RoomView.CITY, true);
        Room r40 = new Room("06-08", 400.21, RoomTypes.DOUBLE, BedTypes.KING, true, RoomView.CITY, true);
        Room r41 = new Room("07-01", 450.21, RoomTypes.DELUXE, BedTypes.DOUBLE, true, RoomView.CITY, true);
        Room r42 = new Room("07-02", 500.21, RoomTypes.DELUXE, BedTypes.QUEEN, true, RoomView.CITY, true);
        Room r43 = new Room("07-03", 500.21, RoomTypes.DELUXE, BedTypes.KING, false, RoomView.CITY, true);
        Room r44 = new Room("07-04", 550.21, RoomTypes.DELUXE, BedTypes.KING, true, RoomView.CITY, true);
        Room r45 = new Room("07-05", 600.21, RoomTypes.SUITE, BedTypes.DOUBLE, true, RoomView.CITY, true);
        Room r46 = new Room("07-06", 550.21, RoomTypes.SUITE, BedTypes.QUEEN, true, RoomView.CITY, false);
        Room r47 = new Room("07-07", 600.21, RoomTypes.SUITE, BedTypes.QUEEN, true, RoomView.CITY, true);
        Room r48 = new Room("07-08", 600.21, RoomTypes.SUITE, BedTypes.KING, true, RoomView.CITY, true);
        Room r49 = new Room("08-01", 150.21, RoomTypes.STANDARD, BedTypes.SINGLE, true, RoomView.NIL, true);
        Room r50 = new Room("08-02", 190.21, RoomTypes.STANDARD, BedTypes.DOUBLE, true, RoomView.NIL, false);
        Room r51 = new Room("08-03", 200.21, RoomTypes.STANDARD, BedTypes.DOUBLE, false, RoomView.NIL, true);
        Room r52 = new Room("08-04", 550.21, RoomTypes.STANDARD, BedTypes.KING, true, RoomView.NIL, false);
        Room r53 = new Room("08-05", 180.21, RoomTypes.STANDARD, BedTypes.DOUBLE, true, RoomView.NIL, true);
        Room r54 = new Room("08-06", 160.21, RoomTypes.STANDARD, BedTypes.QUEEN, true, RoomView.NIL, false);
        Room r55 = new Room("08-07", 160.21, RoomTypes.STANDARD, BedTypes.QUEEN, true, RoomView.NIL, false);
        Room r56 = new Room("08-08", 1800.21, RoomTypes.STANDARD, BedTypes.KING, true, RoomView.NIL, false);

        roomList.add(r1);
        roomList.add(r2);
        roomList.add(r3);
        roomList.add(r4);
        roomList.add(r5);
        roomList.add(r6);
        roomList.add(r7);
        roomList.add(r8);
        roomList.add(r9);
        roomList.add(r10);
        roomList.add(r11);
        roomList.add(r12);
        roomList.add(r13);
        roomList.add(r14);
        roomList.add(r15);
        roomList.add(r16);
        roomList.add(r17);
        roomList.add(r18);
        roomList.add(r19);
        roomList.add(r20);
        roomList.add(r21);
        roomList.add(r22);
        roomList.add(r23);
        roomList.add(r24);
        roomList.add(r25);
        roomList.add(r26);
        roomList.add(r27);
        roomList.add(r28);
        roomList.add(r29);
        roomList.add(r30);
        roomList.add(r31);
        roomList.add(r32);
        roomList.add(r33);
        roomList.add(r34);
        roomList.add(r35);
        roomList.add(r36);
        roomList.add(r37);
        roomList.add(r38);
        roomList.add(r39);
        roomList.add(r40);
        roomList.add(r41);
        roomList.add(r42);
        roomList.add(r43);
        roomList.add(r44);
        roomList.add(r45);
        roomList.add(r46);
        roomList.add(r47);
        roomList.add(r48);
        roomList.add(r49);
        roomList.add(r50);
        roomList.add(r51);
        roomList.add(r52);
        roomList.add(r53);
        roomList.add(r54);
        roomList.add(r55);
        roomList.add(r56);
    }

    /**
     * Return Room object if roomID matches
     * 
     * @param roomID ID of desired room
     * @return room	Room Object with corresponding roomID
     */
    public Room checkExistence(String roomID) {
        for (Room room : roomList) {
            if (room.getRoomID().equals(roomID)) {
                return room;
            }
        }
        return null;
    }

    /**
     * Downcast to Room and add to list of Rooms
     * 
     * @param entities	Room object to be added to list
     */
    public void create(Object entities) {
        Room newRoom = (Room) entities;
        roomList.add(newRoom);

        storeData();
    }

    /**
     * Print all roomIDs
     */
    public void read() {
        for (Room room : roomList) {
            System.out.println(room.getRoomID());
        }
    }

    /**
     * Delete room from list of Rooms
     * 
     * @param entities Room object to be deleted from list
     */
    public void delete(Object entities) {
        Room toBeDeleted = (Room) entities;
        roomList.remove(toBeDeleted);
        storeData();
    }

    /**
     * Update field of Room with input values
     * 
     * @param entities Room object to be update
     * @param choice   choice from UI
     * @param value    input from user to be pass to setters
     */
    public void update(Object entities, int choice, String value) {
        Room toBeUpdated = (Room) entities;

        switch (choice) {
            case 1: // roomID
                if (toBeUpdated.getRoomStatus() == RoomStatus.OCCUPIED) {
                    System.out.println("Room is currently occupied, ID cannot be changed.");
                    break;
                }
                toBeUpdated.setRoomID(value);
                break;
            case 2: // guestID
                toBeUpdated.setGuestID(value);
                break;
            case 3: // roomPrice
                try {
                    double price = Double.parseDouble(value);
                    toBeUpdated.setRoomPrice(price);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 4: // roomType
                toBeUpdated.setRoomType(generateRoomType(value));
                break;
            case 5: // bedType
                toBeUpdated.setBedType(generateBedType(value));
            case 6: // Wifi
                if (value.equals("Y")) {
                    toBeUpdated.setWiFi(true);
                } else {
                    toBeUpdated.setWiFi(false);
                }
                break;
            case 7: // view
                toBeUpdated.setView(generateView(value));
                break;
            case 8: // smoke
                if (value.equals("Y")) {
                    toBeUpdated.setSmoke(true);
                } else {
                    toBeUpdated.setSmoke(false);
                }
                break;
            case 9: // roomStatus
                toBeUpdated.setRoomStatus(generateStatus(value));
                break;
        }
        storeData();
    }
    /**
     * Return roomList
     */
    public ArrayList<Room> getRoomList()
    {
        return this.roomList;
    }

    /**
     * Split VACANT rooms into separate lists according to room type
     * 
     * @return Map list of VACANT rooms split by type
     */
    public Map<RoomTypes, List<Room>> generateOccupancyReport() {
        Map<RoomTypes, List<Room>> report = new HashMap<>();
        ArrayList<Room> vacantRooms = new ArrayList<>();

        ArrayList<Room> singleType = new ArrayList<Room>();
        ArrayList<Room> doubleType = new ArrayList<Room>();
        ArrayList<Room> standardType = new ArrayList<Room>();
        ArrayList<Room> deluxeType = new ArrayList<Room>();
        ArrayList<Room> suiteType = new ArrayList<Room>();

        HashMap<RoomStatus, List<Room>> roomByStatus = (HashMap<RoomStatus, List<Room>>) splitRoomByStatus();
        vacantRooms = (ArrayList<Room>) roomByStatus.get(RoomStatus.VACANT);

        for (Room room : vacantRooms) {
            if (room.getRoomType() == RoomTypes.SINGLE) { // single
                singleType.add(room);
            }
            if (room.getRoomType() == RoomTypes.DOUBLE) { // double
                doubleType.add(room);
            }
            if (room.getRoomType() == RoomTypes.STANDARD) { // standard
                standardType.add(room);
            }
            if (room.getRoomType() == RoomTypes.DELUXE) { // deluxe
                deluxeType.add(room);
            }
            if (room.getRoomType() == RoomTypes.SUITE) { // suite
                suiteType.add(room);
            }
        }

        report.put(RoomTypes.SINGLE, singleType);
        report.put(RoomTypes.DOUBLE, doubleType);
        report.put(RoomTypes.STANDARD, standardType);
        report.put(RoomTypes.DELUXE, deluxeType);
        report.put(RoomTypes.SUITE, suiteType);

        return report;
    }

    /**
     * Split rooms into separate lists according to room type
     * 
     * @return Map list of rooms split by type
     */
    public Map<RoomTypes, List<Room>> splitRoomByType() {
        Map<RoomTypes, List<Room>> roomByType = new HashMap<>();

        ArrayList<Room> singleType = new ArrayList<Room>();
        ArrayList<Room> doubleType = new ArrayList<Room>();
        ArrayList<Room> standardType = new ArrayList<Room>();
        ArrayList<Room> deluxeType = new ArrayList<Room>();
        ArrayList<Room> suiteType = new ArrayList<Room>();

        for (Room room : roomList) {
            if (room.getRoomType() == RoomTypes.SINGLE) { // single
                singleType.add(room);
            }
            if (room.getRoomType() == RoomTypes.DOUBLE) { // double
                doubleType.add(room);
            }
            if (room.getRoomType() == RoomTypes.STANDARD) { // deluxe
                standardType.add(room);
            }
            if (room.getRoomType() == RoomTypes.DELUXE) { // deluxe
                deluxeType.add(room);
            }
            if (room.getRoomType() == RoomTypes.SUITE) { // suite
                suiteType.add(room);
            }
        }

        roomByType.put(RoomTypes.SINGLE, singleType);
        roomByType.put(RoomTypes.DOUBLE, doubleType);
        roomByType.put(RoomTypes.STANDARD, standardType);
        roomByType.put(RoomTypes.DELUXE, deluxeType);
        roomByType.put(RoomTypes.SUITE, suiteType);

        return roomByType;
    }

    /**
     * Split rooms into separate lists according to status
     * 
     * @return Map list of rooms split by type
     */
    public Map<RoomStatus, List<Room>> splitRoomByStatus() {
        Map<RoomStatus, List<Room>> roomByStatus = new HashMap<>();

        ArrayList<Room> vacantRooms = new ArrayList<Room>();
        ArrayList<Room> occupiedRooms = new ArrayList<Room>();
        ArrayList<Room> reservedRooms = new ArrayList<Room>();
        ArrayList<Room> maintainRooms = new ArrayList<Room>();

        for (Room room : roomList) {
            if (room.getRoomStatus() == RoomStatus.VACANT) { // VACANT
                vacantRooms.add(room);
            }
            if (room.getRoomStatus() == RoomStatus.OCCUPIED) { // OCCUPIED
                occupiedRooms.add(room);
            }
            if (room.getRoomStatus() == RoomStatus.RESERVED) { // RESERVED
                reservedRooms.add(room);
            }
            if (room.getRoomStatus() == RoomStatus.MAINTAINENCE) { // MAINTAINENCE
                maintainRooms.add(room);
            }
        }

        roomByStatus.put(RoomStatus.VACANT, vacantRooms);
        roomByStatus.put(RoomStatus.OCCUPIED, occupiedRooms);
        roomByStatus.put(RoomStatus.RESERVED, reservedRooms);
        roomByStatus.put(RoomStatus.MAINTAINENCE, maintainRooms);

        return roomByStatus;

    }

    /**
     * Return room status given string input
     * 
     * @param value	String input to be converted to RoomStatus Enum
     * @return RoomStatus Enum assigned to input
     */
    public RoomStatus generateStatus(String value) {
        int choice = Integer.parseInt(value);
        switch (choice) {
            case 1:
                return RoomStatus.VACANT;
            case 2:
                return RoomStatus.OCCUPIED;
            case 3:
                return RoomStatus.MAINTAINENCE;
            case 4:
                return RoomStatus.RESERVED;
            default:
                return RoomStatus.MAINTAINENCE;

        }
    }

    /**
     * Return room view given string input
     * 
     * @param value String input to be converted to RoomView Enum
     * @return RoomView Enum assigned to input
     */
    public RoomView generateView(String value) {
        int choice = Integer.parseInt(value);
        switch (choice) {
            case 1:
                return RoomView.CITY;
            case 2:
                return RoomView.POOL;
            case 3:
                return RoomView.NIL;
            default:
                return RoomView.NIL;
        }
    }

    /**
     * Return room bed type given string input
     * 
     * @param value String input to be converted to BedTypes Enum
     * @return BedTypes Enum assigned to input
     */
    public BedTypes generateBedType(String value) {
        int choice = Integer.parseInt(value);
        switch (choice) {
            case 1:
                return BedTypes.SINGLE;
            case 2:
                return BedTypes.DOUBLE;
            case 3:
                return BedTypes.QUEEN;
            case 4:
                return BedTypes.KING;
            default:
                return BedTypes.SINGLE;
        }
    }

    /**
     * Return room type given string input
     * 
     * @param value String input to be converted to RoomTypes Enum
     * @return RoomTypes Enum assigned to input
     */
    public RoomTypes generateRoomType(String value) {
        int choice = Integer.parseInt(value);
        switch (choice) {
            case 1:
                return RoomTypes.SINGLE;
            case 2:
                return RoomTypes.DOUBLE;
            case 3:
                return RoomTypes.DELUXE;
            case 4:
                return RoomTypes.SUITE;
            default:
                return RoomTypes.SINGLE;
        }
    }

    /**
     * Store list of Rooms into serializable file
     */
    public void storeData() {
        super.storeData("Room.ser", roomList);
    }

    /**
     * Loads list of Items from serializable file
     */
    public void loadData() {
        ArrayList<Entities> data = super.loadData("Room.ser");
        roomList.clear();
        for (Entities room : data)
            roomList.add((Room) room);
    }
}
