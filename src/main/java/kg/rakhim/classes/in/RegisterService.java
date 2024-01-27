package kg.rakhim.classes.in;

import kg.rakhim.classes.database.Storage;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.models.User;

import java.time.LocalDateTime;


public class RegisterService {
    private final Storage storage;


    public RegisterService(Storage storage) {
        this.storage = storage;
    }


    public boolean registerUser(User user) {
        boolean res = true;
        // Проверка - не существует ли такого пользователя
        if (storage.getUsers().contains(user))
            res = false;

        // Добавление аудита
        Audit audit = new Audit(user.getUsername(), "Регистрация", LocalDateTime.now());
        storage.getAudits().add(audit);

        // Сохранение пользователя
        storage.getUsers().add(user);
        return res;
    }


    public boolean loginUser(String username, String password) {
        boolean res = false;
        for (User u : storage.getUsers()) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                res = true;
                break;
            }
        }
        return res;
    }
}
