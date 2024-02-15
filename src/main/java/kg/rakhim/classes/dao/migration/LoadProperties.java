package kg.rakhim.classes.dao.migration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс для загрузки настроек из файла application.yml
 */
public class LoadProperties {
    private String url;
    private String username;
    private String password;
    private String liquibasePath;

    /**
     * Конструктор класса, загружает настройки из файла application.yml
     */
    public LoadProperties() {
        Properties properties = new Properties();
        InputStream input;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            input = classLoader.getResourceAsStream("application.yml");
            if (input == null) {
                System.out.println("Не удается найти файл application.yml");
                return;
            }
            properties.load(input);
            url = properties.getProperty("db.url");
            username = properties.getProperty("db.username");
            password = properties.getProperty("db.password");
            liquibasePath = properties.getProperty("liquibase.changelog");
        } catch (IOException e) {
            System.out.println("Exception " + e.getMessage());
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLiquibasePath() {
        return liquibasePath;
    }

    public void setLiquibasePath(String liquibasePath) {
        this.liquibasePath = liquibasePath;
    }
}