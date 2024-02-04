package kg.rakhim.classes.dao.migration;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
@Setter
public class LoadProperties {
    private String url;
    private String username;
    private String password;
    private String liquibasePath;

    public LoadProperties(){
        Properties properties = new Properties();
        InputStream input;
        try{
            ClassLoader classLoader = getClass().getClassLoader();
            input = classLoader.getResourceAsStream("application.properties");
            if (input == null) {
                System.out.println("Не удается найти файл application.properties");
                return;
            }
            properties.load(input);
            url = properties.getProperty("db.url");
            username = properties.getProperty("db.username");
            password = properties.getProperty("db.password");
            liquibasePath = properties.getProperty("liquibase.changelog");
        }catch (IOException e){
            System.out.println("Exception "+e.getMessage());
        }
    }
}
