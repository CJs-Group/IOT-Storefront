package uts.isd.model;

import java.util.HashMap;
import java.util.Map;

public class Basket {
    private Map<Item, Integer> items = new HashMap<>();

    public Map<Item, Integer> getItems() {
        return items;
    }

    public void addItem(Item item, Integer quantity) {
        if (items.containsKey(item)) {
            items.put(item, items.get(item) + quantity);
            System.out.println("Item already in the basket, updated the quantity");
        } else {
            items.put(item, quantity);
            System.out.println("Item added to the basket");
        }
    }

    public boolean containsItem(String itemId){
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            if(entry.getKey().getItemId().equals(itemId)){
                return true;
            }
        }
        return false;
    }

    public void removeItem(String itemId) {
        if(containsItem(itemId)) {
            for (Map.Entry<Item, Integer> entry : items.entrySet()) {
                if (entry.getKey().getItemId().equals(itemId)){
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

    public int getItemQuantity(Item item) {
        if (items.containsKey(item)) {
            return items.get(item);
        }
        return 0;
    }
}
