package Model.Users;
import Model.Items.ItemType;
import Model.Order.PaymentInfo;
import Model.Users.Basket;

public class Customer extends User {
    private Basket basket = new Basket();
    private Basket wishlist = new Basket();
    PaymentInfo paymentInfo;
    String address;

    public Customer(int userID, String username, String password, String email, String phoneNumber, String address, PaymentInfo paymentInfo) {
        super(userID, username, password, email, phoneNumber);
        this.address = address;
        this.paymentInfo = paymentInfo;
        basket = new Basket();
        wishlist = new Basket();
    }

    public Customer(int userID, String username, String password, String email, String phoneNumber) {
        super(userID, username, password, email, phoneNumber);
        basket = new Basket();
        wishlist = new Basket();
        paymentInfo = new PaymentInfo();
    }

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket cart) {
        this.basket = cart;
    }

    public Basket getWishlist() {
        return wishlist;
    }

    public void setWishlist(Basket wishlist) {
        this.wishlist = wishlist;
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