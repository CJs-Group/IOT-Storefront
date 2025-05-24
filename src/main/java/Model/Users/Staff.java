package Model.Users;

public class Staff extends User {
    boolean isAdmin = false;
    StaffRole staffRole;

    public Staff(int userID, String username, String password, String email, String phoneNumber, boolean isAdmin, StaffRole staffRole) {
        super(userID, username, password, email, phoneNumber);
        this.isAdmin = isAdmin;
        this.staffRole = staffRole;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public StaffRole getStaffRole() {
        return staffRole;
    }


    public void setStaffRole(StaffRole staffRole) {
        this.staffRole = staffRole;
    }
}
