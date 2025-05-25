package Model.Users;
import Model.Basket.Basket;
import Model.Order.PaymentInfo;
import Model.Users.AccountType;

public class Customer extends User {
    PaymentInfo paymentInfo;
    String address;
    AccountType accountType;

    public Customer(int userID, String username, String password, String email, String phoneNumber, String address, PaymentInfo paymentInfo, AccountType accountType) {
        super(userID, username, password, email, phoneNumber);
        this.address = address;
        this.paymentInfo = paymentInfo;
        this.accountType = accountType;
    }

    public Customer(int userID, String username, String password, String email, String phoneNumber, AccountType accountType) {
        super(userID, username, password, email, phoneNumber);
        this.paymentInfo = new PaymentInfo();
        this.accountType = accountType;
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

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}