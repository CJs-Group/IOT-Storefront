package Model.Users;

public enum StaffRole {
    Owner,
    Manager,
    AssistantManager,
    StockCoordinator,
    RetailMember,
    SalesStaff;

    public static StaffRole fromString(String text) {
        if (text != null) {
            for (StaffRole b : StaffRole.values()) {
                if (text.equalsIgnoreCase(b.name())) {
                    return b;
                }
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found in StaffRole enum");
    }
}
