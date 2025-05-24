package Model.Users;
import Model.Basket.Basket;
import Model.Order.PaymentInfo;

public class Customer extends User {
    PaymentInfo paymentInfo;
    String address;

    public Customer(int userID, String username, String password, String email, String phoneNumber, String address, PaymentInfo paymentInfo, AccountType accountType) {
        super(userID, username, password, email, phoneNumber, accountType);
        this.address = address;
        this.paymentInfo = paymentInfo;
    }

    public Customer(int userID, String username, String password, String email, String phoneNumber, AccountType accountType) {
        super(userID, username, password, email, phoneNumber, accountType);
        paymentInfo = new PaymentInfo();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PaymentInfo getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(PaymentInfo paymentInfo) {
        this.paymentInfo = paymentInfo;
    }
}