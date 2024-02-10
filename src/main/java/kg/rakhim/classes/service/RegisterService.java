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

import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.out.ConsoleOut;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Scanner;

//import static kg.rakhim.classes.in.ConsoleIn.start;

/**
 * Класс {@code RegisterService} предоставляет сервис для регистрации и авторизации пользователей.
 */
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
        Audit audit = new Audit(user.getUsername(), "Регистрация", LocalDateTime.now());
        auditService.save(audit);
        message.put("message: ", "Регистрация успешна");
        return Map.of(true, message);
    }

    /**
     * Вход в систему существующего пользователя.
     *
     * @return имя вошедшего в систему пользователя
     */
//    public String authorization() {
//        Scanner scanner = new Scanner(System.in);
//        String res = "";
//        ConsoleOut.printLine("Введите имя: ");
//        String username = scanner.next();
//        ConsoleOut.printLine("Пароль: ");
//        String pass = scanner.next();
//        if (username.isEmpty() || pass.isEmpty()) {
//            ConsoleOut.printLine("Вы ввели не корректные данные");
//        }
//        boolean log = loginUser(username, pass);
//        if (!log) {
//            ConsoleOut.printLine("Что пошло не так попробуйте снова\n");
//            start();
//        }
//        else {
//            res = username;
//            ConsoleOut.printLine("Вы вошли в систему");
//        }
//        return res;
//    }

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
