// Order is a list of items allocated to a person when they purchase something.

package Model.Order;
import Model.Order.OrderItem;
import java.util.List;

public class Order {
    List<OrderItem> orderItems;
    int orderID;
    int quantity;
    int priceAtPurchase; // divided by 100 to get the actual price

    public Order(List<OrderItem> orderItems, int orderID, int quantity, int priceAtPurchase) {
        this.orderItems = orderItems;
        this.orderID = orderID;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(int priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }
}
