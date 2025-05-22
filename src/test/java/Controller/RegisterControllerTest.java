package Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static org.mockito.Mockito.*;
import java.io.IOException;
import javax.servlet.ServletException;

class RegisterControllerTest {
    private RegisterController controller;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        controller = new RegisterController();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    void testDoPostWithNullFields() throws ServletException, IOException {
        when(request.getParameter("username")).thenReturn(null);
        when(request.getParameter("password")).thenReturn(null);
        when(request.getParameter("email")).thenReturn(null);
        controller.doPost(request, response);
        verify(response).sendRedirect(contains("error"));
    }
}
