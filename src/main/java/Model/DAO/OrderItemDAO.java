package Model.DAO;

import Model.Items.Unit;
import Model.Order.OrderItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO {
    private Connection conn;
    private UnitDAO unitDAO;

    public OrderItemDAO(Connection conn, UnitDAO unitDAO) {
        this.conn = conn;
        this.unitDAO = unitDAO;
    }

    private OrderItem resultToOrderItem(ResultSet rs) throws SQLException {
        int orderItemID = rs.getInt("OrderItemID");
        int unitID = rs.getInt("UnitID");
        int quantity = rs.getInt("Quantity");
        int priceAtPurchase = rs.getInt("PriceAtPurchase"); // Price in cents

        Unit unit = unitDAO.getUnitById(unitID);
        if (unit == null) {
            throw new SQLException("Associated Unit with ID " + unitID + " not found for OrderItemID " + orderItemID);
        }
        return new OrderItem(orderItemID, unit, quantity, priceAtPurchase);
    }

    public void createOrderItem(OrderItem orderItem, int orderId) throws SQLException {
        String sql = "INSERT INTO OrderItems (OrderID, UnitID, Quantity, PriceAtPurchase) VALUES (?, ?, ?, ?) RETURNING OrderItemID";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, orderItem.getUnit().getUnitID());
            ps.setInt(3, orderItem.getQuantity()); // Should be 1
            ps.setInt(4, orderItem.getPriceAtPurchase()); // Price in cents

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    orderItem.setOrderItemID(rs.getInt(1));
                }
                else {
                    throw new SQLException("Creating order item failed, no ID obtained.");
                }
            }
        }
    }

    public List<OrderItem> getOrderItemsByOrderId(int orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT * FROM OrderItems WHERE OrderID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(resultToOrderItem(rs));
                }
            }
        }
        return items;
    }

    public void updateOrderItem(OrderItem orderItem) throws SQLException {
        // Generally, order items might not be updated once an order is placed.
        String sql = "UPDATE OrderItems SET UnitID = ?, Quantity = ?, PriceAtPurchase = ? WHERE OrderItemID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderItem.getUnit().getUnitID());
            ps.setInt(2, orderItem.getQuantity());
            ps.setInt(3, orderItem.getPriceAtPurchase());
            ps.setInt(4, orderItem.getOrderItemID());
            ps.executeUpdate();
        }
    }

    public void deleteOrderItem(int orderItemId) throws SQLException {
        String sql = "DELETE FROM OrderItems WHERE OrderItemID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderItemId);
            ps.executeUpdate();
        }
    }

    public void deleteOrderItemsByOrderId(int orderId) throws SQLException {
        String sql = "DELETE FROM OrderItems WHERE OrderID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.executeUpdate();
        }
    }
}