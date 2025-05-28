package Model.Order;

import Model.Items.Unit;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {
    @Test
    void testConstructorAndGetters() {
        Unit unit = new Unit(1, null, null, null);
        OrderItem orderItem = new OrderItem(10, unit, 2, 500);
        assertEquals(10, orderItem.getOrderItemID());
        assertEquals(unit, orderItem.getUnit());
        assertEquals(2, orderItem.getQuantity());
        assertEquals(500, orderItem.getPriceAtPurchase());
    }

    @Test
    void testSetters() {
        Unit unit = new Unit(1, null, null, null);
        OrderItem orderItem = new OrderItem(10, unit, 2, 500);
        orderItem.setOrderItemID(20);
        orderItem.setUnit(unit);
        orderItem.setQuantity(5);
        orderItem.setPriceAtPurchase(1000);
        assertEquals(20, orderItem.getOrderItemID());
        assertEquals(unit, orderItem.getUnit());
        assertEquals(5, orderItem.getQuantity());
        assertEquals(1000, orderItem.getPriceAtPurchase());
    }
}
