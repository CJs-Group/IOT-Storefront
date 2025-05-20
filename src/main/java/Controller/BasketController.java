package Controller;

import java.io.IOException;
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
import Model.Items.ItemType;

@WebServlet("/updateBasket")
public class BasketController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        try (DBConnector dbc = new DBConnector()){
            DBManager dbm = new DBManager(dbc.openConnection());

            HttpSession session = request.getSession();
            String itemIdStr = request.getParameter("itemId");
            String action = request.getParameter("action");
            ItemType itemType = DB.getItemById(itemIdStr);
            Customer customer = new Customer(0, "", "", "", "");

            if (session.getAttribute("user") != null) {
                customer = (Customer)session.getAttribute("user");
            }
            if (itemIdStr != null && action != null) {
                try {
                    switch (action) {
                        case "remove":
                            dbm.removeItemFromBasket(itemType, customer);
                            break;
                        case "+1":
                            dbm.incrementItemQuantity(itemType, customer);
                            break;
                        case "-1":
                            dbm.decrementItemQuantity(itemType, customer);
                            break;
                        case "add":
                            dbm.addItemToBasket(itemType, customer);
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid item ID: " + itemIdStr);
                }
            }
            response.sendRedirect("pdbSystem/basket.jsp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}