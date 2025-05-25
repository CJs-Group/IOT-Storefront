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
            String action = request.getParameter("action");
            if (action == null || action.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "checkout.jsp?error=" + URLEncoder.encode("Invalid action.", "UTF-8"));
                return;
            }
            if (action.equals("Submit Order") || action.equals("Save Order")){
                if (basket == null || basket.getItems().isEmpty()) {
                    String redirectUrl = isGuest ? "basket.jsp" : "checkout.jsp";
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
                    String redirectUrl = isGuest ? "basket.jsp" : "checkout.jsp";
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
            Order newOrder = null;
            if (action.equals("Submit Order")) {
                newOrder = new Order(orderUserId, new Date(), OrderStatus.Completed, shippingAddress);
            } else if (action.equals("Save Order")) {
                if (!isGuest) {
                    newOrder = new Order(user.getUserID(), new Date(), OrderStatus.Saved, shippingAddress);
                } else {
                    response.sendRedirect(request.getContextPath() + "checkout.jsp?error=" + URLEncoder.encode("Couldn't process the order", "UTF-8"));
                }
            } else {
                response.sendRedirect(request.getContextPath() + "checkout.jsp?error=" + URLEncoder.encode("Couldn't process the order", "UTF-8"));
                return;
            }
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

            response.sendRedirect(request.getContextPath() + "receipt.jsp?orderId=" + newOrder.getOrderID() + "&success=" + URLEncoder.encode("Order placed successfully!", "UTF-8"));
            } else if (action.equals("cancelOrder")) {
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                Order order = dbm.getOrderById(orderId, true);
                if (order == null) {
                    response.sendRedirect(request.getContextPath() + "orders.jsp?error=" + URLEncoder.encode("Order not found.", "UTF-8"));
                    return;
                }
                if (order.getOrderStatus() != OrderStatus.Saved) {
                    response.sendRedirect(request.getContextPath() + "orders.jsp?error=" + URLEncoder.encode("Only saved orders can be cancelled.", "UTF-8"));
                    return;
                }
                List<OrderItem> orderItems = order.getOrderItems();
                for (OrderItem orderItem : orderItems) {
                    Unit unit = orderItem.getUnit();
                    unit.setStatus(Status.In_Stock);
                    unit.setDatePurchased(null); 
                    dbm.updateUnit(unit, null); 
                }
                dbm.cancelOrder(orderId);
                response.sendRedirect(request.getContextPath() + "orders.jsp?success=" + URLEncoder.encode("Order cancelled successfully!", "UTF-8"));
            } else if (action.equals("completeOrder")){
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                for (OrderItem orderItem : dbm.getOrderById(orderId, true).getOrderItems()) {
                    Unit unit = orderItem.getUnit();
                    unit.setStatus(Status.Out_for_Delivery);
                    unit.setDatePurchased(new Date()); 
                    dbm.updateUnit(unit, user.getUserID()); 
                }
                dbm.updateOrderStatus(orderId, OrderStatus.Completed);
                response.sendRedirect(request.getContextPath() + "orders.jsp?success=" + URLEncoder.encode("Order completed successfully!", "UTF-8"));
            } else if (action.equals("removeItem")){
                int orderId = Integer.parseInt(request.getParameter("orderId"));
                int unitId = Integer.parseInt(request.getParameter("unitId"));
                Order order = dbm.getOrderById(orderId, true);
                for (OrderItem orderItem : order.getOrderItems()) {
                    if (orderItem.getUnit().getUnitID() == unitId) {
                        dbm.deleteOrderItem(orderItem.getOrderItemID());
                        break;
                    }
                }
                List<OrderItem> orderItems = dbm.getOrderItemsByOrderId(orderId);
                if (orderItems.isEmpty()) {
                    dbm.deleteOrder(orderId); 
                    response.sendRedirect(request.getContextPath() + "orders.jsp?success=" + URLEncoder.encode("Order item removed and order cancelled as it is empty.", "UTF-8"));
                    return;
                }
                Unit unit = dbm.getUnitById(unitId);
                unit.setStatus(Status.In_Stock);
                unit.setDatePurchased(null);
                dbm.updateUnit(unit, null);
                response.sendRedirect(request.getContextPath() + "editOrder.jsp?orderId=" + orderId); 
            } else if (action.equals("Change Delivery Method")){
                int orderId = Integer.parseInt(request.getParameter("orderId"));
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
                Order order = dbm.getOrderById(orderId, true);
                order.setShippingAddress(shippingAddress);
                dbm.updateOrder(order);
                response.sendRedirect(request.getContextPath() + "orders.jsp?success=" + URLEncoder.encode("Delivery method has been updated", "UTF-8"));
            }
        }
        catch (SQLException e) {
            String redirectUrl = isGuest ? "basket.jsp" : "checkout.jsp";
            response.sendRedirect(request.getContextPath() + redirectUrl + "?error=" + URLEncoder.encode("Database error: " + e.getMessage(), "UTF-8"));
        }
        catch (Exception e) { 
            String redirectUrl = isGuest ? "basket.jsp" : "checkout.jsp";
            response.sendRedirect(request.getContextPath() + redirectUrl + "?error=" + URLEncoder.encode("An error occurred: " + e.getMessage(), "UTF-8"));
        }
    }
}