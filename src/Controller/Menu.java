package Controller;

import java.util.ArrayList; //Import ArrayList class
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Database.SerializeDB;
import Entity.Entities;
import Entity.Item;
import Enums.ItemTypes;

/***
 * Represents a Menu/Item Controller
 * 
 * @version 1.0
 * @since 2022-04-17
 */

public class Menu extends SerializeDB implements IController, IStorage {
	/**
     * The collection of items
     */
	private ArrayList<Item> itemList;
	/**
     * The Instance of this Controller
     */
	private static Menu single_instance = null;

	/**
     * Constructor
     */
	private Menu() {
		itemList = new ArrayList<Item>();
	}
	
	/**
     * Returns the Menu instance and creates an instance if it does not exist
     * 
     * @return Menu instance
     */
	public static Menu getInstance() {
		if (single_instance == null)
			single_instance = new Menu();
		return single_instance;
	}

	// Behaviours
	/**
     * Print all items sorted by itemType (Category)
     */
	public void printMenu() {
		System.out.println("Total number of Items: " + itemList.size() + "\n");
		for (ItemTypes itemType : ItemTypes.values()) {
			printCat(itemType);
		}
	}

	/**
     * Print items in a Category
     * 
     * @param itemType	the item type to be printed
     */
	public void printCat(ItemTypes itemType) {
		List<Item> types = splitItemByType().get(itemType);
		switch (itemType) {
			case APPETIZER:
				System.out.println("\033[1m=======Appetizers=======\033[0m");
				break;
			case ENTREE:
				System.out.println("\033[1m=========Entree=========\033[0m");
				break;
			case SIDE:
				System.out.println("\033[1m=========Sides=========\033[0m");
				break;
			case DESSERT:
				System.out.println("\033[1m========Desserts========\033[0m");
				break;
			case BEVERAGE:
				System.out.println("\033[1m=======Beverages=======\033[0m");
				break;
			default:
				break;
		}
		System.out.println("Number of items: " + types.size());
		for (Item item : types) {
			printItem(item);
		}
		System.out.println();
	}

	/**
     * Print item details
     * 
     * @param item	the Item object to be printed
     */
	public void printItem(Item item) {
		System.out.format("%s %s\n", item.getID(), item.getName());
		System.out.format("\033[3m%s\033[0m\n", item.getDesc());
		System.out.printf("Price: $%.2f\n\n", item.getPrice());
	}

	/**
     * Return Item object if itemID matches
     * 
     * @param itemID	ID of desired item
     * @return Item		The Item object with corresponding itemID
     */
	public Item checkExistance(String itemID) {
		for (Item item : itemList) {
			if (item.getID().compareTo(itemID) == 0)
				return item;
		}
		return null;
	}

	/**
     * Sort list of Item in ascending ID
     */
	public void sortData() {
		int a, b;
		Item temp;
		for (a = 0; a < itemList.size(); a++) {
			for (b = a; b > 0; b--) {
				temp = itemList.get(b);
				if (temp.getID().compareTo(itemList.get(b - 1).getID()) > 0)
					break;
				else {
					itemList.set(b, itemList.get(b - 1));
					itemList.set(b - 1, temp);
				}
			}
		}
	}

	/**
     * Downcast to Item and add to list of Items
     * 
     * @param entities	Item object to be saved into list
     */
	public void create(Object entities) {
		Item toBeAdded = (Item) entities;
		itemList.add(toBeAdded);
		cleanID();
		storeData();
	}

	/**
     * Print all itemIDs
     */
	public void read() {
		for (Item item : itemList) {
			System.out.println(item.getName());
		}
	}

	/**
     * Delete single Item Object from list of Items
     * 
     * @param entities	Item object to be deleted from list
     */
	public void delete(Object entities) {
		Item toBeDeleted = (Item) entities;
		itemList.remove(toBeDeleted);
		cleanID();
		storeData();
	}

	/**
     * Update field of Item with input values
     * 
     * @param entities Item object to be updated
     * @param choice   choice from UI
     * @param value    input from user to be pass to setters
     */
	public void update(Object entities, int choice, String value) {
		Item item = (Item) entities;
		switch (choice) {
			case 1: // itemID
				item.setID(value);
				sortData();
				break;
			case 2: // itemName
				item.setName(value);
				break;
			case 3: // itemDesc
				item.setDesc(value);
				break;
			case 4: // itemPrice
				try {
					Float F = Float.parseFloat(value);
					item.setPrice(F);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 5: // itemType
				item.setType(toType(value));
				break;
			default:
				break;
		}
		cleanID();
		storeData();
	}

	/**
     * Split items into separate lists according to type/category
     * 
     * @return Map list of items split by type/category
     */
	public Map<ItemTypes, List<Item>> splitItemByType() {
		Map<ItemTypes, List<Item>> itemByType = new HashMap<>();

		ArrayList<Item> appetizers = new ArrayList<Item>();
		ArrayList<Item> entrees = new ArrayList<Item>();
		ArrayList<Item> sides = new ArrayList<Item>();
		ArrayList<Item> desserts = new ArrayList<Item>();
		ArrayList<Item> beverages = new ArrayList<Item>();

		for (Item item : itemList) {
			if (item.getType() == ItemTypes.APPETIZER)
				appetizers.add(item);
			if (item.getType() == ItemTypes.ENTREE)
				entrees.add(item);
			if (item.getType() == ItemTypes.SIDE)
				sides.add(item);
			if (item.getType() == ItemTypes.DESSERT)
				desserts.add(item);
			if (item.getType() == ItemTypes.BEVERAGE)
				beverages.add(item);
		}

		itemByType.put(ItemTypes.APPETIZER, appetizers);
		itemByType.put(ItemTypes.ENTREE, entrees);
		itemByType.put(ItemTypes.SIDE, sides);
		itemByType.put(ItemTypes.DESSERT, desserts);
		itemByType.put(ItemTypes.BEVERAGE, beverages);

		return itemByType;
	}

	/**
     * Set ID of items according to number of items in the type/category
     * itemID = TXX (T represents itemType, XX represents item count)
     */
	private void cleanID() {
		Map<ItemTypes, List<Item>> itemByType = splitItemByType();
		int count = 1;
		for (Item item : itemByType.get(ItemTypes.APPETIZER))
			item.setID(String.valueOf(100 + (count++)));
		count = 1;
		for (Item item : itemByType.get(ItemTypes.ENTREE))
			item.setID(String.valueOf(200 + (count++)));
		count = 1;
		for (Item item : itemByType.get(ItemTypes.SIDE))
			item.setID(String.valueOf(300 + (count++)));
		count = 1;
		for (Item item : itemByType.get(ItemTypes.DESSERT))
			item.setID(String.valueOf(400 + (count++)));
		count = 1;
		for (Item item : itemByType.get(ItemTypes.BEVERAGE))
			item.setID(String.valueOf(500 + (count++)));
		sortData();
	}

	/**
     * Return item type given string input
     * 
     * @param type	String input to be converted to ItemTypes Enum
     * @return ItemTypes Enum assigned to input
     */
	public ItemTypes toType(String type) {
		switch (type.charAt(0)) {
			case '1':
				return ItemTypes.APPETIZER;
			case '2':
				return ItemTypes.ENTREE;
			case '3':
				return ItemTypes.SIDE;
			case '4':
				return ItemTypes.DESSERT;
			case '5':
				return ItemTypes.BEVERAGE;
			default:
				return null;
		}
	}

	/**
     * Return item type given int input
     * 
     * @param type int input to be converted to ItemTypes Enum
     * @return ItemTypes Enum assigned to input
     */
	public ItemTypes toType(int type) {
		switch (type) {
			case 1:
				return ItemTypes.APPETIZER;
			case 2:
				return ItemTypes.ENTREE;
			case 3:
				return ItemTypes.SIDE;
			case 4:
				return ItemTypes.DESSERT;
			case 5:
				return ItemTypes.BEVERAGE;
			default:
				return null;
		}
	}

	/**
     * Store list of Items into serializable file
     */
	public void storeData() {
		super.storeData("Menu.ser", itemList);
		cleanID();
	}

	/**
     * Loads list of Items from serializable file
     */
	public void loadData() {
		ArrayList<Entities> data = super.loadData("Menu.ser");
		itemList.clear();
		for (Entities item : data)
			itemList.add((Item) item);
	}
}
