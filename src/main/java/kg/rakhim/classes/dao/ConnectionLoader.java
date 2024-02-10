package kg.rakhim.classes.dao;

import kg.rakhim.classes.dao.migration.LoadProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс для загрузки соединения с базой данных.
 */
public class ConnectionLoader {

    private static final LoadProperties properties = new LoadProperties();

    /**
     * Получает соединение с базой данных.
     *
     * @return соединение с базой данных
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(properties.getUrl(), properties.getUsername(), properties.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}