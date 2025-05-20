package Model.DAO;

import Model.Order.Order;
import Model.Order.OrderItem;
import Model.Order.OrderStatus;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class OrderDAO {
    private Connection conn;
    private OrderItemDAO orderItemDAO;

    public OrderDAO(Connection conn, OrderItemDAO orderItemDAO) {
        this.conn = conn;
        this.orderItemDAO = orderItemDAO;
    }

    private Order resultToOrder(ResultSet rs) throws SQLException {
        int orderID = rs.getInt("OrderID");
        int userID = rs.getInt("UserID");
        Timestamp orderDateTimestamp = rs.getTimestamp("OrderDate");
        String statusStr = rs.getString("OrderStatus");
        String shippingAddress = rs.getString("ShippingAddress");
        String receipt = rs.getString("Receipt");
        java.sql.Date etaSqlDate = rs.getDate("ETA");

        Date orderDate = (orderDateTimestamp != null) ? new Date(orderDateTimestamp.getTime()) : null;
        OrderStatus orderStatus = OrderStatus.fromString(statusStr);
        Date eta = (etaSqlDate != null) ? new Date(etaSqlDate.getTime()) : null;

        Order order = new Order(orderID, userID, orderDate, orderStatus, shippingAddress, receipt, eta);
        return order;
    }

    public void createOrder(Order order) throws SQLException {
        boolean originalAutoCommit = conn.getAutoCommit();
        try {
            conn.setAutoCommit(false); 

            String sqlOrder = "INSERT INTO Orders (UserID, OrderDate, OrderStatus, ShippingAddress, Receipt, ETA) VALUES (?, ?, ?, ?, ?, ?) RETURNING OrderID";
            try (PreparedStatement psOrder = conn.prepareStatement(sqlOrder)) {
                psOrder.setInt(1, order.getUserID());
                psOrder.setTimestamp(2, (order.getOrderDate() != null) ? new Timestamp(order.getOrderDate().getTime()) : new Timestamp(System.currentTimeMillis()));
                psOrder.setString(3, order.getOrderStatus().name());
                psOrder.setString(4, order.getShippingAddress());
                
                if (order.getReceipt() != null) {
                    psOrder.setString(5, order.getReceipt());
                }
                else {
                    psOrder.setNull(5, Types.VARCHAR);
                }
                
                if (order.getEta() != null) {
                    psOrder.setDate(6, new java.sql.Date(order.getEta().getTime()));
                }
                else {
                    psOrder.setNull(6, Types.DATE);
                }

                try (ResultSet rs = psOrder.executeQuery()) {
                    if (rs.next()) {
                        order.setOrderID(rs.getInt(1));
                    }
                    else {
                        throw new SQLException("Creating order failed, no ID obtained.");
                    }
                }
            }

            if (order.getOrderItems() != null) {
                for (OrderItem item : order.getOrderItems()) {
                    orderItemDAO.createOrderItem(item, order.getOrderID());
                }
            }
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
        finally {
            conn.setAutoCommit(originalAutoCommit);
        }
    }

    public Order getOrderById(int orderId, boolean fetchItems) throws SQLException {
        Order order = null;
        String sql = "SELECT * FROM Orders WHERE OrderID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    order = resultToOrder(rs);
                    if (fetchItems && order != null) {
                        order.setOrderItems(orderItemDAO.getOrderItemsByOrderId(order.getOrderID()));
                    }
                }
            }
        }
        return order;
    }

    public List<Order> getOrdersByUserId(int userId, boolean fetchItems) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders WHERE UserID = ? ORDER BY OrderDate DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = resultToOrder(rs);
                    if (fetchItems) {
                        order.setOrderItems(orderItemDAO.getOrderItemsByOrderId(order.getOrderID()));
                    }
                    orders.add(order);
                }
            }
        }
        return orders;
    }
    
    public List<Order> getAllOrders(boolean fetchItems) throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders ORDER BY OrderDate DESC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Order order = resultToOrder(rs);
                 if (fetchItems) {
                    order.setOrderItems(orderItemDAO.getOrderItemsByOrderId(order.getOrderID()));
                }
                orders.add(order);
            }
        }
        return orders;
    }

    public void updateOrder(Order order) throws SQLException {
        String sql = "UPDATE Orders SET UserID = ?, OrderDate = ?, OrderStatus = ?, ShippingAddress = ?, Receipt = ?, ETA = ? WHERE OrderID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, order.getUserID());
            ps.setTimestamp(2, new Timestamp(order.getOrderDate().getTime()));
            ps.setString(3, order.getOrderStatus().name());
            ps.setString(4, order.getShippingAddress());
            ps.setString(5, order.getReceipt());
            if (order.getEta() != null) {
                ps.setDate(6, new java.sql.Date(order.getEta().getTime()));
            }
            else {
                ps.setNull(6, Types.DATE);
            }
            ps.setInt(7, order.getOrderID());
            ps.executeUpdate();
        }
    }
    
    public void updateOrderStatus(int orderId, OrderStatus newStatus) throws SQLException {
        String sql = "UPDATE Orders SET OrderStatus = ? WHERE OrderID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus.name());
            ps.setInt(2, orderId);
            ps.executeUpdate();
        }
    }

    public void deleteOrder(int orderId) throws SQLException {
        boolean originalAutoCommit = conn.getAutoCommit();
        try {
            conn.setAutoCommit(false); 

            orderItemDAO.deleteOrderItemsByOrderId(orderId);

            String sql = "DELETE FROM Orders WHERE OrderID = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, orderId);
                ps.executeUpdate();
            }
            conn.commit();
        }
        catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(originalAutoCommit);
        }
    }
}