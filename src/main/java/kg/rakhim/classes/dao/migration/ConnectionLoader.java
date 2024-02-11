package kg.rakhim.classes.dao.migration;

import kg.rakhim.classes.context.ApplicationContext;
import kg.rakhim.classes.dao.migration.LoadProperties;

import javax.swing.text.html.HTMLDocument;
import java.sql.*;

/**
 * Класс для загрузки соединения с базой данных.
 */
public class ConnectionLoader {

    private final LoadProperties properties;

    public ConnectionLoader() {
        properties = (LoadProperties) ApplicationContext.getContext("properties");
    }

    /**
     * Получает соединение с базой данных.
     *
     * @return соединение с базой данных
     */
    public Connection getConnection() throws NullPointerException{
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(properties.getUrl(), properties.getUsername(), properties.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}