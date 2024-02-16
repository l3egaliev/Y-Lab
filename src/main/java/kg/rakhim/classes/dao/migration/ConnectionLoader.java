package kg.rakhim.classes.dao.migration;

import java.sql.*;

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
    public static Connection getConnection() throws NullPointerException{
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(properties.getUrl(), properties.getUsername(), properties.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}