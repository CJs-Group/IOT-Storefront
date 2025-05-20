package Model.Order;

public enum OrderStatus {
    Pending,
    Processing,
    Shipped,
    Completed,
    Cancelled;

    public static OrderStatus fromString(String text) {
        if (text != null) {
            for (OrderStatus b : OrderStatus.values()) {
                if (text.equalsIgnoreCase(b.name())) {
                    return b;
                }
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found in OrderStatus enum");
    }
}