package Scripts;

import java.sql.*;
import Model.DAO.*;
import Model.Users.Customer;
import Model.Users.User;
import Model.Users.Staff;

public class SeedDB {

    public static void main(String[] args) throws SQLException {
        try (DBConnector connector = new DBConnector()) {
            Connection conn = connector.openConnection();
            DBManager db = new DBManager(conn);
            
<<<<<<< Updated upstream
            db.createUser(new Customer(1, "John", "password", "john@gmail.com", "1234567890"));
            db.createUser(new Customer(2, "Alice", "passwordAlice", "alice@gmail.com", "0987654321"));
            db.createUser(new Customer(3, "Bob", "passwordBob", "bob@gmail.com", "1122334455"));
            db.createUser(new Staff(4, "Carol", "passwordCarol", "carol@gmail.com", "2233445566", false));
            db.createUser(new Staff(5, "Dave", "passwordDave", "dave@gmail.com", "3344556677", false));
            db.createUser(new Staff(6, "Admin", "adminPassword", "admin@example.com", "4455667788", true));
        }
=======
            db.createUser(new Customer(1, "John", hashPassword("password"), "john@gmail.com", "1234567890"));
            db.createUser(new Customer(2, "Alice", hashPassword("passwordAlice"), "alice@gmail.com", "0987654321"));
            db.createUser(new Customer(3, "Bob", hashPassword("passwordBob"), "bob@gmail.com", "1122334455"));
            db.createUser(new Staff(4, "Carol", hashPassword("passwordCarol"), "carol@gmail.com", "2233445566", false));
            db.createUser(new Staff(5, "Dave", hashPassword("passwordDave"), "dave@gmail.com", "3344556677", false));
            db.createUser(new Staff(6, "Admin", hashPassword("adminPassword"), "admin@example.com", "4455667788", true));
        
            ItemType[] items = {
                new ItemType(0, 1, "Router", "A device that forwards data packets between computer networks", Types.Networking, "images/router.jpg"),
                new ItemType(0, 2, "Switch", "A device that connects devices on a computer network by using packet switching to forward data to its destination", Types.Networking, "images/switch.png"),
                new ItemType(0, 3, "Doorbell", "A device that signals the presence of a visitor at a door", Types.Security, "images/doorbell.png"),
                new ItemType(0, 4, "Security Camera", "A device that records video footage of a specific area", Types.Security, "images/security_camera.png"),
                new ItemType(0, 5, "Smart Light", "A light bulb that can be controlled remotely", Types.Smart_Home, "images/smart_light.png"),
                new ItemType(0, 6, "Smart Thermostat", "A device that controls the temperature of a building", Types.Smart_Home, "images/smart_thermostat.png"),
                new ItemType(0, 7, "Smart Lock", "A lock that can be controlled remotely", Types.Security, "images/smart_lock.png"),
                new ItemType(0, 8, "Smart TV", "A television set that is connected to the internet", Types.Smart_Home, "images/smart_tv.png"),
                new ItemType(0, 9, "Baby Monitor", "A device that allows parents to monitor their baby remotely", Types.Smart_Home, "images/baby_monitor.png"),
                new ItemType(0, 10, "Amazon Echo", "A smart speaker that can control other smart devices", Types.Assistants, "images/amazon_echo.png"),
                new ItemType(0, 11,  "Google Nest", "A smart speaker that can control other smart devices", Types.Assistants, "images/google_nest.png")
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
                    for (ItemType itemType : allItemTypes) {
                        db.addItemToBasket(itemType, customer);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
>>>>>>> Stashed changes
    }

}