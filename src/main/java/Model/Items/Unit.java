//Units are the individual items that are stored in the warehouse which are then allocated to a user when purchased.

package Model.Items;
import java.util.Date;

public class Unit {
    int UnitID;
    ItemType itemType;
    Date dateAdded;
    Status status;
    Date datePurchased;
    Date ETA;

    public Unit(int UnitID, ItemType itemType, Date dateAdded, Status status) {
        this.UnitID = UnitID;
        this.itemType = itemType;
        this.dateAdded = dateAdded;
        this.status = status;
    }

    public Unit(int UnitID, ItemType itemType, Date dateAdded, Status status, Date datePurchased, Date ETA) {
        this.UnitID = UnitID;
        this.itemType = itemType;
        this.dateAdded = dateAdded;
        this.status = status;
        this.datePurchased = datePurchased;
        this.ETA = ETA;
    }

    public int getUnitID() {
        return UnitID;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public Status getStatus() {
        return status;
    }

    public Date getDatePurchased() {
        return datePurchased;
    }

    public Date getETA() {
        return ETA;
    }

    public void setUnitID(int unitID) {
        UnitID = unitID;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDatePurchased(Date datePurchased) {
        this.datePurchased = datePurchased;
    }

    public void setETA(Date ETA) {
        this.ETA = ETA;
    }
}