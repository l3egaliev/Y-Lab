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
package kg.rakhim.classes.service;

import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.models.User;

import java.time.LocalDateTime;

/**
 * Класс {@code RegisterService} предоставляет сервис для регистрации и авторизации пользователей.
 */
public class RegisterService {
    private final UserService userService;
    private final AuditService auditService;

    /**
     * Конструктор для создания экземпляра класса {@code RegisterService}.
     *
     * @param userService  TODO
     * @param auditService TODO
     */
    public RegisterService(UserService userService, AuditService auditService) {
        this.userService = userService;
        this.auditService = auditService;
    }

    /**
     * Регистрация нового пользователя.
     *
     * @param user объект User для регистрации
     * @return true, если регистрация успешна; false, если пользователь уже существует
     */
    public int registerUser(User user) {
        // Проверка - не существует ли такого пользователя
        for (User u : userService.findAll()){
            if (u.getUsername().equals(user.getUsername()))
                return 0;
        }

        // Добавление аудита
        Audit audit = new Audit(user.getUsername(), "Регистрация", LocalDateTime.now());
        auditService.save(audit);

        // Сохранение пользователя
        userService.save(user);
        return 1;
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
        for (User u : userService.findAll()) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                res = true;
                auditService.save(new Audit(username,"Вход", LocalDateTime.now()));
                break;
            }
        }
        return res;
    }
}
