package Model.DAO;

import Model.Items.ItemType;
import Model.Users.Customer;
import Model.Users.Staff;
import Model.Users.User;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/* 
* DBManager is the primary DAO class to interact with the database. 
* Complete the existing methods of this classes to perform CRUD operations with the db.
*/

public class DBManager {

    private Connection conn;
    
    public DBManager(Connection conn) {       
        this.conn = conn;   
    }

    public ItemType getItemById(int id) {
        return null;
    }

    private User resultToUser(ResultSet rs) throws SQLException {
        String type  = rs.getString("Type");
        if (type.equals("Customer")) {
            Customer c = new Customer(
                rs.getInt("UserID"),
                rs.getString("Name"),
                rs.getString("PasswordHash"),
                rs.getString("Email"),
                rs.getString("PhoneNumber")
            );
            c.setAddress(rs.getString("ShippingAddress"));
            return c;
        }
        else {
            Staff s = new Staff(
                rs.getInt("UserID"),
                rs.getString("Name"),
                rs.getString("PasswordHash"),
                rs.getString("Email"),
                rs.getString("PhoneNumber"),
                type.equals("Admin")
            );
            return s;
        }
    }

    /**
     * Creates a user in the DB and mutates the ID of the input user to match that of the one in the DB.
     * @param user
     * @throws SQLException
     */
    public void createUser(User user) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("""
            INSERT INTO Users
            (
                Name,
                Email,
                PasswordHash,
                PhoneNumber,
                Type,
                ShippingAddress
            )
            VALUES
            (
                ?,
                ?,
                ?,
                ?,
                ?,
                ?
            )
            RETURNING UserID;
        """);
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());
        ps.setString(4, user.getPhoneNumber());
        ps.setString(5, user instanceof Staff ? ((Staff)user).isAdmin() ? "Admin" : "Staff" : "Customer" );
        if (user instanceof Customer) {
            ps.setString(6, ((Customer)user).getAddress());
        }
        ResultSet rs = ps.executeQuery();
        user.setUserID(rs.getInt("UserID"));
    }

    public User getUserById(int userId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("""
            SELECT
                *
            FROM Users
            WHERE UserID = ?
        """);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        return resultToUser(rs);
    }

    public User getUserByEmail(String email) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("""
            SELECT
                *
            FROM Users
            WHERE Email = ?
        """);
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        return resultToUser(rs);
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

    public int createOrder(int userId, Map<Integer, Integer> itemIdToQuantity, double totalPrice) throws SQLException {
        // Generate a new orderID (could be auto-increment or max+1)
        int orderId = -1;
        PreparedStatement ps1 = conn.prepareStatement("SELECT IFNULL(MAX(orderID), 0) + 1 AS newOrderId FROM Orders");
        ResultSet rs = ps1.executeQuery();
        if (rs.next()) {
            orderId = rs.getInt("newOrderId");
        }
        for (Map.Entry<Integer, Integer> entry : itemIdToQuantity.entrySet()) {
            PreparedStatement ps2 = conn.prepareStatement("""
                INSERT INTO Orders (orderID, userID, itemID, quantity, totalPrice)
                VALUES (?, ?, ?, ?, ?)
            """);
            ps2.setInt(1, orderId);
            ps2.setInt(2, userId);
            ps2.setInt(3, entry.getKey());
            ps2.setInt(4, entry.getValue());
            ps2.setDouble(5, totalPrice);
            ps2.executeUpdate();
        }
        return orderId;
    }

    public ResultSet getOrderById(int orderId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Orders WHERE orderID = ?");
        ps.setInt(1, orderId);
        return ps.executeQuery();
    }

    public int getLatestOrderIdForUser(int userId) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("""
            SELECT orderID FROM Orders o
            JOIN Baskets b ON o.itemID = b.itemID
            WHERE b.userID = ?
            ORDER BY o.orderID DESC LIMIT 1
        """);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("orderID");
        }
        return -1;
    }
}