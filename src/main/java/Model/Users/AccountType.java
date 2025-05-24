package Model.Users;

public enum AccountType {
    Individual,
    Enterprise;

    public static AccountType fromString(String text) {
        if (text != null) {
            for (AccountType b : AccountType.values()) {
                if (text.equalsIgnoreCase(b.name())) {
                    return b;
                }
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found in AccountType enum");
    }
}
