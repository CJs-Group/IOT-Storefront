package Model.Order;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaymentInfoTest {
    @Test
    void testConstructorAndGettersSetters() {
        PaymentInfo info = new PaymentInfo(1, "1234567890123456", 123, "12/30", "John Doe");
        assertEquals(1, info.getPaymentId());
        assertEquals("1234567890123456", info.getCardNo());
        assertEquals(123, info.getCCV());
        assertEquals("12/30", info.getExpiryDate());
        assertEquals("John Doe", info.getCardHolderName());
        info.setPaymentId(2);
        info.setCardNo("6543210987654321");
        info.setCCV(321);
        info.setExpiryDate("11/29");
        info.setCardHolderName("Jane Smith");
        assertEquals(2, info.getPaymentId());
        assertEquals("6543210987654321", info.getCardNo());
        assertEquals(321, info.getCCV());
        assertEquals("11/29", info.getExpiryDate());
        assertEquals("Jane Smith", info.getCardHolderName());
    }

    @Test
    void testValidateValid() {
        PaymentInfo info = new PaymentInfo(1, "1234567890123456", 123, "12/30", "John Doe");
        assertTrue(info.validate());
    }

    @Test
    void testValidateInvalidCard() {
        PaymentInfo info = new PaymentInfo(1, "1234", 123, "12/30", "John Doe");
        assertFalse(info.validate());
    }

    @Test
    void testValidateInvalidCCV() {
        PaymentInfo info = new PaymentInfo(1, "1234567890123456", 12, "12/30", "John Doe");
        assertFalse(info.validate());
    }

    @Test
    void testValidateInvalidExpiry() {
        PaymentInfo info = new PaymentInfo(1, "1234567890123456", 123, "01/20", "John Doe");
        assertFalse(info.validate());
    }

    @Test
    void testValidateInvalidExpiryFormat() {
        PaymentInfo info = new PaymentInfo(1, "1234567890123456", 123, "bad", "John Doe");
        assertFalse(info.validate());
    }
}
