package Model.DAO;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;

public class DBConnector extends DB implements AutoCloseable {
    public DBConnector() throws SQLException {
        conn = DriverManager.getConnection(URL + db, dbuser, dbpass);
    }

    public Connection openConnection() {
        return this.conn;
    }

    @Override
    public void close() throws SQLException {
        this.conn.close();
    }
}