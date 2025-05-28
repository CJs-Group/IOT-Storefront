package Model.Users;

import Model.Order.PaymentInfo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    @Test
    void testConstructorAndGettersSetters() {
        PaymentInfo paymentInfo = new PaymentInfo();
        Customer customer = new Customer(1, "user", "pass", "email", "1234567890", "address", paymentInfo, AccountType.Individual);
        assertEquals(1, customer.getUserID());
        assertEquals("user", customer.getUsername());
        assertEquals("pass", customer.getPassword());
        assertEquals("email", customer.getEmail());
        assertEquals("1234567890", customer.getPhoneNumber());
        assertEquals("address", customer.getAddress());
        assertEquals(paymentInfo, customer.getPaymentInfo());
        assertEquals(AccountType.Individual, customer.getAccountType());
        customer.setAddress("new address");
        customer.setPaymentInfo(paymentInfo);
        customer.setAccountType(AccountType.Enterprise);
        assertEquals("new address", customer.getAddress());
        assertEquals(AccountType.Enterprise, customer.getAccountType());
    }
}
