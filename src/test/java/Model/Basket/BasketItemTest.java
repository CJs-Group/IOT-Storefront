package Model.Basket;

import Model.Items.ItemType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BasketItemTest {
    @Test
    void testConstructorAndGettersSetters() {
        ItemType itemType = new ItemType(1, 100, "Test", "desc", null, "img.png");
        BasketItem item = new BasketItem(10, 20, itemType, 2, 100);
        assertEquals(Integer.valueOf(10), Integer.valueOf(item.getBasketItemID()));
        assertEquals(Integer.valueOf(20), Integer.valueOf(item.getBasketID()));
        assertEquals(itemType, item.getItemType());
        assertEquals(Integer.valueOf(2), Integer.valueOf(item.getQuantity()));
        assertEquals(Integer.valueOf(100), Integer.valueOf(item.getPriceAtPurchase()));
        item.setBasketItemID(11);
        item.setBasketID(21);
        item.setItemType(itemType);
        item.setQuantity(3);
        item.setPriceAtPurchase(200);
        assertEquals(Integer.valueOf(11), Integer.valueOf(item.getBasketItemID()));
        assertEquals(Integer.valueOf(21), Integer.valueOf(item.getBasketID()));
        assertEquals(itemType, item.getItemType());
        assertEquals(Integer.valueOf(3), Integer.valueOf(item.getQuantity()));
        assertEquals(Integer.valueOf(200), Integer.valueOf(item.getPriceAtPurchase()));
    }
}
