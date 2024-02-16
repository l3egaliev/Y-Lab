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
 * @see Storage
 * @see Audit
 * @see User
 */
package kg.rakhim.classes.service;

import kg.rakhim.classes.models.User;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Класс {@code RegisterService} предоставляет сервис для регистрации и авторизации пользователей.
 */
@Service
public class RegisterService {
    private final UserService userService;

    /**
     * Конструктор для создания экземпляра класса {@code RegisterService}.
     */
    public RegisterService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Регистрация нового пользователя.
     *
     * @param user объект User для регистрации
     */
    public void registerUser(User user) {
        user.setRole("USER");
        userService.save(user);
    }

    /**
     * Авторизация пользователя.
     *
     * @return true, если пользователь аутентифицирован; false, если не найден или пароль неверен
     */
    public boolean loginUser(User user) {
        boolean res;
        Optional<User> u = userService.findByUsername(user.getUsername());
        res = u.isPresent() && u.get().getPassword().equals(user.getPassword());
        return res;
    }
}
