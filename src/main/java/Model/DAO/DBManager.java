package Model.DAO;

<<<<<<< Updated upstream
=======
import Model.Basket.Basket;
import Model.Basket.BasketItem;
>>>>>>> Stashed changes
import Model.Items.ItemType;
import Model.Users.Customer;
import Model.Users.Staff;
import Model.Users.User;
import java.sql.*;
<<<<<<< Updated upstream
import java.util.HashMap;
import java.util.Map;
=======
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Model.Items.Status;
>>>>>>> Stashed changes

/* 
* DBManager is the primary DAO class to interact with the database. 
* Complete the existing methods of this classes to perform CRUD operations with the db.
*/

public class DBManager {

    private Connection conn;
<<<<<<< Updated upstream
    
    public DBManager(Connection conn) {       
        this.conn = conn;   
=======
    private UserDAO userDAO;
    private ItemTypeDAO itemTypeDAO;
    private UnitDAO unitDAO;
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;
    private BasketDAO basketDAO;
    private BasketItemDAO basketItemDAO;
    private CardDetailDAO cardDetailDAO;

    public DBManager(Connection conn) {
        this.conn = conn;
        this.userDAO = new UserDAO(conn);
        this.itemTypeDAO = new ItemTypeDAO(conn);
        this.unitDAO = new UnitDAO(conn, itemTypeDAO);
        this.orderItemDAO = new OrderItemDAO(conn, this.unitDAO);
        this.orderDAO = new OrderDAO(conn);
        this.basketItemDAO = new BasketItemDAO(conn, this.itemTypeDAO);
        this.basketDAO = new BasketDAO(conn);
        this.cardDetailDAO = new CardDetailDAO(conn);
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
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
=======
    public void deleteItemType(int itemTypeId) throws SQLException {
        itemTypeDAO.deleteItemType(itemTypeId);
    }

    public void createUnit(Unit unit, Integer userID) throws SQLException {
        unitDAO.createUnit(unit, userID);
    }

    public Unit getUnitById(int unitId) throws SQLException {
        return unitDAO.getUnitById(unitId);
    }

    public List<Unit> getAllUnits() throws SQLException {
        return unitDAO.getAllUnits();
    }

    public List<Unit> getUnitsByItemTypeId(int itemTypeId) throws SQLException {
        return unitDAO.getUnitsByItemTypeId(itemTypeId);
    }

    public List<Unit> getUnitsByStatus(Status status) throws SQLException {
        return unitDAO.getUnitsByStatus(status);
    }

    public List<Unit> getUnitsByUserId(int userId) throws SQLException {
        return unitDAO.getUnitsByUserId(userId);
    }

    public void updateUnit(Unit unit, Integer userID) throws SQLException {
        unitDAO.updateUnit(unit, userID);
    }

    public void deleteUnit(int unitId) throws SQLException {
        unitDAO.deleteUnit(unitId);
    }

    public void createOrder(int orderId, int userId, int itemId, int quantity, double price) throws SQLException {
        orderDAO.createOrder(orderId, userId, itemId, quantity, price);
    }

    public List<Integer> getOrderItemIds(int orderId) throws SQLException {
        return orderDAO.getOrderItemIds(orderId);
    }

    public int getLatestOrderId() throws SQLException {
        return orderDAO.getLatestOrderId();
    }

    public List<Integer> getOrderIdsForUser(int userId) throws SQLException {
        return orderDAO.getOrderIdsForUser(userId);
    }

    public int getOrderItemQuantity(int orderId, int itemId) throws SQLException {
        return orderDAO.getOrderItemQuantity(orderId, itemId);
    }

    public void createOrderItem(OrderItem orderItem, int orderId) throws SQLException {
        orderItemDAO.createOrderItem(orderItem, orderId);
    }

    public List<OrderItem> getOrderItemsByOrderId(int orderId) throws SQLException {
        return orderItemDAO.getOrderItemsByOrderId(orderId);
    }

    public void updateOrderItem(OrderItem orderItem) throws SQLException {
        orderItemDAO.updateOrderItem(orderItem);
    }

    public void deleteOrderItem(int orderItemId) throws SQLException {
        orderItemDAO.deleteOrderItem(orderItemId);
    }

    public Map<Integer, Integer> getBasketItemIds(int userId) throws SQLException {
        return basketDAO.getBasketItemIds(userId);
    }

    public void addItemToBasket(ItemType item, Customer customer) throws SQLException {
        basketDAO.addItemToBasket(item, customer);
    }

    public void removeItemFromBasket(ItemType item, Customer customer) throws SQLException {
        basketDAO.addItemToBasket(item, customer);
    }

    public void incrementItemQuantity(ItemType item, Customer customer) throws SQLException {
        basketDAO.incrementItemQuantity(item, customer);
    }

    public void decrementItemQuantity(ItemType item, Customer customer) throws SQLException {
        basketDAO.decrementItemQuantity(item, customer);
    }

    public void getBasketItemQuantity(ItemType item, Customer customer) throws SQLException {
        basketDAO.getBasketItemQuantity(item, customer);
    }

    public void resetBasket(Customer customer) throws SQLException {
        basketDAO.resetBasket(customer);
    }

    public void createBasketItem(BasketItem basketItem) throws SQLException {
        basketItemDAO.createBasketItem(basketItem);
    }

    public List<BasketItem> getBasketItemsByBasketId(int basketId) throws SQLException {
        return basketItemDAO.getBasketItemsByBasketId(basketId);
    }

    public BasketItem getBasketItemById(int basketItemId) throws SQLException {
        return basketItemDAO.getBasketItemById(basketItemId);
    }

    public void updateBasketItem(BasketItem basketItem) throws SQLException {
        basketItemDAO.updateBasketItem(basketItem);
    }

    public void createCardDetail(PaymentInfo cardDetail, int userId) throws SQLException {
        cardDetailDAO.createCardDetail(cardDetail, userId);
    }

    public PaymentInfo getCardDetailById(int cardId) throws SQLException {
        return cardDetailDAO.getCardDetailById(cardId);
    }

    public List<PaymentInfo> getCardDetailsByUserId(int userId) throws SQLException {
        return cardDetailDAO.getCardDetailsByUserId(userId);
    }

    public void updateCardDetail(PaymentInfo cardDetail) throws SQLException {
        cardDetailDAO.updateCardDetail(cardDetail);
    }

    public void deleteCardDetail(int cardId) throws SQLException {
        cardDetailDAO.deleteCardDetail(cardId);
>>>>>>> Stashed changes
    }
}