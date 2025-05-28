package Model.DAO;

import Model.Basket.Basket;
import Model.Basket.BasketItem;
import java.sql.*;
import java.util.List;
import Model.Items.ItemType;

public class BasketDAO {
    private Connection conn;
    private BasketItemDAO basketItemDAO;

    public BasketDAO(Connection conn, BasketItemDAO basketItemDAO) {
        this.conn = conn;
        this.basketItemDAO = basketItemDAO;
    }

    private Basket resultToBasket(ResultSet rs) throws SQLException {
        int basketID = rs.getInt("BasketID");
        int userID = rs.getInt("UserID");
        Basket basket = new Basket(basketID, userID);
        return basket;
    }

    public Basket createBasket(Basket basket) throws SQLException {
        Basket existingBasket = getBasketByUserId(basket.getUserID(), false);
        if (existingBasket != null) {
            return existingBasket;
        }

        String sql = "INSERT INTO Baskets (UserID) VALUES (?) RETURNING BasketID";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, basket.getUserID());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    basket.setBasketID(rs.getInt(1));
                    return basket;
                }
                else {
                    throw new SQLException("Creating basket failed, no ID obtained.");
                }
            }
        }
    }
    
    public Basket getBasketForUser(int userId) throws SQLException {
        Basket basket = getBasketByUserId(userId, true);
        if (basket == null) {
            Basket newBasket = new Basket(userId);
            return createBasket(newBasket);
        }
        return basket;
    }


    public Basket getBasketById(int basketId, boolean fetchItems) throws SQLException {
        Basket basket = null;
        String sql = "SELECT * FROM Baskets WHERE BasketID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, basketId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    basket = resultToBasket(rs);
                    if (fetchItems && basket != null) {
                        basket.setItems(basketItemDAO.getBasketItemsByBasketId(basket.getBasketID()));
                    }
                }
            }
        }
        return basket;
    }

    public Basket getBasketByUserId(int userId, boolean fetchItems) throws SQLException {
        Basket basket = null;
        String sql = "SELECT * FROM Baskets WHERE UserID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    basket = resultToBasket(rs);
                    if (fetchItems && basket != null) {
                        basket.setItems(basketItemDAO.getBasketItemsByBasketId(basket.getBasketID()));
                    }
                }
            }
        }
        return basket;
    }

    public void addItemToBasket(int basketId, ItemType itemType, int quantityToChange) throws SQLException {
        if (itemType == null) {
            throw new SQLException("ItemType cannot be null when modifying basket item quantity.");
        }

        BasketItem existingItem = basketItemDAO.findBasketItemByBasketIdAndItemTypeId(basketId, itemType.getItemID());

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + quantityToChange;
            if (newQuantity > 0) {
                existingItem.setQuantity(newQuantity);
                basketItemDAO.updateBasketItem(existingItem);
            } else {
                basketItemDAO.deleteBasketItem(existingItem.getBasketItemID());
            }
        }
        else {
            if (quantityToChange > 0) {
                BasketItem newItem = new BasketItem(0, basketId, itemType, quantityToChange, itemType.getPrice());
                basketItemDAO.createBasketItem(newItem);
            }
        }
    }
    
    public void updateBasketItemQuantity(int basketItemId, int newQuantity) throws SQLException {
        BasketItem item = basketItemDAO.getBasketItemById(basketItemId);
        if (item != null) {
            if (newQuantity <= 0) {
                basketItemDAO.deleteBasketItem(basketItemId);
            } else {
                item.setQuantity(newQuantity);
                basketItemDAO.updateBasketItem(item);
            }
        }
        else {
            throw new SQLException("BasketItem with ID " + basketItemId + " not found for update.");
        }
    }

    public void removeItemFromBasket(int basketItemId) throws SQLException {
        basketItemDAO.deleteBasketItem(basketItemId);
    }

    public void removeItemTypeFromBasket(int basketId, int itemTypeId) throws SQLException {
        BasketItem itemToRemove = basketItemDAO.findBasketItemByBasketIdAndItemTypeId(basketId, itemTypeId);
        if (itemToRemove != null) {
            basketItemDAO.deleteBasketItem(itemToRemove.getBasketItemID());
        }
    }

    public void clearBasket(int basketId) throws SQLException {
        basketItemDAO.deleteBasketItemsByBasketId(basketId);
    }
}