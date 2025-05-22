package Model.Basket;

import Model.Items.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BasketTest {
    private Basket basket;
    private ItemType itemType;
    private BasketItem basketItem;

    @BeforeEach
    void setUp() {
        basket = new Basket(1);
        itemType = new ItemType(100, 200, "Test Item", "desc", null, null);
        basketItem = new BasketItem(1, itemType, 2, 200);
    }

    @Test
    void testAddBasketItem() {
        basket.addBasketItem(basketItem);
        List<BasketItem> items = basket.getItems();
        assertEquals(1, items.size());
        assertEquals(basketItem, items.get(0));
    }

    @Test
    void testGetTotalPrice() {
        basket.addBasketItem(basketItem); // 2 * 200 = 400
        assertEquals(400, basket.getTotalPrice());
    }

    @Test
    void testGetBasketItemByType() {
        basket.addBasketItem(basketItem);
        BasketItem found = basket.getBasketItemByType(100);
        assertNotNull(found);
        assertEquals(basketItem, found);
    }

    @Test
    void testEmptyBasketTotalPrice() {
        assertEquals(0, basket.getTotalPrice());
    }
}
