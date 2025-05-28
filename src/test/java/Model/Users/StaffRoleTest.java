package Model.Users;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StaffRoleTest {
    @Test
    void testFromString() {
        assertEquals(StaffRole.Owner, StaffRole.fromString("Owner"));
        assertEquals(StaffRole.Manager, StaffRole.fromString("Manager"));
        assertEquals(StaffRole.AssistantManager, StaffRole.fromString("AssistantManager"));
        assertEquals(StaffRole.StockCoordinator, StaffRole.fromString("StockCoordinator"));
        assertEquals(StaffRole.RetailMember, StaffRole.fromString("RetailMember"));
        assertEquals(StaffRole.SalesStaff, StaffRole.fromString("SalesStaff"));
        assertThrows(IllegalArgumentException.class, () -> StaffRole.fromString("Unknown"));
    }
}
