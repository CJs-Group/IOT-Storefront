package Model.Items;

public enum Types {
    Networking,
    Security,
    Smart_Home,
    Assistants;

    public static Types fromString(String text) {
        if (text != null) {
            for (Types b : Types.values()) {
                if (text.equalsIgnoreCase(b.name())) {
                    return b;
                }
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found in Types enum");
    }
}
