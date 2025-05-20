package Model.Items;

public enum Status { //Wouldn't be out of stock because it's a singular item
    Restocking,
    In_Stock,
    Reserved,
    Sold,
    Out_for_Delivery,
    Delivered;

    public static Status fromString(String text) {
        if (text != null) {
            for (Status b : Status.values()) {
                if (text.equalsIgnoreCase(b.name())) {
                    return b;
                }
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found in Status enum");
    }
}
