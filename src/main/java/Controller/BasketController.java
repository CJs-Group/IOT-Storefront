package Controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

import Model.DAO.DBManager;
import Model.DAO.DBConnector;
import Model.Basket.Basket;
import Model.Basket.BasketItem;
import Model.Items.ItemType;
import Model.Users.Customer;

@WebServlet("/updateBasket")
public class BasketController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (DBConnector dbc = new DBConnector()) {
            HttpSession session = request.getSession();
            DBManager dbm = new DBManager(dbc.openConnection());
            String itemIdStr = request.getParameter("itemId");
            String action = request.getParameter("action");
            if (itemIdStr != null && action != null) {
            try {
                int basketID = dbm.getBasketByUserId((int)session.getAttribute("userId"), false).getBasketID();
                int itemId = Integer.parseInt(itemIdStr);
                ItemType item = dbm.getItemById(itemId);
                int userId = (int)session.getAttribute("userId");
                switch (action) {
                    case "remove":
                        dbm.removeItemTypeFromBasket(basketID, itemId);
                        break;
                    case "+1":
                        dbm.addItemToBasket(basketID, item, 1);
                        break;
                    case "-1":
                        dbm.addItemToBasket(basketID, item, -1);
                        break;
                    }
                    // session.setAttribute("basket", basket);
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid item ID: " + itemIdStr);
                }
                catch (SQLException e) {
                    System.out.println("Error updating basket: " + e.getMessage());
                }
            }
            
            response.sendRedirect("pdbSystem/basket.jsp");
        }
        catch (SQLException e) {
            response.sendRedirect("pdbSystem/basket.jsp?error=" + e.getMessage());
        }
    }
}