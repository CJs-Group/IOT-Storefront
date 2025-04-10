package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Scanner;
import java.util.logging.*;
import java.util.stream.Stream;

import Model.DAO.*;
import Model.Users.Customer;
import Model.Users.Staff;

public class SetupDB {

    public static void main(String[] args) throws Exception {
        Path[] paths = Files.walk(Paths.get("migrations/")).toArray(Path[]::new);

        DBConnector connector = new DBConnector();

        Connection conn = connector.openConnection();

        for (Path path : paths) {
            File file = new File(path.toString());
            if (!file.isFile()) {
                continue;
            }
            FileInputStream inputStream = new FileInputStream(path.toAbsolutePath().toString());
            Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name());
            String content = scanner.useDelimiter("\\A").next();
            Statement st = conn.createStatement();
            System.out.println(content);
            int i = st.executeUpdate(content);
            if (i == 0) {
                System.out.println("Migration Successful: " + file.getName());
            }
            else {
                System.out.println("Migration Unsuccessful: " + file.getName());
            }
            scanner.close();
        }

        connector.closeConnection();

    }

}