///**
// * Класс {@code MeterReadingService} предоставляет сервис для управления регистрацией пользователей,
// * входом в систему и выполнением команд пользователей и администраторов в отношении показаний счетчиков.
// * <p>
// * Зависит от классов {@link Storage}, {@link RegisterService}, {@link UsersActions}, {@link AdminActions}.
// * </p>
// *
// * @author Рахим Нуралиев
// * @version 1.0
// * @since 2024-01-26
// * @see Storage
// * @see RegisterService
// * @see UsersActions
// * @see AdminActions
// */
//package kg.rakhim.classes.in;
//
//import kg.rakhim.classes.context.ApplicationContext;
//import kg.rakhim.classes.models.Audit;
//import kg.rakhim.classes.models.User;
//import kg.rakhim.classes.out.ConsoleOut;
//import kg.rakhim.classes.service.*;
//import kg.rakhim.classes.service.actions.AdminActions;
//import kg.rakhim.classes.service.actions.UsersActions;
//
//import java.time.LocalDateTime;
//import java.util.Scanner;
//
///**
// * Класс {@code MeterReadingService} предоставляет сервис для управления регистрацией пользователей,
// * входом в систему и выполнением команд пользователей и администраторов в отношении показаний счетчиков.
// */
//public class ConsoleIn {
//    private final static Scanner scanner = new Scanner(System.in);
//    private final static UserService userService = (UserService) ApplicationContext.getContext("userService");
//    private final static AuditService auditService = (AuditService) ApplicationContext.getContext("auditService");
//    private final static RegisterService registerService = (RegisterService) ApplicationContext.getContext("registerService");
//    private final static UsersActions usersActions = new UsersActions(
//            userService, auditService,
//            (MeterReadingService) ApplicationContext.getContext("meterReadingService"),
//            (MeterTypesService) ApplicationContext.getContext("meterTypeService")
//    );
//    private final static AdminActions adminActions = new AdminActions(
//            userService, auditService,
//            (MeterReadingService) ApplicationContext.getContext("meterReadingService"),
//            (MeterTypesService) ApplicationContext.getContext("meterTypeService"));
//    private static boolean loop = true;
//
//    /**
//     * Запускает систему регистрации и входа в систему.
//     */
//    public static void start() {
//        while (loop) {
//            ConsoleOut.printLine("Регистрация - 1");
//            ConsoleOut.printLine("Войти - 2");
//            String button = scanner.next();
//            switch (button) {
//                case "1" -> handleRegistration();
//                case "2" -> handleLogin();
//                default -> {
//                    ConsoleOut.printLine("Неправильная команда");
//                }
//            }
//        }
//    }
//
//
//    private static void handleRegistration() {
//        String res = registerService.registration();
//        if (res.isEmpty()) {
//            loop = false;
//        } else {
//            commandList(res);
//        }
//    }
//
//    private static void handleLogin() {
//        String res = registerService.authorization();
//        if (res.isEmpty()) {
//            loop = false;
//        } else {
//            commandList(res);
//        }
//    }
//
//
//    /**
//     * Основной список команд для пользователя или администратора.
//     *
//     * @param username имя пользователя или администратора
//     */
//    public static void commandList(String username) {
//        String command;
//        if (userService.isAdmin(username)) {
//            ConsoleOut.printLine("--------------\n"+
//                            """
//                            Список команд:\s
//                            - Просмотр актуальных показаний пользователей - 1
//                            - Просмотр поданных показаний всех пользователей за конкретный месяц - 2
//                            - Просмотр всю историю поданных показаний конкретного пользователя - 3
//                            - Добавить новый тип показаний - 4
//                            - Просмотр аудита пользователя - 5
//                            - Выйти - 6""");
//            command = scanner.next();
//            switch (command) {
//                case "1" -> adminActions.viewActualReadingsOfUsers(username);
//                case "2" -> {
//                    ConsoleOut.printLine("Выберите месяц: ");
//                    int m = scanner.nextInt();
//                    adminActions.viewReadingsHistoryForMonth(m, username);
//                }
//                case "3" -> {
//                    ConsoleOut.printLine("Имя пользователя: ");
//                    String searchUser = scanner.next();
//                    adminActions.viewReadingsHistoryOfUser(searchUser, username);
//                }
//                case "4" ->  adminActions.setNewType(username);
//                case "5" -> adminActions.viewAudit(username);
//                case "6"-> exit(username);
//                default -> {
//                    ConsoleOut.printLine("Неправильно выбранная команда");
//                    commandList(username);
//                }
//            }
//        } else {
//            ConsoleOut.printLine("--------------\n"+
//                        """
//                        Список команд:\s
//                        - Подать показание - 1
//                        - Просмотр актуального показания - 2
//                        - Просмотр поданных показаний за конкретный месяц - 3
//                        - Просмотр всю историю поданных показаний - 4
//                        - Выйти - 5""");
//            command = scanner.next();
//            switch (command) {
//                case "1" -> usersActions.submitNewReading(username);
//                case "2" -> usersActions.viewCurrentReadings(username);
//                case "3" -> usersActions.viewReadingHistoryForMonth(username);
//                case "4" -> usersActions.viewReadingHistory(username);
//                case "5" -> exit(username);
//                default -> {
//                    ConsoleOut.printLine("Неправильно выбранная команда");
//                    commandList(username);
//                }
//            }
//        }
//    }
//    /**
//     * Выход из системы.
//     */
//    public static void exit(String username) {
//        ConsoleOut.printLine("""
//                Вы вышли из системы.
//                 ~ Войти в другой аккаунт - 1
//                 ~ Отключить систему - любая другая кнопка.""");
//        String c = scanner.next();
//        if (!c.equals("1")) {
//            System.exit(0);
//        } else {
//            auditService.save(new Audit(username, "Выход", LocalDateTime.now()));
//        }
//        start();
//    }
//}
