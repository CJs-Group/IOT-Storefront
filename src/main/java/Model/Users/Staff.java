package Model.Users;

public class Staff extends User {
    boolean isAdmin = false;

    public Staff(int userID, String username, String password, String email, String phoneNumber, boolean isAdmin) {
        super(userID, username, password, email, phoneNumber);
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
