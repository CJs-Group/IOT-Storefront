//I have been moving the DAO code for the different classes into their own files

package Model.DAO;

import Model.Basket.Basket;
import Model.Basket.BasketItem;
import Model.Items.ItemType;
import Model.Items.Unit;
import Model.Users.Customer;
import Model.Users.Staff;
import Model.Users.User;
import Model.Order.Order;
import Model.Order.OrderItem;
import Model.Order.OrderStatus;
import Model.Order.PaymentInfo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Model.Items.Status;

/* 
* DBManager is the primary DAO class to interact with the database. 
* Complete the existing methods of this classes to perform CRUD operations with the db.
*/

public class DBManager {

    private Connection conn;
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
    }

    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public ItemType getItemById(int id) throws SQLException {
        return itemTypeDAO.getItemTypeById(id);
    }

    public void createUser(User user) throws SQLException {
        userDAO.createUser(user);
    }

    public User getUserById(int userId) throws SQLException {
        return userDAO.getUserById(userId);
    }

    public User getUserByEmail(String email) throws SQLException {
        return userDAO.getUserByEmail(email);
    }

    public void updateUser(User user) throws SQLException {
        userDAO.updateUser(user);
    }

    public List<User> getAllUsers() throws SQLException {
        return userDAO.getAllUsers();
    }

    public List<Customer> getCustomers() throws SQLException {
        return userDAO.getCustomers();
    }

    public List<Staff> getStaff() throws SQLException {
        return userDAO.getStaff();
    }

    public boolean doesEmailExist(String email) throws SQLException {
        return userDAO.doesEmailExist(email);
    }

    public boolean doesUsernameExist(String username) throws SQLException {
        return userDAO.doesUsernameExist(username);
    }

    public void createItemType(ItemType itemType) throws SQLException {
        itemTypeDAO.createItemType(itemType);
    }

    public List<ItemType> getAllItemTypes() throws SQLException {
        return itemTypeDAO.getAllItemTypes();
    }

    public void updateItemType(ItemType itemType) throws SQLException {
        itemTypeDAO.updateItemType(itemType);
    }

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
    }
}