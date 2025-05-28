package Model.Items;

import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class UnitTest {
    @Test
    void testConstructorAndGetters() {
        ItemType itemType = new ItemType(1, 1000, "TestName", "TestDesc", null, "img.png");
        Date now = new Date();
        Unit unit = new Unit(10, itemType, now, Status.In_Stock);
        assertEquals(10, unit.getUnitID());
        assertEquals(itemType, unit.getItemType());
        assertEquals(now, unit.getDateAdded());
        assertEquals(Status.In_Stock, unit.getStatus());
    }

    @Test
    void testSetters() {
        ItemType itemType = new ItemType(1, 1000, "TestName", "TestDesc", null, "img.png");
        Date now = new Date();
        Unit unit = new Unit(10, itemType, now, Status.In_Stock);
        unit.setUnitID(20);
        unit.setItemType(itemType);
        Date newDate = new Date(now.getTime() + 1000);
        unit.setDateAdded(newDate);
        unit.setStatus(Status.Sold);
        assertEquals(20, unit.getUnitID());
        assertEquals(itemType, unit.getItemType());
        assertEquals(newDate, unit.getDateAdded());
        assertEquals(Status.Sold, unit.getStatus());
    }
}
