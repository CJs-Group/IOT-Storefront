package Model.DAO;

import Model.Basket.BasketItem;
import Model.Items.ItemType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BasketItemDAO {
    private Connection conn;
    private ItemTypeDAO itemTypeDAO;

    public BasketItemDAO(Connection conn, ItemTypeDAO itemTypeDAO) {
        this.conn = conn;
        this.itemTypeDAO = itemTypeDAO;
    }

    private BasketItem resultToBasketItem(ResultSet rs) throws SQLException {
        int basketItemID = rs.getInt("BasketItemID");
        int basketID = rs.getInt("BasketID");
        int itemTypeID = rs.getInt("ItemTypeID");
        int quantity = rs.getInt("Quantity");
        Integer priceAtPurchase = rs.getObject("PriceAtPurchase", Integer.class);

        ItemType itemType = itemTypeDAO.getItemTypeById(itemTypeID);
        if (itemType == null) {
            throw new SQLException("Associated ItemType with ID " + itemTypeID + " not found for BasketItemID " + basketItemID);
        }
        return new BasketItem(basketItemID, basketID, itemType, quantity, priceAtPurchase);
    }

    public void createBasketItem(BasketItem basketItem) throws SQLException {
        String sql = "INSERT INTO BasketItems (BasketID, ItemTypeID, Quantity, PriceAtPurchase) VALUES (?, ?, ?, ?) RETURNING BasketItemID";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, basketItem.getBasketID());
            ps.setInt(2, basketItem.getItemType().getItemID());
            ps.setInt(3, basketItem.getQuantity());
            if (basketItem.getPriceAtPurchase() != null) {
                ps.setInt(4, basketItem.getPriceAtPurchase());
            } else {
                ps.setNull(4, Types.INTEGER);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    basketItem.setBasketItemID(rs.getInt(1));
                }
                else {
                    throw new SQLException("Creating basket item failed, no ID obtained.");
                }
            }
        }
    }

    public BasketItem getBasketItemById(int basketItemId) throws SQLException {
        String sql = "SELECT * FROM BasketItems WHERE BasketItemID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, basketItemId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return resultToBasketItem(rs);
                }
            }
        }
        return null;
    }
    
    public List<BasketItem> getBasketItemsByBasketId(int basketId) throws SQLException {
        List<BasketItem> items = new ArrayList<>();
        String sql = "SELECT * FROM BasketItems WHERE BasketID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, basketId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(resultToBasketItem(rs));
                }
            }
        }
        return items;
    }

    public void updateBasketItem(BasketItem basketItem) throws SQLException {
        String sql = "UPDATE BasketItems SET ItemTypeID = ?, Quantity = ?, PriceAtPurchase = ? WHERE BasketItemID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, basketItem.getItemType().getItemID());
            ps.setInt(2, basketItem.getQuantity());
            if (basketItem.getPriceAtPurchase() != null) {
                ps.setInt(3, basketItem.getPriceAtPurchase());
            }
            else {
                ps.setNull(3, Types.INTEGER);
            }
            ps.setInt(4, basketItem.getBasketItemID());
            ps.executeUpdate();
        }
    }

    public BasketItem findBasketItemByBasketIdAndItemTypeId(int basketId, int itemTypeId) throws SQLException {
        String sql = "SELECT * FROM BasketItems WHERE BasketID = ? AND ItemTypeID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, basketId);
            ps.setInt(2, itemTypeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return resultToBasketItem(rs);
                }
            }
        }
        return null;
    }

    public void deleteBasketItem(int basketItemId) throws SQLException {
        String sql = "DELETE FROM BasketItems WHERE BasketItemID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, basketItemId);
            ps.executeUpdate();
        }
    }

    public void deleteBasketItemsByBasketId(int basketId) throws SQLException {
        String sql = "DELETE FROM BasketItems WHERE BasketID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, basketId);
            ps.executeUpdate();
        }
    }
}