package Controller;

import java.sql.*;

import java.util.*;

import java.util.logging.*;

import Model.DAO.*;
import Model.Users.Customer;
import Model.Users.User;

public class TestDB {

    private static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {

        try {

            DBConnector connector = new DBConnector();

            Connection conn = connector.openConnection();

            DBManager db = new DBManager(conn);
            
            Customer user = new Customer(0, "s", "", "SDSD", "0");

            user.setAddress(null);

            db.createUser(user);

            connector.closeConnection();

        } catch (ClassNotFoundException | SQLException ex) {

            Logger.getLogger(TestDB.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

}