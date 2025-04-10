package Controller;

import java.sql.*;

import java.util.logging.*;

import Model.DAO.*;
import Model.Users.Customer;
import Model.Users.Staff;

public class SeedDB {

    public static void main(String[] args) {

        try {

            DBConnector connector = new DBConnector();

            Connection conn = connector.openConnection();

            DBManager db = new DBManager(conn);
            
            db.createUser(new Customer(1, "John", "password", "john@gmail.com", "1234567890"));
            db.createUser(new Customer(2, "Alice", "passwordAlice", "alice@gmail.com", "0987654321"));
            db.createUser(new Customer(3, "Bob", "passwordBob", "bob@gmail.com", "1122334455"));
            db.createUser(new Staff(4, "Carol", "passwordCarol", "carol@gmail.com", "2233445566", false));
            db.createUser(new Staff(5, "Dave", "passwordDave", "dave@gmail.com", "3344556677", false));
            db.createUser(new Staff(6, "Admin", "adminPassword", "admin@example.com", "4455667788", true));

            connector.closeConnection();

        } catch (ClassNotFoundException | SQLException ex) {

            Logger.getLogger(SeedDB.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

}