//I have been moving the DAO code for the different classes into their own files

package Model.DAO;
import Model.Items.ItemType;
import Model.Users.Customer;
import Model.Users.Staff;
import Model.Users.User;
import Model.Order.PaymentInfo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Model.Basket.Basket;

public class UserDAO {

    private Connection conn;
    
    public UserDAO(Connection conn) {       
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
        String sql = """
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
        """;
        
        int newUserId = -1;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhoneNumber());
            
            String userTypeString;
            if (user instanceof Staff) {
                userTypeString = ((Staff)user).isAdmin() ? "Admin" : "Staff";
            } else {
                userTypeString = "Customer";
            }
            ps.setString(5, userTypeString);

            if (user instanceof Customer) {
                ps.setString(6, ((Customer)user).getAddress());
            } else {
                ps.setNull(6, Types.VARCHAR);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    newUserId = rs.getInt("UserID");
                    user.setUserID(newUserId);
                } else {
                    throw new SQLException("User creation failed, no UserID returned.");
                }
            }
        }

        // if (newUserId != -1 && user instanceof Customer) {
        //     ItemTypeDAO itemTypeDAO = new ItemTypeDAO(this.conn); 
        //     BasketItemDAO basketItemDAO = new BasketItemDAO(this.conn, itemTypeDAO);
        //     BasketDAO basketDAO = new BasketDAO(this.conn, basketItemDAO);
        //     Basket newBasket = new Basket(newUserId);
        //     basketDAO.createBasket(newBasket);
        // } else if (newUserId == -1 && user instanceof Customer) {
        //     throw new SQLException("Cannot create basket because user creation failed.");
        // }
    }

    public User getUserById(int userId) throws SQLException {
        String sql = """
            SELECT
                *
            FROM Users
            WHERE UserID = ?
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return resultToUser(rs);
                }
                else {
                    throw new SQLException("User not found with ID: " + userId);
                }
            }
        }
    }

    public User getUserByEmail(String email) throws SQLException {
        String sql = """
            SELECT
                *
            FROM Users
            WHERE Email = ?
        """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return resultToUser(rs);
                }
                else {
                    return null;
                }
            }
        }
    }

    // Was tempting to add restrictions to modifying the user objects, but this should be handled in the Model and just gets in the way here
    public void updateUser(User user) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("""
            UPDATE Users
            SET
                Name = ?,
                Email = ?,
                PasswordHash = ?,
                PhoneNumber = ?,
                ShippingAddress = ?
            WHERE UserID = ?
        """);
        ps.setString(1, user.getUsername());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());
        ps.setString(4, user.getPhoneNumber());
        if (user instanceof Customer) {
            ps.setString(5, ((Customer)user).getAddress());
        }
        ps.setInt(6, user.getUserID());
        ps.executeUpdate();
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            users.add(resultToUser(rs));
        }
        return users;
    }

    public List<Customer> getCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE Type = 'Customer'";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Customer c = new Customer(
                    rs.getInt("UserID"),
                    rs.getString("Name"),
                    rs.getString("PasswordHash"),
                    rs.getString("Email"),
                    rs.getString("PhoneNumber"),
                    rs.getString("ShippingAddress"),
                    new PaymentInfo()
                );
                customers.add(c);
            }
        }
        return customers;
    }

    public List<Staff> getStaff() throws SQLException {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT * FROM Users WHERE Type IN ('Staff','Admin')";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                boolean isAdmin = "Admin".equals(rs.getString("Type"));
                Staff s = new Staff(
                    rs.getInt("UserID"),
                    rs.getString("Name"),
                    rs.getString("PasswordHash"),
                    rs.getString("Email"),
                    rs.getString("PhoneNumber"),
                    isAdmin
                );
                staffList.add(s);
            }
        }
        return staffList;
    }

    public boolean doesEmailExist(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Users WHERE Email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public boolean doesUsernameExist(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Users WHERE Name = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}