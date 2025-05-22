package Controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static org.mockito.Mockito.*;
import java.io.IOException;
import javax.servlet.ServletException;

class OrderControllerTest {
    private OrderController controller;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        controller = new OrderController();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testDoPostWithNoUserId() throws ServletException, IOException {
        when(session.getAttribute("userId")).thenReturn(null);
        controller.doPost(request, response);
        verify(response).sendRedirect(contains("login.jsp"));
    }
}
