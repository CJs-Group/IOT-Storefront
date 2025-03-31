package uts.isd.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CreditCard {
    private String cardNumber;
    private String fullName;
    private String expirationDate;
    private String cvv;

    public CreditCard(String cardNumber, String fullName, String expirationDate, String cvv) {
        this.cardNumber = cardNumber;
        this.fullName = fullName;
        this.expirationDate = expirationDate.trim();
        this.cvv = cvv;
    }

    public boolean validate() throws DateTimeParseException {
        if(cardNumber.length() != 17) {
            System.out.println(cardNumber + " is not a valid card number");
            System.out.println("Card number length must be 16");
            return false;
        }
        try{
            if(LocalDate.parse(expirationDate, DateTimeFormatter.ofPattern("dd/MM/yy")).isBefore(LocalDate.now())) {
                System.out.println("Expiration Date is before current date");
                return false;
            }
        } catch (DateTimeParseException e) {
            System.out.println("DateTimeParseException occurred");
            return false;
        }

        return cvv.length() == 3;
    }

    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getExpirationDate() {
        return expirationDate;
    }
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
    public String getCvv() {
        return cvv;
    }

}
