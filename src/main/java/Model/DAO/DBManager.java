 

package Model.DAO;

import Model.Items.ItemType;
import Model.Items.Unit;
import Model.Users.Customer;
import Model.Users.Staff;
import Model.Users.User;
import java.sql.*;

/* 
* DBManager is the primary DAO class to interact with the database. 
* Complete the existing methods of this classes to perform CRUD operations with the db.
*/

public class DBManager {

    private Connection conn;
    
    public DBManager(Connection conn) throws SQLException {       
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

    public User findUserById(int userId) throws SQLException {
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

    public User findUserByEmail(String email) throws SQLException {
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
}