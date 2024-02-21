package kg.rakhim.classes.migration;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс для запуска миграций базы данных с использованием Liquibase.
 */
public class LiquibaseStart {

    private static final LoadProperties properties = new LoadProperties();
    private static final String url = properties.getUrl();
    private static final String username = properties.getUsername();
    private static final String password = properties.getPassword();
    private static final String liquibase_changelog = properties.getLiquibasePath();

    /**
     * Метод main для запуска миграций.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(liquibase_changelog, new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Миграции выполнены");
        } catch (SQLException | LiquibaseException e) {
            e.printStackTrace();
        }
    }
}