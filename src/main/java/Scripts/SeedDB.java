package Scripts;

import java.sql.*;
import Model.DAO.*;
import Model.Users.Customer;
import Model.Users.Staff;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import Model.Items.Status;
import Model.Items.Unit;
import Model.Items.Types;
import Model.Items.ItemType;
import Model.Items.Types;
import Model.Users.AccountType;
import Model.Users.StaffRole;

public class SeedDB {

    private static String hashPassword(String password) {
        return Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }
    public static void main(String[] args) throws SQLException {
        try (DBConnector connector = new DBConnector()) {
            Connection conn = connector.openConnection();
            DBManager db = new DBManager(conn);
            
            db.createUser(new Customer(1, "John", hashPassword("password"), "john@gmail.com", "1234567890", AccountType.Individual));
            db.createUser(new Customer(2, "Alice", hashPassword("passwordAlice"), "alice@gmail.com", "0987654321", AccountType.Individual));
            db.createUser(new Customer(3, "Bob", hashPassword("passwordBob"), "bob@gmail.com", "1122334455", AccountType.Individual));
            db.createUser(new Staff(4, "Carol", hashPassword("passwordCarol"), "carol@gmail.com", "2233445566", false, StaffRole.RetailMember));
            db.createUser(new Staff(5, "Dave", hashPassword("passwordDave"), "dave@gmail.com", "3344556677", false, StaffRole.SalesStaff));
            db.createUser(new Staff(6, "Admin", hashPassword("adminPassword"), "admin@example.com", "4455667788", true, StaffRole.Owner));
            db.createUser(new Staff(7, "Michael", hashPassword("passwordMichael"), "michael@gmail.com", "0411991582", false, StaffRole.StockCoordinator));
            db.createUser(new Customer(8, "Liam", hashPassword("passwordLiam"), "liam@gmail.com", "0919568302", AccountType.Individual));
            db.createUser(new Customer(9, "Phillip", hashPassword("passwordPhillip"), "phillip@gmail.com", "5794832018", AccountType.Individual));
            db.createUser(new Customer(10, "Alex", hashPassword("passwordAlex"), "alex@gmail.com", "5948302198", AccountType.Individual));
            db.createUser(new Customer(11, "Kris", hashPassword("passwordKris"), "kris@gmail.com", "9475694302", AccountType.Individual));
            db.createUser(new Staff(12, "Ben", hashPassword("passwordBen"), "ben@gmail.com", "2783049271", false, StaffRole.RetailMember));
            db.createUser(new Staff(13, "Josh", hashPassword("passwordJosh"), "josh@gmail.com", "7584932098", true, StaffRole.Manager));
            db.createUser(new Staff(14, "Asher", hashPassword("passwordAsher"), "asher@gmail.com", "849360192", false, StaffRole.AssistantManager));
            db.createUser(new Customer(15, "George", hashPassword("passwordGeorge"), "george@gmail.com", "5946309827", AccountType.Individual));
            db.createUser(new Customer(16, "Mark", hashPassword("passwordMark"), "markg@gmail.com", "3984750923", AccountType.Individual));
            db.createUser(new Customer(17, "Ilan", hashPassword("passwordIlan"), "ilan@gmail.com", "759483082", AccountType.Individual));
            db.createUser(new Customer(18, "Deborah", hashPassword("passwordDeborah"), "deborah@gmail.com", "7489384792", AccountType.Individual));
            db.createUser(new Staff(19, "Colin", hashPassword("passwordColin"), "colin@gmail.com", "8495704932", false, StaffRole.SalesStaff));
            db.createUser(new Staff(20, "Jenny", hashPassword("passwordJenny"), "jenny@gmail.com", "4837209873", false, StaffRole.SalesStaff));
        
            ItemType[] items = {
                new ItemType(0, 1, "Router", "A device that forwards data packets between computer networks", Types.Networking, "images/router.jpg"),
                new ItemType(0, 2, "Switch", "A device that connects devices on a computer network by using packet switching to forward data to its destination", Types.Networking, "images/switch.jpg"),
                new ItemType(0, 3, "Doorbell", "A device that signals the presence of a visitor at a door", Types.Security, "images/doorbell.jpg"),
                new ItemType(0, 4, "Security Camera", "A device that records video footage of a specific area", Types.Security, "images/security-camera.jpg"),
                new ItemType(0, 5, "Smart Light", "A light bulb that can be controlled remotely", Types.Smart_Home, "images/smart-light.jpg"),
                new ItemType(0, 6, "Smart Thermostat", "A device that controls the temperature of a building", Types.Smart_Home, "images/smart-thermostat.jpg"),
                new ItemType(0, 7, "Smart Lock", "A lock that can be controlled remotely", Types.Security, "images/smart-lock.jpg"),
                new ItemType(0, 8, "Smart TV", "A television set that is connected to the internet", Types.Smart_Home, "images/smart-tv.jpeg"),
                new ItemType(0, 9, "Baby Monitor", "A device that allows parents to monitor their baby remotely", Types.Smart_Home, "images/baby-monitor.jpg"),
                new ItemType(0, 10, "Amazon Echo", "A smart speaker that can control other smart devices", Types.Assistants, "images/amazon-echo.png"),
                new ItemType(0, 11,  "Google Nest", "A smart speaker that can control other smart devices", Types.Assistants, "images/google-nest.jpg")
            };

            for (int i = 0; i < items.length; i++) {
                ItemType itemType = items[i];
                db.createItemType(itemType);
                for (int j = 0; j < 10; j++) {
                    Unit unit = new Unit(0, itemType, new java.util.Date(), Model.Items.Status.In_Stock); //UnitID auto generated
                    db.createUnit(unit, null);
                }
            }

            List<Customer> customers = db.getCustomers();
            List<ItemType> allItemTypes = db.getAllItemTypes();

            for (Customer customer : customers) {
                Model.Basket.Basket basket = db.getBasketForUser(customer.getUserID());
                if (basket != null) {
                    for (ItemType itemType : allItemTypes) {
                        db.addItemToBasket(basket.getBasketID(), itemType, 1);
                    }
                }
            }
        }
    }
}