package Model.Users;
import Model.Items.ItemType;
import Model.Order.PaymentInfo;

import java.util.HashMap; //Dunno if I should be using hash maps but I'll think about it later because Java doesn't have pairs
import java.util.Map;

public class Customer extends User {
    // Cart and wishlist are maps of ItemTypes, not Units, this is because the units are unallocated until purchasing.
    Map<ItemType, Integer> cart = new HashMap<>(); // Item to quantity
    Map<ItemType, Integer> wishlist = new HashMap<>(); // Item to quantity
    PaymentInfo paymentInfo;
    String address;

    public Customer(int userID, String username, String password, String email, String phoneNumber, String address, PaymentInfo paymentInfo) {
        super(userID, username, password, email, phoneNumber);
        this.address = address;
        this.paymentInfo = paymentInfo;
    }

    public Customer(int userID, String username, String password, String email, String phoneNumber) {
        super(userID, username, password, email, phoneNumber);
    }

    public Map<ItemType, Integer> getCart() {
        return cart;
    }

    public void setCart(Map<ItemType, Integer> cart) {
        this.cart = cart;
    }

    public void addToCart(ItemType item, int quantity) {
        cart.put(item, cart.getOrDefault(item, 0) + quantity);
    }

    public void removeFromCart(ItemType item) {
        cart.remove(item);
    }

    public Map<ItemType, Integer> getWishlist() {
        return wishlist;
    }

    public void setWishlist(Map<ItemType, Integer> wishlist) {
        this.wishlist = wishlist;
    }

    public void addToWishlist(ItemType item, int quantity) {
        wishlist.put(item, wishlist.getOrDefault(item, 0) + quantity);
    }

    public void removeFromWishlist(ItemType item) {
        wishlist.remove(item);
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