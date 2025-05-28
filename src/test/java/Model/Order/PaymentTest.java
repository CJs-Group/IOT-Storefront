package Model.Order;

import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    @Test
    void testConstructorAndGettersSetters() {
        Date now = new Date();
        Payment payment = new Payment(1, 2, 1000, Payment.PaymentStatus.Completed);
        payment.setPaymentID(10);
        payment.setOrderID(20);
        payment.setCardID(30);
        payment.setAmount(2000);
        payment.setPaymentDate(now);
        payment.setPaymentStatus(Payment.PaymentStatus.Failed);
        PaymentInfo info = new PaymentInfo();
        payment.setPaymentInfo(info);
        assertEquals(10, payment.getPaymentID());
        assertEquals(20, payment.getOrderID());
        assertEquals(30, payment.getCardID());
        assertEquals(2000, payment.getAmount());
        assertEquals(now, payment.getPaymentDate());
        assertEquals(Payment.PaymentStatus.Failed, payment.getPaymentStatus());
        assertEquals(info, payment.getPaymentInfo());
    }

    @Test
    void testEnumFromString() {
        assertEquals(Payment.PaymentStatus.Pending, Payment.PaymentStatus.fromString("Pending"));
        assertEquals(Payment.PaymentStatus.Completed, Payment.PaymentStatus.fromString("Completed"));
        assertThrows(IllegalArgumentException.class, () -> Payment.PaymentStatus.fromString("Unknown"));
    }
}
