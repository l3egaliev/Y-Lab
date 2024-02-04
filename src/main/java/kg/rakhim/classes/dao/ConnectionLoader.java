package kg.rakhim.classes.dao;

import kg.rakhim.classes.dao.migration.LoadProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionLoader {
    private static final LoadProperties properties = new LoadProperties();
    private static Connection connection;
    public static Connection getConnection(){
        try {
            connection = DriverManager.getConnection(properties.getUrl(), properties.getUsername(), properties.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
