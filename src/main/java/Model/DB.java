// Database class to store all items and users in the system, 

package Model;
import Model.Items.ItemType;
import Model.Items.Types;
import Model.Items.Unit;
import Model.Users.Customer;
import Model.Users.User;
import Model.Users.Staff;
import java.util.ArrayList;
import java.util.List;

public class DB {
    public static List<ItemType> items = new ArrayList<>();
    public static List<Unit> units = new ArrayList<>();
    public static List<User> users = new ArrayList<>();

    static {
        addItems();
        System.out.println("");
    }

    public static void addItems() { //Temporary to add fake items to the database.
        items.add(new ItemType(1, "Router", "A device that forwards data packets between computer networks", Types.Networking, "images/router.png"));
        items.add(new ItemType(2, "Switch", "A device that connects devices on a computer network by using packet switching to forward data to its destination", Types.Networking, "images/switch.png"));
        items.add(new ItemType(3, "Doorbell", "A device that signals the presence of a visitor at a door", Types.Security, "images/doorbell.png"));
        items.add(new ItemType(4, "Security Camera", "A device that records video footage of a specific area", Types.Security, "images/security_camera.png"));
        items.add(new ItemType(5, "Smart Light", "A light bulb that can be controlled remotely", Types.Smart_Home, "images/smart_light.png"));
        items.add(new ItemType(6, "Smart Thermostat", "A device that controls the temperature of a building", Types.Smart_Home, "images/smart_thermostat.png"));
        items.add(new ItemType(7, "Smart Lock", "A lock that can be controlled remotely", Types.Security, "images/smart_lock.png"));
        items.add(new ItemType(8, "Smart TV", "A television set that is connected to the internet", Types.Smart_Home, "images/smart_tv.png"));
        items.add(new ItemType(9, "Baby Monitor", "A device that allows parents to monitor their baby remotely", Types.Smart_Home, "images/baby_monitor.png"));
        items.add(new ItemType(10, "Amazon Echo", "A smart speaker that can control other smart devices", Types.Assistants, "images/amazon_echo.png"));
        items.add(new ItemType(11, "Google Nest", "A smart speaker that can control other smart devices", Types.Assistants, "images/google_nest.png"));
        int i = 0;
        for (ItemType item : items) {
            for (int j = 0; j < 10; j++) {
                units.add(new Unit(i, item, new java.util.Date(), Model.Items.Status.In_Stock));
                i++;
            }
        }
        users.add(new Customer(1, "John", "password", "john@gmail.com", "1234567890"));
        users.add(new Customer(2, "Alice", "passwordAlice", "alice@gmail.com", "0987654321"));
        users.add(new Customer(3, "Bob", "passwordBob", "bob@gmail.com", "1122334455"));
        users.add(new Staff(4, "Carol", "passwordCarol", "carol@gmail.com", "2233445566", false));
        users.add(new Staff(5, "Dave", "passwordDave", "dave@gmail.com", "3344556677", false));
        users.add(new Staff(6, "Admin", "adminPassword", "admin@example.com", "4455667788", true));
        for (User user : users) {
            if (user instanceof Customer) {
                Customer customer = (Customer) user;
                for (ItemType item : items) {
                    customer.getBasket().addItem(item, 1);
                }
            }
        }
    }

    public static ItemType getItemById(int id) {
        for (ItemType item : items) {
            if (item.getItemID() == id) {
                return item;
            }
        }
        return null;
    }

    public static Unit getUnitById(int id) {
        for (Unit unit : units) {
            if (unit.getUnitID() == id) {
                return unit;
            }
        }
        return null;
    }

    public static User getUserById(int id) {
        for (User user : users) {
            if (user.getUserID() == id) {
                return user;
            }
        }
        return null;
    }
}