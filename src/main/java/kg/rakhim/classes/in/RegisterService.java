/**
 * Класс {@code RegisterService} предоставляет сервис для регистрации и авторизации пользователей.
 * <p>
 * Зависит от класса {@link Storage} для доступа и манипуляции данными о пользователях и аудитах.
 * </p>
 * <p>
 * Реализует методы для регистрации нового пользователя и проверки аутентификации.
 * </p>
 *
 * @author Рахим Нуралиев
 * @version 1.0
 * @since 2024-01-26
 * @see Storage
 * @see Audit
 * @see User
 */
package kg.rakhim.classes.in;

import kg.rakhim.classes.database.Storage;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.models.User;

import java.time.LocalDateTime;

/**
 * Класс {@code RegisterService} предоставляет сервис для регистрации и авторизации пользователей.
 */
public class RegisterService {
    private final Storage storage;

    /**
     * Конструктор для создания экземпляра класса {@code RegisterService}.
     *
     * @param storage объект Storage для доступа и манипуляции данными о пользователях и аудитах
     */
    public RegisterService(Storage storage) {
        this.storage = storage;
    }

    /**
     * Регистрация нового пользователя.
     *
     * @param user объект User для регистрации
     * @return true, если регистрация успешна; false, если пользователь уже существует
     */
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

    /**
     * Авторизация пользователя.
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     * @return true, если пользователь аутентифицирован; false, если не найден или пароль неверен
     */
    public boolean loginUser(String username, String password) {
        boolean res = false;
        for (User u : storage.getUsers()) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                res = true;
                storage.getAudits().add(new Audit(username,"Вход", LocalDateTime.now()));
                break;
            }
        }
        return res;
    }
}
