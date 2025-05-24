package Model.Users;

public class Staff extends User {
    boolean isAdmin = false;
    StaffRole staffRole;

    public Staff(int userID, String username, String password, String email, String phoneNumber, AccountType accountType, boolean isAdmin, StaffRole staffRole) {
        super(userID, username, password, email, phoneNumber, accountType);
        this.isAdmin = isAdmin;
        this.staffRole = staffRole;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
