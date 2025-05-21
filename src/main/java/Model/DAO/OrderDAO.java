package Model.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.Order.*;
import Model.Items.ItemType;

public class OrderDAO {
    private Connection conn;

    public OrderDAO(Connection conn) {
        this.conn = conn;
    }

    public int createOrder(int orderId, int userId, int itemId, int quantity, double price) throws SQLException {
        // Generate a new orderID (could be auto-increment or max+1)

        PreparedStatement ps = conn.prepareStatement("""
                    INSERT INTO Orders (orderID, userID, itemID, quantity, price)
                    VALUES (?, ?, ?, ?, ?)
                """);
        ps.setInt(1, orderId);
        ps.setInt(2, userId);
        ps.setInt(3, itemId);
        ps.setInt(4, quantity);
        ps.setDouble(5, price);
        ps.executeUpdate();

        return orderId;
    }

    public List<Integer> getOrderItemIds(int orderId) throws SQLException {
        List<Integer> itemIds = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("SELECT itemId FROM Orders WHERE orderID = ?");
        ps.setInt(1, orderId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            itemIds.add(rs.getInt("itemId"));
        }
        return itemIds;
    }

    public int getLatestOrderId() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("""
                    SELECT o.orderID
                    FROM Orders o
                    JOIN Baskets b ON o.itemID = b.itemID
                    ORDER BY o.orderID DESC
                    LIMIT 1;
                """);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("orderID");
        }
        return -1;
    }

    public List<Integer> getOrderIdsForUser(int userId) throws SQLException {
        List<Integer> orderIds = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT orderId FROM Orders where userID = ?");
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            orderIds.add(rs.getInt("orderId"));
        }
        return orderIds;
    }

    public int getOrderItemQuantity(int orderId, int itemId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT quantity FROM Orders WHERE orderID = ? AND itemID = ?");
        ps.setInt(1, orderId);
        ps.setInt(2, itemId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("quantity");
        }
        return -1;
    }
}