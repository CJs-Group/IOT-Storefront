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
        HttpSession session = request.getSession();
        String itemIdStr = request.getParameter("itemId");
        String action = request.getParameter("action");
        if (itemIdStr != null && action != null) {
            try {
                int itemId = Integer.parseInt(itemIdStr);
                Object userIdObj = session.getAttribute("userId");
                if (userIdObj != null) {
                    // User is logged in - use database basket
                    try (DBConnector dbc = new DBConnector()) {
                        DBManager dbm = new DBManager(dbc.openConnection());
                        int userId = (int) userIdObj;
                        int basketID = dbm.getBasketByUserId(userId, false).getBasketID();
                        ItemType item = dbm.getItemTypeById(itemId);
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
                    }
                    catch (SQLException e) {
                        System.out.println("Error updating basket: " + e.getMessage());
                    }
                } else {
                    // User is not logged in - use session basket
                    try (DBConnector dbc = new DBConnector()) {
                        DBManager dbm = new DBManager(dbc.openConnection());
                        ItemType item = dbm.getItemTypeById(itemId);
                        Basket sessionBasket = (Basket) session.getAttribute("sessionBasket");
                        if (sessionBasket == null) {
                            sessionBasket = new Basket(-1);
                            session.setAttribute("sessionBasket", sessionBasket);
                        }
                        switch (action) {
                            case "remove":
                                sessionBasket.getItems().removeIf(basketItem -> 
                                    basketItem.getItemType().getItemID() == itemId);
                                break;
                            case "+1":
                                BasketItem existingItem = sessionBasket.getBasketItemByType(itemId);
                                if (existingItem != null) {
                                    existingItem.setQuantity(existingItem.getQuantity() + 1);
                                }
                                else {
                                    BasketItem newItem = new BasketItem(-1, item, 1, item.getPrice());
                                    sessionBasket.addBasketItem(newItem);
                                }
                                break;
                            case "-1":
                                BasketItem existingItemMinus = sessionBasket.getBasketItemByType(itemId);
                                if (existingItemMinus != null) {
                                    if (existingItemMinus.getQuantity() > 1) {
                                        existingItemMinus.setQuantity(existingItemMinus.getQuantity() - 1);
                                    } else {
                                        sessionBasket.getItems().removeIf(basketItem -> 
                                            basketItem.getItemType().getItemID() == itemId);
                                    }
                                }
                                break;
                        }
                        session.setAttribute("sessionBasket", sessionBasket);
                    } catch (SQLException e) {
                        System.out.println("Error accessing item: " + e.getMessage());
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid item ID: " + itemIdStr);
            }
        }
        
        response.sendRedirect("basket.jsp");
    }
}