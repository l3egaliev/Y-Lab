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
import kg.rakhim.classes.context.UserContext;
import kg.rakhim.classes.context.UserDetails;
import kg.rakhim.classes.models.User;
import org.json.JSONObject;

import java.util.Map;

//import static kg.rakhim.classes.in.ConsoleIn.start;

/**
 * Класс {@code RegisterService} предоставляет сервис для регистрации и авторизации пользователей.
 */
@AuditableForAuth
public class RegisterService {
    private final UserService userService;
    private final AuditService auditService;

    /**
     * Конструктор для создания экземпляра класса {@code RegisterService}.
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
    public Map<Boolean, JSONObject> registerUser(User user) {
        JSONObject message = new JSONObject();
        // Проверка - не существует ли такого пользователя
        for (User u : userService.findAll()){
            if (u.getUsername().equals(user.getUsername())) {
                message.put("message: ", "Такой пользователь уже существует");
                return Map.of(false, message);
            }
        }
        userService.save(user);
        UserDetails userDetails = new UserDetails(user.getUsername(), Map.of("registerUser", "Регистрация"));
        UserContext.setCurrentUser(userDetails);
        message.put("message: ", "Регистрация успешна");
        return Map.of(true, message);
    }
    /**
     * Авторизация пользователя.
     *
     * @param username имя пользователя
     * @param password пароль пользователя
     * @return true, если пользователь аутентифицирован; false, если не найден или пароль неверен
     */
    public Map<Boolean, JSONObject> loginUser(String username, String password) {
        JSONObject message = new JSONObject();
        for (User u : userService.findAll()) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                message.put("message:", "Вы успешно вошли в систему");
                UserDetails userDetails = new UserDetails(username, Map.of("loginUser", "Вход в систему"));
                UserContext.setCurrentUser(userDetails);
                return Map.of(true, message);
            }
        }
        UserDetails error = new UserDetails("null", Map.of("loginUser", "Не получилось войти"));
        UserContext.setCurrentUser(error);
        message.put("message:", "Некорректные данные");
        return Map.of(false, message);
    }
}
