package Entity;

import Enums.CreditcardTypes;

import java.util.Date;
import java.text.SimpleDateFormat;

/***
 * Represents a Creditcard
 * 
 * @version 1.0
 * @since 2022-04-17
 */
public class Creditcard extends Entities {
	private String cardNumber;
	private Date expiryDate;
	private int CVV;
	private CreditcardTypes cardType;
	private String registeredName;

	/**
	 * Constructor
	 */
	public Creditcard() {

	}

	/**
	 * Constructor with attributes as parameters
	 * 
	 * @param cardNumber
	 * @param expiryDate
	 * @param CVV
	 * @param type
	 * @param cardName
	 */
	public Creditcard(String cardNumber, Date expiryDate, int CVV, int type, String cardName) {
		this.cardNumber = cardNumber;
		this.expiryDate = expiryDate;
		this.CVV = CVV;
		switch (type) {
			case 1:
				cardType = CreditcardTypes.VISA;
				break;
			case 2:
				cardType = CreditcardTypes.MASTER;
				break;
			case 3:
				cardType = CreditcardTypes.AMEX;
				break;
			default:
				cardType = null;
				break;
		}
		this.registeredName = cardName;
	}

	/**
	 * @return String Return card number
	 */
	public String getCardNumber() {
		return cardNumber;
	}

	/**
	 * @param cardNumber Set card number
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * @return Date Return card expiry date
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate Set card expiry date
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @return int Return CVV
	 */
	public int getCVV() {
		return CVV;
	}

	/**
	 * @param CVV Set CVV
	 */
	public void setCVV(int CVV) {
		this.CVV = CVV;
	}

	/**
	 * @return CreditcardTypes Return type of credit card
	 */
	public CreditcardTypes getCreditcardType() {
		return cardType;
	}

	/**
	 * @param cardType Set type of credit card
	 */
	public void setCreditcardType(CreditcardTypes cardType) {
		this.cardType = cardType;
	}

	/**
	 * @return String Return name on card
	 */
	public String getRegisteredName() {
		return registeredName;
	}

	/**
	 * @param name Set name on card
	 */
	public void setRegisteredName(String name) {
		registeredName = name;
	}

	/**
	 * @return String Return formatted Card details
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		String newLine = System.getProperty("line.separator");
		SimpleDateFormat formatter = new SimpleDateFormat("MM/yy");

		//result.append(this.getClass().getName() + newLine);
		result.append("Registered Name: " + this.registeredName + newLine);
		result.append("Card Number: " + this.cardNumber + newLine);
		result.append("Expiry Date: " + formatter.format(this.expiryDate) + newLine);
		result.append("CVV: " + String.format("%03d", this.CVV) + newLine);
		result.append("Creditcard Type: " + this.cardType.toString() + newLine);
		

		return result.toString();
	}
}
