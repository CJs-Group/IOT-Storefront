package Model.DAO;

import Model.Items.ItemType;
import Model.Items.Unit;
import Model.Items.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class UnitDAO {
    private Connection conn;
    private ItemTypeDAO itemTypeDAO;

    public UnitDAO(Connection conn, ItemTypeDAO itemTypeDAO) {
        this.conn = conn;
        this.itemTypeDAO = itemTypeDAO;
    }

    private Unit resultToUnit(ResultSet rs) throws SQLException {
        int unitID = rs.getInt("UnitID");
        int itemTypeID = rs.getInt("ItemTypeID");
        Timestamp dateAddedTimestamp = rs.getTimestamp("DateAdded");
        String statusStr = rs.getString("Status");
        Timestamp datePurchasedTimestamp = rs.getTimestamp("DatePurchased");
        Timestamp etaTimestamp = rs.getTimestamp("ETA");

        ItemType itemType = itemTypeDAO.getItemTypeById(itemTypeID);
        if (itemType == null) {
            throw new SQLException("Failed to find ItemType with ID: " + itemTypeID + " for UnitID: " + unitID);
        }

        Date dateAdded = (dateAddedTimestamp != null) ? new Date(dateAddedTimestamp.getTime()) : null;
        Status status = Status.valueOf(statusStr);
        Date datePurchased = (datePurchasedTimestamp != null) ? new Date(datePurchasedTimestamp.getTime()) : null;
        Date eta = (etaTimestamp != null) ? new Date(etaTimestamp.getTime()) : null;

        Unit unit = new Unit(unitID, itemType, dateAdded, status);
        unit.setDatePurchased(datePurchased);
        unit.setETA(eta);
        return unit;
    }

    public void createUnit(Unit unit, Integer userID) throws SQLException {
        String sql = "INSERT INTO Units (ItemTypeID, DateAdded, Status, DatePurchased, UserID, ETA) VALUES (?, ?, ?, ?, ?, ?) RETURNING UnitID";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, unit.getItemType().getItemID());
            ps.setTimestamp(2, (unit.getDateAdded() != null) ? new Timestamp(unit.getDateAdded().getTime()) : new Timestamp(System.currentTimeMillis()));
            ps.setString(3, unit.getStatus().name());

            if (unit.getDatePurchased() != null) {
                ps.setTimestamp(4, new Timestamp(unit.getDatePurchased().getTime()));
            } else {
                ps.setNull(4, Types.TIMESTAMP);
            }

            if (userID != null) {
                ps.setInt(5, userID);
            } else {
                ps.setNull(5, Types.INTEGER);
            }

            if (unit.getETA() != null) {
                ps.setTimestamp(6, new Timestamp(unit.getETA().getTime()));
            } else {
                ps.setNull(6, Types.TIMESTAMP);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    unit.setUnitID(rs.getInt(1));
                } else {
                    throw new SQLException("Creating unit failed, no ID obtained.");
                }
            }
        }
    }

    public Unit getUnitById(int unitId) throws SQLException {
        String sql = "SELECT * FROM Units WHERE UnitID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, unitId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return resultToUnit(rs);
                }
            }
        }
        return null;
    }

    public List<Unit> getAllUnits() throws SQLException {
        List<Unit> units = new ArrayList<>();
        String sql = "SELECT * FROM Units";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                units.add(resultToUnit(rs));
            }
        }
        return units;
    }

    public List<Unit> getUnitsByItemTypeId(int itemTypeId) throws SQLException {
        List<Unit> units = new ArrayList<>();
        String sql = "SELECT * FROM Units WHERE ItemTypeID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itemTypeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    units.add(resultToUnit(rs));
                }
            }
        }
        return units;
    }
    
    public List<Unit> getUnitsByStatus(Status status) throws SQLException {
        List<Unit> units = new ArrayList<>();
        String sql = "SELECT * FROM Units WHERE Status = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    units.add(resultToUnit(rs));
                }
            }
        }
        return units;
    }

    public List<Unit> getUnitsByUserId(int userId) throws SQLException {
        List<Unit> units = new ArrayList<>();
        String sql = "SELECT * FROM Units WHERE UserID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    units.add(resultToUnit(rs));
                }
            }
        }
        return units;
    }


    public void updateUnit(Unit unit, Integer userID) throws SQLException {
        String sql = "UPDATE Units SET ItemTypeID = ?, DateAdded = ?, Status = ?, DatePurchased = ?, UserID = ?, ETA = ? WHERE UnitID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, unit.getItemType().getItemID());
            ps.setTimestamp(2, (unit.getDateAdded() != null) ? new Timestamp(unit.getDateAdded().getTime()) : null);
            ps.setString(3, unit.getStatus().name());

            if (unit.getDatePurchased() != null) {
                ps.setTimestamp(4, new Timestamp(unit.getDatePurchased().getTime()));
            }
            else {
                ps.setNull(4, Types.TIMESTAMP);
            }

            if (userID != null) {
                ps.setInt(5, userID);
            }
            else {
                ps.setNull(5, Types.INTEGER);
            }

            if (unit.getETA() != null) {
                ps.setTimestamp(6, new Timestamp(unit.getETA().getTime()));
            }
            else {
                ps.setNull(6, Types.TIMESTAMP);
            }
            ps.setInt(7, unit.getUnitID());
            ps.executeUpdate();
        }
    }

    public void deleteUnit(int unitId) throws SQLException {
        String sql = "DELETE FROM Units WHERE UnitID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, unitId);
            ps.executeUpdate();
        }
    }
}