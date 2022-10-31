package Entity;

import java.util.Date;

/***
 * Represents a Creditcard
 * 
 * @version 1.0
 * @since 2022-04-17
 */
public class Guest extends Entities {

    /**
     * Guest can be driver license or passport
     */
    private String guestID;

    private String guestName;
    private String address;
    private String contact;
    private String country;
    private char gender;
    private String nationality;
    private Creditcard card;

    /**
     * Constructors with attributes as parameters
     * 
     * @param guestID
     * @param guestName
     * @param address
     * @param contact
     * @param country
     * @param gender
     * @param nationality
     * @param cardNumber
     * @param expDate
     * @param CVV
     * @param type
     * @param cardName
     */
    public Guest(String guestID, String guestName, String address, String contact, String country, char gender,
            String nationality, String cardNumber, Date expDate, int CVV, int type, String cardName) {
        this.guestID = guestID;
        this.guestName = guestName;
        this.address = address;
        this.contact = contact;
        this.country = country;
        this.gender = gender;
        this.nationality = nationality;
        this.card = new Creditcard(cardNumber, expDate, CVV, type, cardName);
    }

    /**
     * @return String Return guest ID
     */
    public String getID() {
        return this.guestID;
    }

    /**
     * @param guestID Set guest ID
     */
    public void setGuestID(String guestID) {
        this.guestID = guestID;
    }

    /**
     * @return String Return guest name
     */
    public String getGuestName() {
        return guestName;
    }

    /**
     * @param guestName Set guest name
     */
    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    /**
     * @return String Return address of guest
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address Set address of guest
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return String Return contact number of guest
     */
    public String getContact() {
        return contact;
    }

    /**
     * @param contact Set contact number of guest
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * @return String Return country of birth of guest
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country Set country of birth of guest
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return char Return gender of guest M/F
     */
    public char getGender() {
        return gender;
    }

    /**
     * @param gender Set gender of guest M/F
     */
    public void setGender(char gender) {
        this.gender = gender;
    }

    /**
     * @return String Return nationality of guest
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * @param nationality Set nationality of guest
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * @return Creditcard Return credit card object
     */
    public Creditcard getCard() {
        return card;
    }

    /**
     * @param card Set creditcard object
     */
    public void setCard(Creditcard card) {
        this.card = card;
    }

    /**
     * @return String Return formatted guest details
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        // result.append(this.getClass().getName() + newLine);
        result.append(newLine);
        result.append("Guest ID: " + this.guestID + newLine);
        result.append("Guest Name: " + this.guestName + newLine);
        result.append("Address: " + this.address + newLine);
        result.append("Contact: " + this.contact + newLine);
        result.append("Country: " + this.country + newLine);
        result.append("Gender: " + this.gender + newLine);
        result.append("Nationality: " + this.nationality + newLine);
        result.append("Credit Card Details " + newLine + this.card);

        return result.toString();
    }

}
