package Model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {
    @Test
    void testValidateEmail() {
        assertTrue(Validator.validateEmail("test@example.com"));
        assertFalse(Validator.validateEmail("bademail"));
    }

    @Test
    void testValidatePassword() {
        assertTrue(Validator.validatePassword("abcd"));
        assertFalse(Validator.validatePassword("abc"));
    }

    @Test
    void testValidatePhone() {
        assertTrue(Validator.validatePhone("1234567890"));
        assertFalse(Validator.validatePhone("abc123"));
    }
}
