package Controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

import Model.DB;
import Model.Users.Basket;
import Model.Users.Customer;

@WebServlet("/updateBasket")
public class BasketController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        String itemIdStr = request.getParameter("itemId");
        String action = request.getParameter("action");
        
        if (itemIdStr != null && action != null) {
            try {
                int itemId = Integer.parseInt(itemIdStr);
                int userId = (int)session.getAttribute("userId");
                Customer customer = (Customer)DB.getUserById(userId);
                Basket basket = customer.getBasket();
                
                if (basket != null) {
                    switch (action) {
                        case "remove":
                            basket.removeItem(itemId);
                            break;
                        case "+1":
                            basket.increaseByOne(itemId);
                            break;
                        case "-1":
                            basket.decreaseByOne(itemId);
                            break;
                    }
                    session.setAttribute("basket", basket);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid item ID: " + itemIdStr);
            }
        }
        
        response.sendRedirect("pdbSystem/basket.jsp");
    }
}