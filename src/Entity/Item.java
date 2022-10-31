package Entity;

import Enums.ItemTypes;

/***
 * Represents a Item
 * To be used in Menu and Order
 * 
 * @version 1.0
 * @since 2022-04-17
 */
public class Item extends Entities {
	private String itemID;
	private String itemName;
	private String itemDesc;
	private float itemPrice;
	private ItemTypes itemType;

	/**
	 * Constructor
	 */
	public Item() {

	}

	/**
	 * Constructor with attributes as parameters
	 * 
	 * @param itemName
	 * @param itemDesc
	 * @param itemPrice
	 * @param itemType
	 */
	public Item(String itemName, String itemDesc, float itemPrice, ItemTypes itemType) {
		this.itemName = itemName;
		this.itemDesc = itemDesc;
		this.itemPrice = itemPrice;
		this.itemType = itemType;
	}

	/**
	 * @return String Return item ID
	 */
	public String getID() {
		return itemID;
	}

	/**
	 * @return String Return name of item
	 */
	public String getName() {
		return itemName;
	}

	/**
	 * @return String Return description of item
	 */
	public String getDesc() {
		return itemDesc;
	}

	/**
	 * @return float Return price of item
	 */
	public float getPrice() {
		return itemPrice;
	}

	/**
	 * @return ItemTypes Return type of item
	 */
	public ItemTypes getType() {
		return itemType;
	}

	/**
	 * @param ID Set item iD
	 */
	public void setID(String ID) {
		itemID = ID;
	}

	/**
	 * @param name Set item name
	 */
	public void setName(String name) {
		itemName = name;
	}

	/**
	 * @param desc Set item description
	 */
	public void setDesc(String desc) {
		itemDesc = desc;
	}

	/**
	 * @param price Set item price
	 */
	public void setPrice(float price) {
		itemPrice = price;
	}

	/**
	 * @param type Set item type
	 */
	public void setType(ItemTypes type) {
		itemType = type;
	}

	/**
	 * @return String Return formatted String with item atrributes
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append(this.itemID + "\t" + this.itemName + "\t $" + this.itemPrice);

		return result.toString();
	}
}
