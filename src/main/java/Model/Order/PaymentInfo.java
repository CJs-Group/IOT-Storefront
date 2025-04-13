// Payment Info is a class that stores the payment information for a customer.
// This is not deleted when the customer changes their payment info as it is needed for past orders

package Model.Order;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PaymentInfo {
    int paymentId;
    // Customer customer;
    String cardNo;
    int CCV;
    String expiryDate;
    String cardHolderName;

    public PaymentInfo(int paymentId, String cardNo, int CCV, String expiryDate, String cardHolderName) {
        this.paymentId = paymentId;
        this.cardNo = cardNo;
        this.CCV = CCV;
        this.expiryDate = expiryDate;
        this.cardHolderName = cardHolderName;
    }

    public PaymentInfo() {
        this.paymentId = -1;
        this.cardNo = null;
        this.CCV = 0;
        this.expiryDate = null;
        this.cardHolderName = null;
    }

    public boolean validate() throws DateTimeParseException {
        if(cardNo.length() != 16) {
            System.out.println(cardNo + " is not a valid card number");
            System.out.println("Card number length must be 16");
            return false;
        }
        try {
            if(java.time.YearMonth.parse(expiryDate, DateTimeFormatter.ofPattern("MM/yy")).isBefore(java.time.YearMonth.now())) {
                System.out.println("Expiration Date is before current date");
                return false;
            }
        }
        catch (DateTimeParseException e) {
            System.out.println("DateTimeParseException occurred");
            return false;
        }

        return String.valueOf(CCV).length() == 3;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public int getCCV() {
        return CCV;
    }

    public void setCCV(int CCV) {
        this.CCV = CCV;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }
}
