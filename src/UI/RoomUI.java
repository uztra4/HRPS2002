package UI;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map;

import Controller.RoomController;
import Entity.Room;
import Enums.BedTypes;
import Enums.RoomStatus;
import Enums.RoomTypes;
import Enums.RoomView;

/***
 * Represents a RoomUI
 * 
 * @version 1.0
 * @since 2022-04-17
 */
public class RoomUI extends StandardUI implements ControllerUI {

    /**
     * The Instance of this Controller
     */
    private static RoomUI instance = null;
    Scanner sc;
    int choice, qSize;

    /**
     * Constructor
     */
    private RoomUI() {
        sc = new Scanner(System.in);
    }

    /**
     * Returns the RoomUIs instance and creates an instance if it does not
     * 
     * @return RoomUI
     */
    public static RoomUI getInstance() {
        if (instance == null) {
            instance = new RoomUI();
        }
        return instance;
    }

    /**
     * Print selection menu and return number of selections available
     * 
     * @return int
     */
    public int showSelection() {
        System.out.println(" Room options avaiable: ");
        System.out.println("1) Add Room");
        System.out.println("2) View Room");
        System.out.println("3) Update Room Detail");
        System.out.println("4) Remove Room");
        System.out.println("5) Show all room details");
        System.out.println("6) Occupancy Report");
        System.out.println("7) Show room by status");
        System.out.println("8) Return to MainUI");

        return 8;
    }

    /**
     * Select next prompt based on user iuput
     */
    public void mainMenu() {
        do {
            qSize = showSelection();
            choice = getUserChoice(qSize);

            switch (choice) {
                case 1:
                    create();
                    break;
                case 2:
                    readOneDets();
                    break;
                case 3:
                    update();
                    break;
                case 4:
                    delete();
                    break;
                case 5:
                    viewInfo();
                    break;
                case 6:
                    occupancyReport();
                    break;
                case 7:
                    showRoomByStatus();
                    break;
                case 8:
                    break;
            }
        } while (choice < qSize);

    }

    /**
     * Only create order if room has people staying
     * Create Order object with inputs
     */
    public void create() {

        System.out.println("Enter Room ID: ");
        String roomID = getUserString();

        System.out.println("Enter Room Price: ");
        double roomPrice = sc.nextDouble();

        while (roomPrice < 0.0) {
            System.out.println("Enter Positive number: ");
            roomPrice = sc.nextDouble();
        }

        System.out.println("Choose Room Type: ");
        System.out.println("1) SINGLE");
        System.out.println("2) DOUBLE");
        System.out.println("3) DELUXE");
        System.out.println("4) SUITE");
        System.out.println("5) STANDARD");
        RoomTypes roomType = null;

        choice = getUserChoice(5);
        roomType = RoomController.getInstance().generateRoomType(String.valueOf(choice));

        System.out.println("Enter Bed Type: ");
        System.out.println("1) SINGLE");
        System.out.println("2) DOUBLE");
        System.out.println("3) QUEEN");
        System.out.println("4) KING");
        BedTypes bedType = null;

        choice = getUserChoice(4);
        bedType = RoomController.getInstance().generateBedType(String.valueOf(choice));

        System.out.println("Please select if this room is WiFi enabled: ");
        System.out.println("1) WiFi Enabled");
        System.out.println("2) Not WiFi Enabled");

        choice = getUserChoice(2);
        boolean WiFi = choice == 1 ? true : false;

        System.out.println("Please select the room's view: ");
        System.out.println("1) CITY");
        System.out.println("2) POOL");
        System.out.println("3) NO VIEW");
        RoomView view = null;

        choice = getUserChoice(3);
        view = RoomController.getInstance().generateView(String.valueOf(choice));

        System.out.println("Please select if smoking is allowed in this room: ");
        System.out.println("(1) Smoking Allowed");
        System.out.println("(2) Smoking Not Allowed");

        choice = getUserChoice(2);
        boolean smoke = choice == 1 ? true : false;

        Room rawRoom = new Room(roomID, roomPrice, roomType, bedType, WiFi, view, smoke);
        RoomController.getInstance().create(rawRoom);
    }

    /**
     * Check for Room existence and print details of specific Room
     */
    public void readOneDets() {

        System.out.println("Enter roomID: ");
        String roomID = getUserString();

        Room roomRead = RoomController.getInstance().checkExistence(roomID);
        if (roomRead != null) {
            System.out.println("\n==========================");
            System.out.println("        Room Details");
            System.out.println("==========================");
            System.out.println(roomRead);
            System.out.println("==========================");
        } else
            System.out.println("Room does not exist!");

    }

    /**
     * Check for Room existence and prompts for arguments for update
     */
    public void update() {
        System.out.println("Enter roomID: ");
        String roomID = getUserString();

        Room toBeUpdated = RoomController.getInstance().checkExistence(roomID);
        if (toBeUpdated == null) {
            System.out.println("Room does not exist");
        } else {
            System.out.println("What do you want to update: ");
            System.out.println("1) Room ID"); // string
            System.out.println("2) Guest ID"); // string
            System.out.println("3) Room Price"); // float
            System.out.println("4) Room Type"); // enum
            System.out.println("5) Bed Type"); // enum
            System.out.println("6) WiFi Enabled (Y/N)"); // YN
            System.out.println("7) Room View"); // enum
            System.out.println("8) Smoking Room (Y/N)"); // enum
            System.out.println("9) Room Status"); // enum
            System.out.println("10) Cancel Update");

            choice = getUserChoice(10);
            if (choice == 10)
                return;
            String content = null;

            switch (choice) {
                case 4: // roomType
                    System.out.println("Choose room type: ");
                    System.out.println("1) SINGLE");
                    System.out.println("2) DOUBLE");
                    System.out.println("3) DELUXE");
                    System.out.println("4) SUITE");
                    System.out.println("5) STANDARD");
                    content = String.valueOf(getUserChoice(RoomTypes.values().length));
                    break;
                case 5: // bedType
                    System.out.println("Choose bed type: ");
                    System.out.println("1) SINGLE");
                    System.out.println("2) DOUBLE");
                    System.out.println("3) QUEEN");
                    System.out.println("4) KING");
                    content = String.valueOf(getUserChoice(BedTypes.values().length));
                    break;
                case 6:
                    System.out.println("Is the room WiFi Enabled? (Y/N)?");
                    content = getUserYN();
                    break;
                case 7: // roomView
                    System.out.println("Choose view: ");
                    System.out.println("1) CITY");
                    System.out.println("2) POOL");
                    System.out.println("3) NO VIEW");
                    content = String.valueOf(getUserChoice(RoomView.values().length));
                    break;
                case 8: // smoking
                    System.out.println("Is this a smoking room? (Y/N)?");
                    content = getUserYN();
                    break;
                case 9: // roomStatus
                    System.out.println("Choose status: ");
                    System.out.println("1) VACANT");
                    System.out.println("2) OCCUPIED");
                    System.out.println("3) MAINTAINENCE");
                    System.out.println("4) RESERVED");
                    content = String.valueOf(getUserChoice(RoomStatus.values().length));
                    break;
                default:
                    System.out.println("Enter the relevant details: ");
                    content = getUserString();
            }

            RoomController.getInstance().update(toBeUpdated, choice, content);
            System.out.println("\n==========================");
            System.out.println("       Room Details       ");
            System.out.println("==========================");
            System.out.println(toBeUpdated);
            System.out.println("==========================");
        }
    }
    
    /**
     * Check for Room existence and pass Room Object to controller for delete
     */
    public void delete() {
        System.out.println("Enter roomID: ");
        String roomID = getUserString();

        Room toBeDeleted = RoomController.getInstance().checkExistence(roomID);
        if (toBeDeleted == null) {
            System.out.println("Room does not exist");
        } else {
            RoomController.getInstance().delete(toBeDeleted);
        }
    }

    /**
     * View info of rooms by status and overall
     */
    public void viewInfo()
    {
        do
        {
            System.out.println("1) Print all VACANT rooms");
            System.out.println("2) Print all OCCUPIED rooms");
            System.out.println("3) Print all under MAINTAINENCE rooms");
            System.out.println("4) Print all rooms");
            System.out.println("5) Back to Room UI");

            choice = getUserChoice(5);
            switch(choice)
            {
                case 1:
                    viewVacant();
                    break;
                case 2:
                    viewOccupied();
                    break;
                case 3:
                    viewMaintainence();
                    break;
                case 4:
                    viewAll();
                    break;
                case 5:
                    return;
                default:
                    break;
            }
        } while (choice <6);
      
    }

    /**
     * View all occupied room details
     */
    public void viewOccupied()
    {
        System.out.println("\033[1mViewing all occupied rooms\033[0m");
        System.out.println("==============================================================================================================================================================");
        System.out.println(" Room ID          GuestID        Room Price($)     Room Type       Bed Type             Wifi?         View          Smoke         Room Status");
        System.out.println("==============================================================================================================================================================");
        for (Room room : RoomController.getInstance().getRoomList())
        {
            if (room.getRoomStatus() == RoomStatus.OCCUPIED)
                System.out.println(room.tohoriString());
        }
        System.out.println("==============================================================================================================================================================");
    }

    /**
     * View all maintainence room details
     */
    public void viewMaintainence()
    {
        System.out.println("\033[1mViewing all under-maintainence rooms\033[0m");
        System.out.println("==============================================================================================================================================================");
        System.out.println(" Room ID          GuestID        Room Price($)     Room Type       Bed Type             Wifi?         View          Smoke         Room Status");
        System.out.println("==============================================================================================================================================================");
        for (Room room : RoomController.getInstance().getRoomList())
        {
            if (room.getRoomStatus() == RoomStatus.MAINTAINENCE)
                System.out.println(room.tohoriString());
        }
        System.out.println("==============================================================================================================================================================");
    }

        /**
     * View all vacant room details
     */
    public void viewVacant()
    {
        System.out.println("\033[1mViewing all vacant rooms\033[0m");
        System.out.println("==============================================================================================================================================================");
        System.out.println(" Room ID          GuestID        Room Price($)     Room Type       Bed Type             Wifi?         View          Smoke         Room Status");
        System.out.println("==============================================================================================================================================================");
        for (Room room : RoomController.getInstance().getRoomList())
        {
            if (room.getRoomStatus() == RoomStatus.VACANT)
                System.out.println(room.tohoriString());
        }
        System.out.println("==============================================================================================================================================================");
    }


    /**
     * View all room details
     */
    public void viewAll()
    {
        System.out.println("\033[1mViewing all rooms\033[0m");
        System.out.println("==============================================================================================================================================================");
        System.out.println(" Room ID          GuestID        Room Price($)     Room Type       Bed Type             Wifi?         View          Smoke         Room Status");
        System.out.println("==============================================================================================================================================================");
        for (Room room : RoomController.getInstance().getRoomList())
        {
            System.out.println(room.tohoriString());
        }
        System.out.println("==============================================================================================================================================================");
    }


    /**
     * Print out occupancy report by room type for vacant room only
     */
    private void occupancyReport() {
        Map<RoomTypes, List<Room>> roomList = RoomController.getInstance().splitRoomByType();
        HashMap<RoomTypes, List<Room>> report;
        report = (HashMap<RoomTypes, List<Room>>) RoomController.getInstance().generateOccupancyReport();

        for (RoomTypes key : report.keySet()) {
            System.out.format("\033[1m===========%s==========\033[0m\n", key);
            System.out.println("Number : " + report.get(key).size() + " out of " + roomList.get(key).size());
            System.out.println("Rooms : ");
            for (Room room : report.get(key)) {
                System.out.println("\t"+ room.getRoomID());
            }
        }
    }

    /**
     * View room number by status type
     */
    private void showRoomByStatus() {
        HashMap<RoomStatus, List<Room>> roomByStatus;
        roomByStatus = (HashMap<RoomStatus, List<Room>>) RoomController.getInstance().splitRoomByStatus();

        for (RoomStatus key : roomByStatus.keySet()) {
            System.out.format("\033[1m===========%s==========\033[0m\n", key);
            for (Room room : roomByStatus.get(key)) {
                if (room.getRoomStatus() != RoomStatus.RESERVED)
                    System.out.println(room.getRoomID());
            }
            System.out.printf("\n");
        }
    }

}
