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

import kg.rakhim.classes.context.ApplicationContext;
import kg.rakhim.classes.dao.UserDAO;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.out.ConsoleOut;

import java.time.LocalDateTime;
import java.util.Scanner;

import static kg.rakhim.classes.in.ConsoleIn.start;

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
     * @return имя зарегистрированного пользователя
     */
    public String registration() {
        Scanner scanner = new Scanner(System.in);
        String res = "";
        ConsoleOut.printLine("Ваше имя: ");
        String username = scanner.next();
        ConsoleOut.printLine("Пароль: ");
        String pass = scanner.next();
        User user = new User(username, pass, "USER");
        int reg = registerUser(user);
        if (reg == 0){
            start();
            return res;
        }
        else {
            res = username;
            ConsoleOut.printLine("Вы успешно зарегистрировались");
        }
        return res;
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
            if (u.getUsername().equals(user.getUsername())) {
                ConsoleOut.printLine("Такой пользователь уже существует");
                return 0;
            }if (user.getUsername().length()<3 || user.getPassword().length()<5) {
                ConsoleOut.printLine("Вы ввели не корректные данные");
                ConsoleOut.printLine("Username - должно быть не менее 3 символов");
                ConsoleOut.printLine("Password - должно быть не менее 5 символов\n");
                return 0;
            }
        }
        userService.save(user);
        Audit audit = new Audit(user.getUsername(), "Регистрация", LocalDateTime.now());
        auditService.save(audit);
        return 1;
    }

    /**
     * Вход в систему существующего пользователя.
     *
     * @return имя вошедшего в систему пользователя
     */
    public String authorization() {
        Scanner scanner = new Scanner(System.in);
        String res = "";
        ConsoleOut.printLine("Введите имя: ");
        String username = scanner.next();
        ConsoleOut.printLine("Пароль: ");
        String pass = scanner.next();
        if (username.isEmpty() || pass.isEmpty()) {
            ConsoleOut.printLine("Вы ввели не корректные данные");
        }
        boolean log = loginUser(username, pass);
        if (!log) {
            ConsoleOut.printLine("Что пошло не так попробуйте снова\n");
            start();
        }
        else {
            res = username;
            ConsoleOut.printLine("Вы вошли в систему");
        }
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
