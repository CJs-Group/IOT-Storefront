package Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static org.mockito.Mockito.*;
import java.io.IOException;
import javax.servlet.ServletException;

class LoginControllerTest {
    private LoginController controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        controller = new LoginController();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testDoPostWithEmptyEmail() throws ServletException, IOException {
        when(request.getParameter("email")).thenReturn("");
        when(request.getParameter("password")).thenReturn("pass");
        controller.doPost(request, response);
        verify(response).sendRedirect(contains("emailError"));
    }

    @Test
    void testDoPostWithEmptyPassword() throws ServletException, IOException {
        when(request.getParameter("email")).thenReturn("user@example.com");
        when(request.getParameter("password")).thenReturn("");
        controller.doPost(request, response);
        verify(response).sendRedirect(contains("passError"));
    }
}
