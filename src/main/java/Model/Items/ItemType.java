// Itemtype is a representation of each category of item, not the individual units. This is added to the basket.

package Model.Items;

import java.util.List;
public class ItemType {
    int ItemID;
    List<Unit> units;
    double price; 
    String name;
    String description;
    Enum<Types> type;
    String imagePath;

    public ItemType(int ItemID, List<Unit> units, String name, String description, Enum<Types> type, String imagePath, double price) {
        this.ItemID = ItemID;
        this.units = units;
        this.name = name;
        this.description = description;
        this.type = type;
        this.imagePath = imagePath;
        this.price = price;
    }

    public ItemType (int ItemID, String name, String description, Enum<Types> type, String imagePath, double price) {
        this.ItemID = ItemID;
        this.name = name;
        this.description = description;
        this.type = type;
        this.imagePath = imagePath;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Enum<Types> getType() {
        return type;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getItemID() {
        return ItemID;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(Enum<Types> type) {
        this.type = type;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}