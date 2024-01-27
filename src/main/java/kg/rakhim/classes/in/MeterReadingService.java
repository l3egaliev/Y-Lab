package kg.rakhim.classes.in;

import kg.rakhim.classes.database.Storage;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.models.UserRole;

import java.util.Scanner;


public class MeterReadingService {
    private final static Storage storage = new Storage();
    private final static Scanner scanner = new Scanner(System.in);
    private final static RegisterService registerService = new RegisterService(storage);
    private final static UsersActions usersActions = new UsersActions(scanner, storage);
    private static boolean loop = true;


    public static void start() {
        while (loop) {
            System.out.println("Регистрация - 1");
            System.out.println("Войти - 2");
            int button = scanner.nextInt();
            if (button == 1) {
                String res = registration();
                if (res.equals("")) {
                    loop = false;
                    break;
                }else commandList(res);
            } else if (button == 2) {
                String res = login();
                if (res.equals("")) {
                    loop = false;
                    break;
                }else commandList(res);
            }
        }
    }

    static void commandList(String username) {
        int command;
        System.out.println("--------------\n");
        System.out.println("""
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
            case 5 -> exit();
        }
    }


    static String registration() {
        String res = "";
        System.out.println("Ваше имя: ");
        String username = scanner.next();
        System.out.println("Пароль: ");
        String pass = scanner.next();
        if (username.isEmpty() || pass.isEmpty()) {
            System.out.println("Вы ввели не корректные данные");
        }
        UserRole role = UserRole.USER;
        User user = new User(username, pass, role);
        boolean reg = registerService.registerUser(user);
        if (!reg)
            System.out.println("Произошла ошибка");
        else {
            res = username;
            System.out.println("Вы успешно зарегистрировались");
        }
        return res;
    }


    static String login() {
        String res = "";
        System.out.println("Введите имя: ");
        String username = scanner.next();
        System.out.println("Пароль: ");
        String pass = scanner.next();
        if (username.isEmpty() || pass.isEmpty()) {
            System.out.println("Вы ввели не корректные данные");
        }
        boolean log = registerService.loginUser(username, pass);
        if (!log)
            System.out.println("Произошла ошибка");
        else {
            res = username;
            System.out.println("Вы вошли в систему");
        }
        return res;
    }

    public static void exit() {
        System.out.println("""
                Вы вышли из системы.
                 ~ Войти в другой аккаунт - 1
                 ~ Отключить систему - любая другая кнопка.""");
        String c = scanner.next();
        if (c.equals("1")) {
            start();
        } else {
            loop = false;
        }
    }
}
