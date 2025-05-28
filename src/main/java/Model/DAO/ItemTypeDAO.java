package Model.DAO;

import Model.Items.ItemType;
import Model.Items.Types;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemTypeDAO {
    private Connection conn;

    public ItemTypeDAO(Connection conn) {
        this.conn = conn;
    }

    private ItemType resultToItemType(ResultSet rs) throws SQLException {
        return new ItemType(
            rs.getInt("ItemTypeID"),
            rs.getInt("Price"),
            rs.getString("Name"),
            rs.getString("Description"),
            Types.valueOf(rs.getString("Type")),
            rs.getString("ImagePath"),
            rs.getInt("Quantity")
        );
    }

    public ItemType getItemTypeById(int itemTypeId) throws SQLException {
        String sql = "SELECT * FROM ItemTypes WHERE ItemTypeID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itemTypeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return resultToItemType(rs);
                }
            }
        }
        return null;
    }

    public ItemType[] getItemTypesByQuery(String query) throws SQLException {
        ArrayList<ItemType> result = new ArrayList<ItemType>();
        String sql = "SELECT * FROM ItemTypes WHERE Name LIKE ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + query + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    result.add(resultToItemType(rs));
                }
            }
        }
        ItemType[] resultArr = new ItemType[result.size()];
        return result.toArray(resultArr);
    }

    public List<ItemType> getAllItemTypes() throws SQLException {
        List<ItemType> itemTypes = new ArrayList<>();
        String sql = "SELECT * FROM ItemTypes";
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                itemTypes.add(resultToItemType(rs));
            }
        }
        return itemTypes;
    }

    public void createItemType(ItemType itemType) throws SQLException {
        String sql = "INSERT INTO ItemTypes (Name, Description, ImagePath, Type, Price, Quantity) VALUES (?, ?, ?, ?, ?, ?) RETURNING ItemTypeID";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, itemType.getName());
            ps.setString(2, itemType.getDescription());
            ps.setString(3, itemType.getImagePath());
            ps.setString(4, itemType.getType().name());
            ps.setInt(5, itemType.getPrice());
            ps.setInt(6, itemType.getQuantity());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    itemType.setItemID(rs.getInt(1));
                }
            }
        }
    }

    public void updateItemType(ItemType itemType) throws SQLException {
        String sql = "UPDATE ItemTypes SET Name = ?, Description = ?, ImagePath = ?, Type = ?, Price = ?, Quantity = ? WHERE ItemTypeID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, itemType.getName());
            ps.setString(2, itemType.getDescription());
            ps.setString(3, itemType.getImagePath());
            ps.setString(4, itemType.getType().name());
            ps.setInt(5, itemType.getPrice());
            ps.setInt(6, itemType.getQuantity());
            ps.setInt(7, itemType.getItemID());
            ps.executeUpdate();
        }
    }

    public void deleteItemType(int itemTypeId) throws SQLException {
        String sql = "DELETE FROM ItemTypes WHERE ItemTypeID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itemTypeId);
            ps.executeUpdate();
        }
    }

    public void addItemQuantity(ItemType itemType, int newQuantity) throws SQLException {
        int currentQuantity = itemType.getQuantity();
        if ((newQuantity < 0 && currentQuantity > 0) || newQuantity > 0) {
            itemType.setQuantity(currentQuantity + newQuantity);
            this.updateItemType(itemType);
        }
    }
}