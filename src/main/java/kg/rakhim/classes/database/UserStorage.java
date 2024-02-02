package kg.rakhim.classes.database;

import kg.rakhim.classes.models.User;
import kg.rakhim.classes.models.UserRole;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserStorage {
    private List<User> users = new ArrayList<>();

    /**
     * Конструктор для создания экземпляра класса {@code UserStorage}.
     * При запуске приложения создается учетная запись для администратора.
     */
    public UserStorage(){
        User admin = new User("admin", "adminpass", UserRole.ADMIN);
        users.add(admin);
    }
}
