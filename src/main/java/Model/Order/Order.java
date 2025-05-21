package Model.Order;

import Model.Users.User;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Order {
    private int orderID;
    private int userID;
    private User user;
    private List<OrderItem> orderItems;
    private Date orderDate;
    private OrderStatus orderStatus;
    private String shippingAddress;
    private String receipt;
    private Date eta;

    public Order(int userID, Date orderDate, OrderStatus orderStatus, String shippingAddress) {
        this.userID = userID;
        this.orderItems = new ArrayList<>();
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.shippingAddress = shippingAddress;
    }

    public Order(int orderID, int userID, Date orderDate, OrderStatus orderStatus, String shippingAddress, String receipt, Date eta) {
        this.orderID = orderID;
        this.userID = userID;
        this.orderItems = new ArrayList<>();
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.shippingAddress = shippingAddress;
        this.receipt = receipt;
        this.eta = eta;
    }


    // Big block of getters and setters :/
    public int getOrderID() { return orderID; }
    public int getUserID() { return userID; }
    public User getUser() { return user; }
    public List<OrderItem> getOrderItems() { return orderItems; }
    public Date getOrderDate() { return orderDate; }
    public OrderStatus getOrderStatus() { return orderStatus; }
    public String getShippingAddress() { return shippingAddress; }
    public String getReceipt() { return receipt; }
    public Date getEta() { return eta; }

    public void setOrderID(int orderID) { this.orderID = orderID; }
    public void setUserID(int userID) { this.userID = userID; }
    public void setUser(User user) { this.user = user; }
    public void setOrderItems(List<OrderItem> orderItems) { this.orderItems = orderItems; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }
    public void setOrderStatus(OrderStatus orderStatus) { this.orderStatus = orderStatus; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public void setReceipt(String receipt) { this.receipt = receipt; }
    public void setEta(Date eta) { this.eta = eta; }

    public void addOrderItem(OrderItem item) {
        if (this.orderItems == null) {
            this.orderItems = new ArrayList<>();
        }
        this.orderItems.add(item);
    }

    public int getTotalPrice() {
        if (orderItems == null) {
            return 0;
        }
        int total = 0;
        for (OrderItem item : orderItems) {
            total += item.getPriceAtPurchase() * item.getQuantity();
        }
        return total;
    }
}