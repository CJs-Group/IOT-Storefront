package Model.Users;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StaffTest {
    @Test
    void testConstructorAndGettersSetters() {
        Staff staff = new Staff(1, "staff", "pass", "email", "1234567890", true, StaffRole.Owner);
        assertEquals(1, staff.getUserID());
        assertEquals("staff", staff.getUsername());
        assertEquals("pass", staff.getPassword());
        assertEquals("email", staff.getEmail());
        assertEquals("1234567890", staff.getPhoneNumber());
        assertEquals(true, staff.isAdmin());
        assertEquals(StaffRole.Owner, staff.getStaffRole());
        staff.setStaffRole(StaffRole.Manager);
        assertEquals(StaffRole.Manager, staff.getStaffRole());
    }
}
