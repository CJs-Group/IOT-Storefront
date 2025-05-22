package Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static org.mockito.Mockito.*;
import java.io.IOException;
import javax.servlet.ServletException;

class UpdatePaymentControllerTest {
    private UpdatePaymentController controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        controller = new UpdatePaymentController();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testDoPostWithMissingFields() throws ServletException, IOException {
        when(request.getParameter("creditCardNumber")).thenReturn("");
        when(request.getParameter("fullName")).thenReturn("");
        when(request.getParameter("expiryDate")).thenReturn("");
        when(request.getParameter("cvv")).thenReturn("");
        controller.doPost(request, response);
        verify(session).setAttribute(eq("paymentErr"), contains("All fields are required"));
    }
}
