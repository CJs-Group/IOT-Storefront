package Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static org.mockito.Mockito.*;
import java.io.IOException;
import javax.servlet.ServletException;

class BasketControllerTest {
    private BasketController controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        controller = new BasketController();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testDoPostWithNullParams() throws ServletException, IOException {
        when(request.getParameter("itemId")).thenReturn(null);
        when(request.getParameter("action")).thenReturn(null);
        controller.doPost(request, response);
        // No exception means pass
    }
}
