package Model.DAO;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;

public class DBConnector extends DB {
    public DBConnector() throws ClassNotFoundException, SQLException {
        conn = DriverManager.getConnection(URL + db, dbuser, dbpass);
    }

    public Connection openConnection() {
        return this.conn;
    }

    public void closeConnection() throws SQLException {
        this.conn.close();
    }
}