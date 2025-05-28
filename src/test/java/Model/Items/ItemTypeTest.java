package Model.Items;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemTypeTest {
    @Test
    void testConstructorAndGetters() {
        ItemType item = new ItemType(1, 1000, "TestName", "TestDesc", null, "img.png");
        assertEquals(1, item.getItemID());
        assertEquals(1000, item.getPrice());
        assertEquals("TestName", item.getName());
        assertEquals("TestDesc", item.getDescription());
        assertEquals("img.png", item.getImagePath());
    }

    @Test
    void testSetters() {
        ItemType item = new ItemType(1, 1000, "TestName", "TestDesc", null, "img.png");
        item.setPrice(2000);
        item.setName("NewName");
        item.setDescription("NewDesc");
        item.setImagePath("newimg.png");
        assertEquals(2000, item.getPrice());
        assertEquals("NewName", item.getName());
        assertEquals("NewDesc", item.getDescription());
        assertEquals("newimg.png", item.getImagePath());
    }
}
