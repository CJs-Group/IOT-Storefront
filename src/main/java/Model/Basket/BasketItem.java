package Model.Basket;

import Model.Items.ItemType;

public class BasketItem {
    private int basketItemID;
    private int basketID;
    private ItemType itemType;
    private int quantity;
    private Integer priceAtPurchase;

    public BasketItem(int basketID, ItemType itemType, int quantity, Integer priceAtPurchase) {
        this.basketID = basketID;
        this.itemType = itemType;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public BasketItem(int basketItemID, int basketID, ItemType itemType, int quantity, Integer priceAtPurchase) {
        this.basketItemID = basketItemID;
        this.basketID = basketID;
        this.itemType = itemType;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    
    public int getBasketItemID() {
        return basketItemID;
    }
    
    public int getBasketID() {
    return basketID;
    }
    
    public ItemType getItemType() {
        return itemType;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public Integer getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setBasketItemID(int basketItemID) {
        this.basketItemID = basketItemID;
    }

    public void setBasketID(int basketID) {
        this.basketID = basketID;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPriceAtPurchase(Integer priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }
}