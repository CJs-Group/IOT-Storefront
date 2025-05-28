package Model.Order;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderStatusTest {
    @Test
    void testFromString() {
        assertEquals(OrderStatus.Saved, OrderStatus.fromString("Saved"));
        assertEquals(OrderStatus.Pending, OrderStatus.fromString("Pending"));
        assertEquals(OrderStatus.Processing, OrderStatus.fromString("Processing"));
        assertEquals(OrderStatus.Shipped, OrderStatus.fromString("Shipped"));
        assertEquals(OrderStatus.Completed, OrderStatus.fromString("Completed"));
        assertEquals(OrderStatus.Cancelled, OrderStatus.fromString("Cancelled"));
        assertThrows(IllegalArgumentException.class, () -> OrderStatus.fromString("Unknown"));
    }
}
