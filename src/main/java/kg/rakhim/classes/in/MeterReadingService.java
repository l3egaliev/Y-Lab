package kg.rakhim.classes.in;

import kg.rakhim.classes.database.Storage;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.models.UserRole;

import java.util.Scanner;


public class MeterReadingService {
    private final static Storage storage = new Storage();
    private final static Scanner scanner = new Scanner(System.in);
    private final static RegisterService registerService = new RegisterService(storage);
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
                }
            } else if (button == 2) {
                String res = login();
                if (res.equals("")) {
                    loop = false;
                    break;
                }
            }
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

}
