package Model.Users;

import java.util.HashMap;
import java.util.Map;

import Model.Items.ItemType;

public class Basket {
    private Map<ItemType, Integer> items = new HashMap<>();

    public Map<ItemType, Integer> getItems() {
        return items;
    }

    public void addItem(ItemType item, Integer quantity) {
        if (items.containsKey(item)) {
            items.put(item, items.get(item) + quantity);
            System.out.println("Item already in the basket, updated the quantity");
        } else {
            items.put(item, quantity);
            System.out.println("Item added to the basket");
        }
    }

    public boolean containsItem(int itemId){
        for (Map.Entry<ItemType, Integer> entry : items.entrySet()) {
            if(entry.getKey().getItemID() == (itemId)){
                return true;
            }
        }
        return false;
    }

    public void removeItem(int itemId) {
        if(containsItem(itemId)) {
            for (Map.Entry<ItemType, Integer> entry : items.entrySet()) {
                if (entry.getKey().getItemID() == (itemId)){
                    items.remove(entry.getKey());
                    System.out.println("Item removed from the basket");
                    break;
                }
            }
        } else {
            System.out.println("Item does not exist in the basket");
        }
    }

    public int getBasketSize(){
        return items.size();
    }

    public int getItemQuantity(ItemType item) {
        if (items.containsKey(item)) {
            return items.get(item);
        }
        return 0;
    }

    public void increaseByOne(int itemId){
        if(containsItem(itemId)){
            for (Map.Entry<ItemType, Integer> entry : items.entrySet()) {
                if (entry.getKey().getItemID() == (itemId)){
                    entry.setValue(entry.getValue() + 1);
                    System.out.println("Item quantity increase in the basket");
                    break;
                }
            }
        } else {
            System.out.println("Item does not exist in the basket");
        }
    }

    public void decreaseByOne(int itemId){
        if(containsItem(itemId)){
            for (Map.Entry<ItemType, Integer> entry : items.entrySet()) {
                if (entry.getKey().getItemID() == (itemId)){
                    entry.setValue(entry.getValue() - 1);
                    System.out.println("Item quantity decrease in the basket");
                    if (entry.getValue() == 0){
                        items.remove(entry.getKey());
                    }
                    break;
                }
            }
        } else {
            System.out.println("Item does not exist in the basket");
        }
    }
}
