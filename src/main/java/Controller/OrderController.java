package Controller;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

import Model.DB;
import Model.DAO.DBConnector;
import Model.DAO.DBManager;
import Model.Users.Customer;

@WebServlet("/order")
public class OrderController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("OrderController: doPost");
        double totalPrice = 0.0;
        HttpSession session = request.getSession();
        Customer user = (Customer) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp?error=Please log in to place an order.");
            return;
        }
        try (DBConnector dbc = new DBConnector()) {
            DBManager dbm = new DBManager(dbc.openConnection());
            Map<Integer,Integer> items = dbm.getBasketItemIds(user.getUserID());
            for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
                int itemId = entry.getKey();
                int quantity = entry.getValue();
                int price = DB.getItemById(itemId).getPrice();
                totalPrice += price * quantity;
            }
            if (!items.isEmpty()) {
                System.out.println("Creating order for user: " + user.getUserID());
                dbm.createOrder(user.getUserID(), items, totalPrice / 100.0);
                dbm.resetBasket(user);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        request.getRequestDispatcher("pdbSystem/receipt.jsp").forward(request, response);
    }
}
