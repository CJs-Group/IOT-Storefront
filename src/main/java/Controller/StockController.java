package Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

import Model.DAO.DBManager;
import Model.DAO.DBConnector;
import Model.Items.ItemType;

@WebServlet("/updateStock")
public class StockController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (DBConnector dbc = new DBConnector()) {
            HttpSession session = request.getSession();
            DBManager dbm = new DBManager(dbc.openConnection());
            String itemIdStr = request.getParameter("itemId");
            String action = request.getParameter("action");
            if (itemIdStr != null && action != null) {
                try {
                    int itemId = Integer.parseInt(itemIdStr);
                    ItemType item = dbm.getItemById(itemId);
                    switch (action) {
                        case "remove":
                            dbm.deleteItemType(itemId);
                            break;
                        case "+1":
                            dbm.addItemQuantity(item, 1);
                            break;
                        case "-1":
                            dbm.addItemQuantity(item, -1);
                            break;
                        }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid item ID: " + itemIdStr);
                } catch (SQLException e) {
                    System.out.println("Error updating stock: " + e.getMessage());
                }
            }
            response.sendRedirect("stock.jsp");
        } catch (SQLException e) {
            response.sendRedirect("stock.jsp?error=" + e.getMessage());
        }
    }
}
