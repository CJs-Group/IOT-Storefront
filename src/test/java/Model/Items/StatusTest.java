package Model.Items;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StatusTest {
    @Test
    void testFromString() {
        assertEquals(Status.Restocking, Status.fromString("Restocking"));
        assertEquals(Status.In_Stock, Status.fromString("In_Stock"));
        assertEquals(Status.Reserved, Status.fromString("Reserved"));
        assertEquals(Status.Sold, Status.fromString("Sold"));
        assertEquals(Status.Out_for_Delivery, Status.fromString("Out_for_Delivery"));
        assertEquals(Status.Delivered, Status.fromString("Delivered"));
        assertThrows(IllegalArgumentException.class, () -> Status.fromString("Unknown"));
    }
}
