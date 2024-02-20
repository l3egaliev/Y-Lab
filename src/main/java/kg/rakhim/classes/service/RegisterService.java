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

import kg.rakhim.classes.annotations.AuditableForAuth;
import kg.rakhim.classes.annotations.Loggable;
import kg.rakhim.classes.context.UserContext;
import kg.rakhim.classes.context.UserDetails;
import kg.rakhim.classes.dao.UserDAO;
import kg.rakhim.classes.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


/**
 * Класс {@code RegisterService} предоставляет сервис для регистрации и авторизации пользователей.
 */
@Service
@AuditableForAuth
@Loggable
public class RegisterService {
    private final UserService userService;
    private final UserContext userContext;
    /**
     * Конструктор для создания экземпляра класса {@code RegisterService}.
     */
    @Autowired
    public RegisterService(UserService userService, UserContext userContext) {
        this.userService = userService;
        this.userContext = userContext;
    }

    /**
     * Регистрация нового пользователя.
     *
     * @param user объект User для регистрации
     */
    public void registerUser(User user) {
        user.setRole("USER");
        userService.save(user);
        UserDetails userDetails = new UserDetails(user.getUsername());
        userContext.setCurrentUser(userDetails);
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
        UserDetails userDetails = new UserDetails(user.getUsername(), Map.of("loginUser","Вход в систему"));
        userContext.setCurrentUser(userDetails);
        return res;
    }
}
