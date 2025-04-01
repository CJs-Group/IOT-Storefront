// Payment Info is a class that stores the payment information for a customer.
// This is not deleted when the customer changes their payment info as it is needed for past orders

package Model.Order;

// import java.util.Date; //We're just storing 4 numbers for expiry date
import Model.Users.Customer;

public class PaymentInfo {
    int paymentId;
    // Customer customer;
    String cardNo;
    int CCV;
    int expiryDate;
    String cardHolderName;

    public PaymentInfo(int paymentId, String cardNo, int CCV, int expiryDate, String cardHolderName) {
        this.paymentId = paymentId;
        this.cardNo = cardNo;
        this.CCV = CCV;
        this.expiryDate = expiryDate;
        this.cardHolderName = cardHolderName;
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

    public int getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(int expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }
}
