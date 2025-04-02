// OrderItem is essentially a wrapper class around each unit allowing the storing of quantity and price at purchase

package Model.Order;

import Model.Items.Unit;

public class OrderItem {
    int orderItemID;
    Unit unit;
    int quantity;
    int priceAtPurchase; // divided by 100 to get the actual price

    public OrderItem(int orderItemID, Unit unit, int quantity, int priceAtPurchase) {
        this.orderItemID = orderItemID;
        this.unit = unit;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public int getOrderItemID() {
        return orderItemID;
    }

    public void setOrderItemID(int orderItemID) {
        this.orderItemID = orderItemID;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
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
