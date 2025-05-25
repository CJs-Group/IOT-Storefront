package Controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;

import Model.DAO.DBConnector;
import Model.DAO.DBManager;
import Model.Users.User;
import Model.Basket.Basket;
import Model.Basket.BasketItem;
import Model.Items.ItemType;
import Model.Items.Unit;
import Model.Items.Status;
import Model.Order.Order;
import Model.Order.OrderItem;
import Model.Order.OrderStatus;

@WebServlet("/order")
public class OrderController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Connection conn = null; 

        Integer userIdObj = (Integer) session.getAttribute("userId");
        boolean isGuest = (userIdObj == null);

        try (DBConnector dbc = new DBConnector()) {
            conn = dbc.openConnection(); 
            DBManager dbm = new DBManager(conn);

            User user = null;
            Basket basket = null;

            if (!isGuest) {
                int userId = userIdObj;
                user = dbm.getUserById(userId);
                if (user == null) {
                    response.sendRedirect(request.getContextPath() + "/login.jsp?error=" + URLEncoder.encode("User not found.", "UTF-8"));
                    return;
                }
                basket = dbm.getBasketByUserId(userId, true);
            }
            else {
                basket = (Basket) session.getAttribute("sessionBasket");
            }

            if (basket == null || basket.getItems().isEmpty()) {
                String redirectUrl = isGuest ? "/pdbSystem/basket.jsp" : "/pdbSystem/checkout.jsp";
                response.sendRedirect(request.getContextPath() + redirectUrl + "?error=" + URLEncoder.encode("Your basket is empty.", "UTF-8"));
                return;
            }

            Map<ItemType, List<Unit>> unitsToReserveForOrder = new HashMap<>();
            for (BasketItem basketItem : basket.getItems()) {
                ItemType itemType = basketItem.getItemType();
                int requiredQuantity = basketItem.getQuantity();
                List<Unit> availableUnits = dbm.getUnitsByStatusAndItemID(Status.In_Stock, itemType.getItemID()); 

                if (availableUnits.size() < requiredQuantity) {
                    String errorMessage = "Not enough stock for " + itemType.getName() +
                                          ". Required: " + requiredQuantity +
                                          ", Available: " + availableUnits.size() + ".";
                    String redirectUrl = isGuest ? "/pdbSystem/basket.jsp" : "/pdbSystem/checkout.jsp";
                    response.sendRedirect(request.getContextPath() + redirectUrl + "?error=" + URLEncoder.encode(errorMessage, "UTF-8"));
                    return;
                }
                unitsToReserveForOrder.put(itemType, new ArrayList<>(availableUnits.subList(0, requiredQuantity)));
            }

            List<OrderItem> orderItemsForNewOrder = new ArrayList<>();
            for (BasketItem basketItem : basket.getItems()) {
                ItemType itemType = basketItem.getItemType();
                int requiredUnitsForItemType = basketItem.getQuantity();
                List<Unit> unitsForThisItemType = unitsToReserveForOrder.get(itemType);
                
                for (int i = 0; i < requiredUnitsForItemType; i++) {
                    Unit unitToReserve = unitsForThisItemType.get(i); 
                    
                    unitToReserve.setStatus(Status.Reserved);
                    unitToReserve.setDatePurchased(new Date()); 
                    
                    if (!isGuest) {
                        dbm.updateUnit(unitToReserve, user.getUserID()); 
                    }
                    else {
                        dbm.updateUnit(unitToReserve, -1);
                    }

                    OrderItem orderItem = new OrderItem(0, unitToReserve, 1, itemType.getPrice());
                    orderItemsForNewOrder.add(orderItem);
                }
            }

            String deliveryType = request.getParameter("deliveryType"); 
            String shippingAddress = ""; 

            if (deliveryType.equals("delivery")) {
                shippingAddress = "Address: " + request.getParameter("address") +
                                  ", City: " + request.getParameter("city") +
                                  ", State: " + request.getParameter("state") +
                                  ", Country: " + request.getParameter("country") +
                                  " (For: " + request.getParameter("firstName") + " " + request.getParameter("lastName") + ")";
            }
            else if (deliveryType.equals("click")) { 
                shippingAddress = "Click & Collect at: " + request.getParameter("selectedStore");
            }
            else {
                shippingAddress = "Ship to collection point: " + request.getParameter("collectionPoint");
            }
            
            int orderUserId = isGuest ? -1 : user.getUserID();
            Order newOrder = new Order(orderUserId, new Date(), OrderStatus.Pending, shippingAddress);
            newOrder.setOrderItems(orderItemsForNewOrder);

            if (!isGuest) {
                dbm.createOrder(newOrder); 
                dbm.clearBasket(basket.getBasketID());
                session.setAttribute("latestOrderId", newOrder.getOrderID()); 
            }
            else {
                @SuppressWarnings("unchecked")
                List<Order> guestOrders = (List<Order>) session.getAttribute("guestOrders");
                if (guestOrders == null) {
                    guestOrders = new ArrayList<>();
                }
                
                int guestOrderId = guestOrders.size() + 1;
                newOrder.setOrderID(guestOrderId);
                guestOrders.add(newOrder);
                session.setAttribute("guestOrders", guestOrders);
                session.removeAttribute("sessionBasket");
                session.setAttribute("latestOrderId", guestOrderId);
            }

            response.sendRedirect(request.getContextPath() + "/pdbSystem/receipt.jsp?orderId=" + newOrder.getOrderID() + "&success=" + URLEncoder.encode("Order placed successfully!", "UTF-8"));

        }
        catch (SQLException e) {
            String redirectUrl = isGuest ? "/pdbSystem/basket.jsp" : "/pdbSystem/checkout.jsp";
            response.sendRedirect(request.getContextPath() + redirectUrl + "?error=" + URLEncoder.encode("Database error: " + e.getMessage(), "UTF-8"));
        }
        catch (Exception e) { 
            String redirectUrl = isGuest ? "/pdbSystem/basket.jsp" : "/pdbSystem/checkout.jsp";
            response.sendRedirect(request.getContextPath() + redirectUrl + "?error=" + URLEncoder.encode("An error occurred: " + e.getMessage(), "UTF-8"));
        }
    }
}