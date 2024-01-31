/**
 * Класс {@code MeterReadingService} предоставляет сервис для управления регистрацией пользователей,
 * входом в систему и выполнением команд пользователей и администраторов в отношении показаний счетчиков.
 * <p>
 * Зависит от классов {@link Storage}, {@link RegisterService}, {@link UsersActions}, {@link AdminActions}.
 * </p>
 *
 * @author Рахим Нуралиев
 * @version 1.0
 * @since 2024-01-26
 * @see Storage
 * @see RegisterService
 * @see UsersActions
 * @see AdminActions
 */
package kg.rakhim.classes.in;

import kg.rakhim.classes.database.Storage;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.models.UserRole;
import kg.rakhim.classes.out.ConsoleOut;
import kg.rakhim.classes.service.AdminActions;
import kg.rakhim.classes.service.RegisterService;
import kg.rakhim.classes.service.UsersActions;

import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * Класс {@code MeterReadingService} предоставляет сервис для управления регистрацией пользователей,
 * входом в систему и выполнением команд пользователей и администраторов в отношении показаний счетчиков.
 */
public class ConsoleIn {
    private final static Storage storage = new Storage();
    private final static Scanner scanner = new Scanner(System.in);
    private final static RegisterService registerService = new RegisterService(storage);
    private final static UsersActions usersActions = new UsersActions(scanner, storage);
    private final static AdminActions adminActions = new AdminActions(storage, scanner);
    private static boolean loop = true;

    /**
     * Запускает систему регистрации и входа в систему.
     */
    public static void start() {
        while (loop) {
            ConsoleOut.printLine("Регистрация - 1");
            ConsoleOut.printLine("Войти - 2");
            int button = scanner.nextInt();
            if (button == 1) {
                String res = registration();
                if (res.equals("")) {
                    loop = false;
                    break;
                } else commandList(res);
            } else if (button == 2) {
                String res = login();
                if (res.equals("")) {
                    loop = false;
                    break;
                } else commandList(res);
            }
        }
    }

    /**
     * Основной список команд для пользователя или администратора.
     *
     * @param username имя пользователя или администратора
     */
    public static void commandList(String username) {
        int command;
        UserRole role = storage.getUser(username).getRole();
        // У админа другие возможности, поэтому список команд отличается
        if (role.equals(UserRole.ADMIN)) {
            ConsoleOut.printLine("--------------\n"+
                            """
                            Список команд:\s
                            - Просмотр актуальных показаний пользователей - 1
                            - Просмотр поданных показаний всех пользователей за конкретный месяц - 2
                            - Просмотр всю историю поданных показаний конкретного пользователя - 3
                            - Добавить новый тип показаний - 4
                            - Просмотр аудита пользователя - 5
                            - Выйти - 6""");
            command = scanner.nextInt();
            switch (command) {
                case 1 -> adminActions.viewActualReadingsOfUsers(username);
                case 2 -> {
                    ConsoleOut.printLine("Выберите месяц: ");
                    int m = scanner.nextInt();
                    adminActions.viewReadingsHistoryForMonth(m, username);
                }
                case 3 -> {
                    ConsoleOut.printLine("Имя пользователя: ");
                    String searchUser = scanner.next();
                    adminActions.viewReadingsHistoryOfUser(searchUser, username);
                }
                case 4 ->  adminActions.setNewType(username);
                case 5 -> adminActions.viewAudit(username);
                case 6 -> exit(username);
            }
        } else {
            ConsoleOut.printLine("--------------\n"+
                        """
                        Список команд:\s
                        - Подать показание - 1
                        - Просмотр актуального показания - 2
                        - Просмотр поданных показаний за конкретный месяц - 3
                        - Просмотр всю историю поданных показаний - 4
                        - Выйти - 5""");
            command = scanner.nextInt();
            switch (command) {
                case 1 -> usersActions.submitCounterReading(username);
                case 2 -> usersActions.viewCurrentReadings(username);
                case 3 -> usersActions.viewReadingHistoryForMonth(username);
                case 4 -> usersActions.viewReadingHistory(username);
                case 5 -> exit(username);
            }
        }
    }

    /**
     * Регистрация нового пользователя.
     *
     * @return имя зарегистрированного пользователя
     */
    static String registration() {
        String res = "";
        ConsoleOut.printLine("Ваше имя: ");
        String username = scanner.next();
        ConsoleOut.printLine("Пароль: ");
        String pass = scanner.next();
        if (username.isEmpty() || pass.isEmpty()) {
            ConsoleOut.printLine("Вы ввели не корректные данные");
        }
        UserRole role = UserRole.USER;
        User user = new User(username, pass, role);
        boolean reg = registerService.registerUser(user);
        if (!reg)
            ConsoleOut.printLine("Произошла ошибка");
        else {
            res = username;
            ConsoleOut.printLine("Вы успешно зарегистрировались");
        }
        return res;
    }

    /**
     * Вход в систему существующего пользователя.
     *
     * @return имя вошедшего в систему пользователя
     */
    static String login() {
        String res = "";
        ConsoleOut.printLine("Введите имя: ");
        String username = scanner.next();
        ConsoleOut.printLine("Пароль: ");
        String pass = scanner.next();
        if (username.isEmpty() || pass.isEmpty()) {
            ConsoleOut.printLine("Вы ввели не корректные данные");
        }
        boolean log = registerService.loginUser(username, pass);
        if (!log)
            ConsoleOut.printLine("Произошла ошибка");
        else {
            res = username;
            ConsoleOut.printLine("Вы вошли в систему");
        }
        return res;
    }

    /**
     * Выход из системы.
     */
    public static void exit(String username) {
        ConsoleOut.printLine("""
                Вы вышли из системы.
                 ~ Войти в другой аккаунт - 1
                 ~ Отключить систему - любая другая кнопка.""");
        String c = scanner.next();
        if (c.equals("1")) {
            storage.getAudits().add(new Audit(username, "Выход", LocalDateTime.now()));
            start();
        } else {
            loop = false;
        }
    }
}
