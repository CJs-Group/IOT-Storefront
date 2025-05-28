package Model.Order;

import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    @Test
    void testConstructorAndGetters() {
        Date now = new Date();
        Order order = new Order(1, now, OrderStatus.Pending, "address");
        assertEquals(1, order.getUserID());
        assertEquals(now, order.getOrderDate());
        assertEquals(OrderStatus.Pending, order.getOrderStatus());
        assertEquals("address", order.getShippingAddress());
        assertNotNull(order.getOrderItems());
    }

    @Test
    void testSetters() {
        Date now = new Date();
        Order order = new Order(1, now, OrderStatus.Pending, "address");
        order.setOrderID(10);
        order.setUserID(2);
        order.setOrderDate(now);
        order.setOrderStatus(OrderStatus.Shipped);
        order.setShippingAddress("new address");
        order.setReceipt("receipt");
        assertEquals(10, order.getOrderID());
        assertEquals(2, order.getUserID());
        assertEquals(now, order.getOrderDate());
        assertEquals(OrderStatus.Shipped, order.getOrderStatus());
        assertEquals("new address", order.getShippingAddress());
        assertEquals("receipt", order.getReceipt());
    }
}
