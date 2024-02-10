package kg.rakhim.classes.dao.migration;

import kg.rakhim.classes.dao.migration.LoadProperties;

import javax.swing.text.html.HTMLDocument;
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
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(properties.getUrl(), properties.getUsername(), properties.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String [] args) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement p = connection.prepareStatement("SELECT * FROM entities.users");
        ResultSet resultSet = p.executeQuery();
        while(resultSet.next()){
            System.out.println(resultSet.getString("username"));
        }
    }
}