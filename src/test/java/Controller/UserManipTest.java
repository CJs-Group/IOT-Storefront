package Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static org.mockito.Mockito.*;
import java.io.IOException;
import javax.servlet.ServletException;

class UserManipTest {
    private UserManip controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        controller = new UserManip();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testDoPostAddCustomerMissingFields() throws ServletException, IOException {
        when(request.getParameter("formAction")).thenReturn("addCustomer");
        when(request.getParameter("username")).thenReturn("");
        when(request.getParameter("email")).thenReturn("");
        when(request.getParameter("password")).thenReturn("");
        controller.doPost(request, response);
        verify(session).setAttribute(eq("formError"), contains("Username, Email, and Password are required"));
    }
}
