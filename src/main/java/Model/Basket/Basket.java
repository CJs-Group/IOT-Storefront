package Model.Basket;

// import Model.Users.User;
import java.util.ArrayList;
import java.util.List;

public class Basket {
    private int basketID;
    private int userID;
    private List<BasketItem> items;

    public Basket(int userID) {
        this.userID = userID;
        this.items = new ArrayList<>();
    }

    public Basket(int basketID, int userID) {
        this.basketID = basketID;
        this.userID = userID;
        this.items = new ArrayList<>();
    }

    public int getBasketID() {
        return basketID;
    }

    public int getUserID() {
        return userID;
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public void setBasketID(int basketID) {
        this.basketID = basketID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setItems(List<BasketItem> items) {
        this.items = items;
    }

    public void addBasketItem(BasketItem item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.add(item);
    }

    public int getTotalPrice() {
        if (items == null) {
            return 0;
        }
        int total = 0;
        for (BasketItem item : items) {
            Integer priceAtPurchase = item.getPriceAtPurchase();
            if (priceAtPurchase != null) {
                total += priceAtPurchase * item.getQuantity();
            }
        }
        return total;
    }

    public BasketItem getBasketItemByType(int itemTypeID) {
        return items.stream()
                    .filter(item -> item.getItemType().getItemID() == itemTypeID)
                    .findFirst()
                    .orElse(null);
    }
}