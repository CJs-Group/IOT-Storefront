package Model.Users;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AccountTypeTest {
    @Test
    void testValues() {
        for (AccountType type : AccountType.values()) {
            assertNotNull(type.name());
        }
    }

    @Test
    void testFromString() {
        assertEquals(AccountType.Individual, AccountType.fromString("Individual"));
        assertEquals(AccountType.Enterprise, AccountType.fromString("Enterprise"));
        assertThrows(IllegalArgumentException.class, () -> AccountType.fromString("Unknown"));
    }
}
