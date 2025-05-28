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
import Model.Order.Payment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
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
    private PaymentDAO paymentDAO;
    
    public DBManager(Connection conn) {
        this.conn = conn;
        this.userDAO = new UserDAO(conn);
        this.itemTypeDAO = new ItemTypeDAO(conn);
        this.unitDAO = new UnitDAO(conn, itemTypeDAO);
        this.orderItemDAO = new OrderItemDAO(conn, this.unitDAO);
        this.orderDAO = new OrderDAO(conn, this.orderItemDAO);
        this.basketItemDAO = new BasketItemDAO(conn, this.itemTypeDAO);
        this.basketDAO = new BasketDAO(conn, this.basketItemDAO);
        this.cardDetailDAO = new CardDetailDAO(conn);
        this.paymentDAO = new PaymentDAO(conn, this.cardDetailDAO);
    }

    public void close() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public ItemType getItemTypeById(int id) throws SQLException {
        return itemTypeDAO.getItemTypeById(id);
    }

    public List<ItemType> getItemTypesByQuery(String query) throws SQLException {
        return itemTypeDAO.getItemTypesByQuery(query);
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

    public List<Customer> getCustomers(String nameSearch, String accountTypeSearch) throws SQLException {
        return userDAO.getCustomers(nameSearch, accountTypeSearch);
    }

    public List<Staff> getStaff() throws SQLException {
        return userDAO.getStaff();
    }

    public List<Staff> getStaff(String nameSearch, String staffRoleSearch) throws SQLException {
        return userDAO.getStaff(nameSearch, staffRoleSearch);
    }

    public boolean doesEmailExist(String email) throws SQLException {
        return userDAO.doesEmailExist(email);
    }

    public boolean doesUsernameExist(String username) throws SQLException {
        return userDAO.doesUsernameExist(username);
    }

    public void deleteUser(int userId) throws SQLException {
        userDAO.deleteUser(userId);
    }

    public boolean isUserActivated(int userId) throws SQLException {
        return userDAO.isUserActivated(userId);
    }

    public void setUserActivated(int userId, boolean activated) throws SQLException {
        userDAO.setUserActivated(userId, activated);
    }

    public void updateLastLoginDate(int userId) throws SQLException {
        userDAO.updateLastLoginDate(userId);
    }

    public Timestamp getLastLoginDate(int userId) throws SQLException {
        return userDAO.getLastLoginDate(userId);
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

    public void addItemQuantity(ItemType itemType, int newQuantity) throws SQLException {
        unitDAO.addItemQuantity(itemType, newQuantity);
    }

    public int getItemQuantity(int itemTypeId) throws SQLException {
        return unitDAO.getItemQuantity(itemTypeId);
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

    public List<Unit> getUnitsByStatusAndItemID(Status status, int itemTypeId) throws SQLException {
        return unitDAO.getUnitsByStatusAndItemID(status, itemTypeId);
    }

    public void deleteUnit(int unitId) throws SQLException {
        unitDAO.deleteUnit(unitId);
    }

    public void createOrder(Order order) throws SQLException {
        orderDAO.createOrder(order);
    }

    public Order getOrderById(int orderId, boolean fetchItems) throws SQLException {
        return orderDAO.getOrderById(orderId, fetchItems);
    }

    public List<Order> getOrdersByUserId(int userId, boolean fetchItems) throws SQLException {
        return orderDAO.getOrdersByUserId(userId, fetchItems);
    }

    public List<Order> getAllOrders(boolean fetchItems) throws SQLException {
        return orderDAO.getAllOrders(fetchItems);
    }

    public void updateOrder(Order order) throws SQLException {
        orderDAO.updateOrder(order);
    }

    public void deleteOrder(int orderId) throws SQLException {
        orderDAO.deleteOrder(orderId);
    }

    public void updateOrderStatus(int orderId, OrderStatus newStatus) throws SQLException {
        orderDAO.updateOrderStatus(orderId, newStatus);
    }

    public void cancelOrder(int orderId) throws SQLException {
        orderDAO.cancelOrder(orderId);
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

    public Basket getBasketForUser(int userId) throws SQLException {
        return basketDAO.getBasketForUser(userId);
    }

    public Basket getBasketById(int basketId, boolean fetchItems) throws SQLException {
        return basketDAO.getBasketById(basketId, fetchItems);
    }

    public Basket getBasketByUserId(int userId, boolean fetchItems) throws SQLException {
        return basketDAO.getBasketByUserId(userId, fetchItems);
    }

    public void addItemToBasket(int basketId, ItemType item, int quantity) throws SQLException {
        basketDAO.addItemToBasket(basketId, item, quantity);
    }

    public void removeItemFromBasket(int basketItemId) throws SQLException {
        basketDAO.removeItemFromBasket(basketItemId);
    }

    public void removeItemTypeFromBasket(int basketId, int itemTypeId) throws SQLException {
        basketDAO.removeItemTypeFromBasket(basketId, itemTypeId);
    }

    public void updateBasketItemQuantity(int basketItemId, int newQuantity) throws SQLException {
        basketDAO.updateBasketItemQuantity(basketItemId, newQuantity);
    }
    
    public void clearBasket(int basketId) throws SQLException {
        basketDAO.clearBasket(basketId);
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

    // payment management methods
    public void createPayment(Payment payment) throws SQLException {
        paymentDAO.createPayment(payment);
    }

    public Payment getPaymentById(int paymentId) throws SQLException {
        return paymentDAO.getPaymentById(paymentId);
    }

    public List<Payment> getPaymentsByUserId(int userId) throws SQLException {
        return paymentDAO.getPaymentsByUserId(userId);
    }

    public List<Payment> searchPaymentsByUserIdAndCriteria(int userId, Integer paymentId, Date startDate, Date endDate) throws SQLException {
        return paymentDAO.searchPaymentsByUserIdAndCriteria(userId, paymentId, startDate, endDate);
    }

    public void updatePaymentStatus(int paymentId, Payment.PaymentStatus newStatus) throws SQLException {
        paymentDAO.updatePaymentStatus(paymentId, newStatus);
    }

    public void deletePayment(int paymentId) throws SQLException {
        paymentDAO.deletePayment(paymentId);
    }
}