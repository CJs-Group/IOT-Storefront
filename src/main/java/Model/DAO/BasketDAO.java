package Model.DAO;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import Model.Items.ItemType;
import Model.Users.Customer;

public class BasketDAO {
    private Connection conn;

    public BasketDAO(Connection conn) {
        this.conn = conn;
    }

    public Map<Integer, Integer> getBasketItemIds(int userId) throws SQLException {
        System.out.println("Getting basket items for user: " + userId);
        PreparedStatement ps = conn.prepareStatement("""
                    SELECT
                        ItemID,
                        quantity
                    FROM Baskets
                    WHERE UserID = ?
                """);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        Map<Integer, Integer> basketItems = new HashMap<>();
        while (rs.next()) {
            basketItems.put(rs.getInt("ItemID"), rs.getInt("quantity"));
        }
        return basketItems;
    }

    public void addItemToBasket(ItemType item, Customer customer) throws SQLException {
        if (getBasketItemQuantity(item, customer) > 0) {
            incrementItemQuantity(item, customer);
            return;
        }
        PreparedStatement ps = conn.prepareStatement("""
                    INSERT INTO Baskets
                    (
                        UserID,
                        ItemID,
                        quantity
                    )
                    VALUES
                    (
                        ?,
                        ?,
                        1
                    )
                """);
        ps.setInt(1, customer.getUserID());
        ps.setInt(2, item.getItemID());
        ps.executeUpdate();
    }

    public void removeItemFromBasket(ItemType item, Customer customer) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("""
                    DELETE FROM Baskets
                    WHERE UserID = ?
                    AND ItemID = ?
                """);
        ps.setInt(1, customer.getUserID());
        ps.setInt(2, item.getItemID());
        ps.executeUpdate();
    }

    public void incrementItemQuantity(ItemType item, Customer customer) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("""
                    UPDATE Baskets
                    SET quantity = quantity + 1
                    WHERE UserID = ?
                    AND ItemID = ?
                """);
        ps.setInt(1, customer.getUserID());
        ps.setInt(2, item.getItemID());
        ps.executeUpdate();
    }

    public void decrementItemQuantity(ItemType item, Customer customer) throws SQLException {
        getBasketItemQuantity(item, customer);
        if (getBasketItemQuantity(item, customer) <= 1) {
            removeItemFromBasket(item, customer);
            return;
        }
        PreparedStatement ps = conn.prepareStatement("""
                    UPDATE Baskets
                    SET quantity = quantity - 1
                    WHERE UserID = ?
                    AND ItemID = ?
                """);
        ps.setInt(1, customer.getUserID());
        ps.setInt(2, item.getItemID());
        ps.executeUpdate();
    }

    public int getBasketItemQuantity(ItemType item, Customer customer) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("""
                    SELECT quantity
                    FROM Baskets
                    WHERE UserID = ?
                    AND ItemID = ?
                """);
        ps.setInt(1, customer.getUserID());
        ps.setInt(2, item.getItemID());
        ResultSet rs = ps.executeQuery();
        return rs.getInt("quantity");
    }

    public void resetBasket(Customer customer) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("""
                    DELETE FROM Baskets
                    WHERE UserID = ?
                """);
        ps.setInt(1, customer.getUserID());
        ps.executeUpdate();
    }
}