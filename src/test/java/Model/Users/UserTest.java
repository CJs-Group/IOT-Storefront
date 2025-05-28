package Model.Users;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTestImpl extends User {
    public UserTestImpl(int userID, String username, String password, String email, String phoneNumber) {
        super(userID, username, password, email, phoneNumber);
    }
}

class UserTest {
    @Test
    void testConstructorAndGettersSetters() {
        User user = new UserTestImpl(1, "user", "pass", "email", "1234567890");
        assertEquals(1, user.getUserID());
        assertEquals("user", user.getUsername());
        assertEquals("pass", user.getPassword());
        assertEquals("email", user.getEmail());
        assertEquals("1234567890", user.getPhoneNumber());
        user.setUserID(2);
        user.setUsername("newuser");
        user.setPassword("newpass");
        user.setEmail("newemail");
        user.setPhoneNumber("0987654321");
        assertEquals(2, user.getUserID());
        assertEquals("newuser", user.getUsername());
        assertEquals("newpass", user.getPassword());
        assertEquals("newemail", user.getEmail());
        assertEquals("0987654321", user.getPhoneNumber());
    }
}
