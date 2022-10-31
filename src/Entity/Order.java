package Entity;

import java.util.ArrayList;
import java.text.SimpleDateFormat;

import Enums.OrderStatus;

/***
 * Represents a Order
 * Order comprises of a list of items
 * A room can have many orders
 * 
 * @version 1.0
 * @since 2022-04-17
 */
public class Order extends Entities {
    private String orderID;
    private String roomID;
    private OrderStatus orderStatus; // confirmed, preparing, delivered
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    private String date;
    private ArrayList<Item> listOfFood;
    private String remarks;

    /**
     * Constructor
     * 
     * @param roomID
     */
    public Order(String roomID) {
        this.roomID = roomID;
    }

    /**
     * @return int Return size of Order
     */
    public int size() {
        return this.listOfFood.size();
    }

    /**
     * @param orderID Set orderID
     */
    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    /**
     * @return String Return orderID
     */
    public String getOrderID() {
        return orderID;
    }

    /**
     * @param roomID Set RoomID
     */
    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    /**
     * @return String Return RoomID
     */
    public String getRoomID() {
        return roomID;
    }

    /**
     * Order status is to be change manually
     * due to the different stages it can go through
     * 
     * @param status Set order status
     */
    public void setOrderStatus(OrderStatus status) {
        this.orderStatus = status;
    }

    /**
     * @return OrderStatus Return Order status
     */
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    /**
     * @param date Set date of order
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return String Return date of order
     */
    public String getDate() {
        return date;
    }

    /**
     * @return ArrayList<Item> Return list of item in order
     */
    public ArrayList<Item> getListOfFood() {
        return listOfFood;
    }

    /**
     * @param listOfFood Set list of item in order
     */
    public void setListOfFood(ArrayList<Item> listOfFood) {
        this.listOfFood = listOfFood;
    }

    /**
     * @param remarks Set Remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return String Return remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Generate formatted Order
     * 
     * @return float Return total cost of current order
     */
    public float viewOrder() {

        sortOrder();

        float cost = 0;

        System.out.println();
        System.out.println("==============================");
        System.out.println("Items ordered for Room Service");
        System.out.println("==============================");
        System.out.println("Order ID: " + orderID);
        Item temp = null;
        int count = 0;
        for (Item food : listOfFood) {
            if (temp == food) {
                count++;
            } else {
                if (count != 0)
                    System.out.println("\t" + count);
                temp = food;
                System.out.print(food);
                count = 1;
            }
            cost += food.getPrice();
        }
        System.out.println("\t" + count);
        System.out.println("Total cost: $" + cost);
        System.out.printf("Status:\t");
        switch (orderStatus) {
            case CONFIRM:
                System.out.println("Confirmed");
                break;
            case PREPARING:
                System.out.println("Preparing");
                break;
            case DELIVERED:
                System.out.println("Delivered");
                break;
            case PAID:
                System.out.println("Paid");
                break;
        }
        System.out.println();
        return cost;
    }

    /**
     * @return String Return formatted Order
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append(this.getClass().getName() + newLine);

        result.append("Date: " + this.date + newLine);
        result.append("orderID: " + this.orderID + "\tStatus: " + this.orderStatus + newLine);
        result.append("For Room: " + this.roomID + newLine);
        result.append("Remarks: " + this.remarks + newLine);

        return result.toString();
    }

    /**
     * Sort item in ascending
     */
    public void sortOrder() {
        int a, b;
        Item temp;
        for (a = 0; a < listOfFood.size(); a++) {
            for (b = a; b > 0; b--) {
                temp = listOfFood.get(b);
                if (temp.getID().compareTo(listOfFood.get(b - 1).getID()) > 0)
                    break;
                else {
                    listOfFood.set(b, listOfFood.get(b - 1));
                    listOfFood.set(b - 1, temp);
                }
            }
        }
    }

}
