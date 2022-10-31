package Controller;

import java.text.SimpleDateFormat;

import Enums.OrderStatus;

import java.util.*;

import Database.SerializeDB;
import Entity.Entities;
import Entity.Item;
import Entity.Order;

/***
 * Represents a Order Controller
 * 
 * @version 1.0
 * @since 2022-04-17
 */

public class OrderController extends SerializeDB implements IController, IStorage {
	/**
     * The collection of orders
     */
    private ArrayList<Order> orderList;
    /**
     * The Instance of this Controller
     */
    private static OrderController instance = null;
    /**
     * Date formatter
     */
    SimpleDateFormat formatter;

    /**
     * Constructor
     * 
     * Sets date format to dd/MM/yyyy hh:mm:ss
     */
    private OrderController() {
        orderList = new ArrayList<Order>();
        formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    }

    /**
     * Returns the OrderController instance and creates an instance if it does not exist
     * 
     * @return OrderController instance
     */
    public static OrderController getInstance() {
        if (instance == null) {
            instance = new OrderController();
        }
        return instance;
    }

    /**
     * Return Order object if orderID matches
     * 
     * @param orderID	ID of desired order
     * @return order	The Order object with corresponding orderID
     */
    public Order checkExistence(String orderID) { // by orderID eg. 02021
        for (Order order : orderList) {
            if (order.getOrderID().compareTo(orderID) == 0)
                return order;
        }
        return null;
    }

    /**
     * Downcast to Order and add to list of Orders
     * Sets orderID of order as roomID + date
     * 
     * @param entities	Order object to be saved into list
     */
    public void create(Object entities) {
        Order order = (Order) entities;

        Calendar c = Calendar.getInstance();
        Date time = c.getTime();
        String date = formatter.format(time);
        order.setDate(date);
        SimpleDateFormat formatID = new SimpleDateFormat("ddMMyyhhmm");
        order.setOrderID(order.getRoomID() + formatID.format(time));

        order.setOrderStatus(OrderStatus.CONFIRM);
        order.setListOfFood(new ArrayList<Item>());

        orderList.add(order);

        // storeData();
        storeData("Order.ser", orderList);
    }

    /**
     * Delete single Order Object from list of Orders
     * Order object cannot be deleted if it in delivered status
     * 
     * @param entities	Order object to be deleted from list
     */
    public void delete(Object entities) {

        Order toBeDeleted = (Order) entities;

        if (toBeDeleted.getOrderStatus() != OrderStatus.DELIVERED) {
            orderList.remove(toBeDeleted);
            System.out.println("Order is deleted.");
            return;
        }

        System.out.println("Order can't be deleted as it is delivered.");
        storeData();
    }

    /**
     * Update field of Order with input values
     * 
     * @param entities Order object to be updated
     * @param choice   choice from UI
     * @param value    input from user to be pass to setters
     */
    public void update(Object entities, int choice, String value) {

        Order order = (Order) entities;

        switch (choice) {
            case 1: // roomID
                order.setRoomID(value);
                break;
            case 2: // remarks
                order.setRemarks(value);
                break;
            case 3: // orderStatus
                OrderStatus status = generateStatus(value);
                order.setOrderStatus(status);
                break;
        }

        storeData();
    }

    /**
     * Add Item to existing Order
     * Item can only be added if order is in the confirmed status
     * 
     * @param order	Order object to be added with item
     * @param itemToAdd	Item object to be added to order
     */
    public void addItemtoOrder(Order order, Item itemToAdd) {
        if (order.getOrderStatus() != OrderStatus.CONFIRM) {
            System.out.println("Order is preparing or is delivered, no changes can be made.");
            return;
        }
        order.getListOfFood().add(itemToAdd);
        storeData();
    }

    /**
     * Delete Item to existing Order
     * Item can only be deleted if order is in the confirmed status
     * Order is deleted if order has no remaining items
     * 
     * @param order	Order object to be delete item from
     * @param itemToDelete Item object to be deleted
     */
    public void deleteItemfromOrder(Order order, Item itemToDelete) 
    {
            if (order.getOrderStatus() != OrderStatus.CONFIRM) {
                System.out.println("Order is preparing or is delivered, no changes can be made.");
                return;
            }
            if (order.getListOfFood().remove(itemToDelete)) {
                System.out.println("Item removed from order " + order.getOrderID());
                if (order.getListOfFood().size() == 0) {
                    System.out.println("No items left... Deleting order...");
                    orderList.remove(order);
                }
                storeData();
            }
            else {
              System.out.println("Order " + order.getOrderID() + " does not have item " + itemToDelete.getID());
            }
            storeData();
    }

    /**
     * Print all OrderIDs
     */
    public void read() {
        for (Order order : orderList) {
            System.out.println(order.getOrderID());
        }
    }

    /**
     * Print single Order details
     * 
     * @param order Order object to view list of items in order
     */
    public void read(Order order) {
        order.viewOrder();
    }

    /**
     * Returns all existing orders from a room
     * 
     * @param roomID ID or room which made order
     * @return ArrayList of Order full list of all orders made by the room
     */
    public ArrayList<Order> retrieveOrdersOfRoom(String roomID) { // by roomID
        ArrayList<Order> retrieveOL = new ArrayList<Order>();
        for (Order order : orderList) {
            if (order.getRoomID().compareTo(roomID) == 0 && order.getOrderStatus() != OrderStatus.PAID)
                retrieveOL.add(order);
        }
        if (retrieveOL.size() > 0)
            return retrieveOL;
        else
            return null;
    }

    /**
     * Return order status given string input
     * 
     * @param value String input to be converted to OrderStatus Enum
     * @return OrderStatus Enum assigned to input
     */
    private OrderStatus generateStatus(String value) {
        int choice = Integer.parseInt(value);
        switch (choice) {
            case 1:
                return OrderStatus.CONFIRM;
            case 2:
                return OrderStatus.PREPARING;
            case 3:
                return OrderStatus.DELIVERED;
            case 4:
                return OrderStatus.PAID;
            default:
                return null;
        }
    }

    /**
     * Store list of Orders into serializable file
     */
    public void storeData() {
        super.storeData("Order.ser", orderList);
    }

    /**
     * Loads list of Orders from serializable file
     */
    public void loadData() {
        ArrayList<Entities> data = super.loadData("Order.ser");
        orderList.clear();
        for (Entities order : data)
            orderList.add((Order) order);
    }

}
