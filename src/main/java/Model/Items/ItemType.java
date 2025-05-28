// Itemtype is a representation of each category of item, not the individual units. This is added to the basket.

package Model.Items;

public class ItemType {
    int ItemID;
    // List<Unit> units;
    int price; // divided by 100 to get the actual price
    String name;
    String description;
    Enum<Types> type;
    String imagePath;
    int quantity;

    public ItemType(int ItemID, int price, String name, String description, Enum<Types> type, String imagePath) {
        this.ItemID = ItemID;
        this.price = price;
        this.name = name;
        this.description = description;
        this.type = type;
        this.imagePath = imagePath;
    }

    public ItemType(int ItemID, String name, String description, Enum<Types> type, String imagePath) {
        this.ItemID = ItemID;
        this.price = 0;
        this.name = name;
        this.description = description;
        this.type = type;
        this.imagePath = imagePath;
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
    
    public int getQuantity() {
        return quantity;
    }

    // public List<Unit> getUnits() {
    //     return units;
    // }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setItemID(int itemID) {
        ItemID = itemID;
    }

    // public void setUnits(List<Unit> units) {
    //     this.units = units;
    // }

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

    // Do this with the dbm
    // public void setQuantity(int quantity) {
    //     this.quantity = quantity;
    // }
}