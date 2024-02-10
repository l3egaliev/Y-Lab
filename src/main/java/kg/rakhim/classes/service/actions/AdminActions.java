package kg.rakhim.classes.service.actions;

import kg.rakhim.classes.models.*;
import kg.rakhim.classes.out.ConsoleOut;
import kg.rakhim.classes.service.AuditService;
import kg.rakhim.classes.service.MeterReadingService;
import kg.rakhim.classes.service.MeterTypesService;
import kg.rakhim.classes.service.UserService;

import java.time.LocalDateTime;
import java.util.Scanner;

//import static kg.rakhim.classes.in.ConsoleIn.commandList;

/**
 * Класс {@code AdminActions} предоставляет методы для административных действий, связанных с показаниями счетчиков.
 */
public class AdminActions {
    private static final Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    private final AuditService auditService;
    private final MeterReadingService mService;
    private final MeterTypesService typesService;

    /**
     * Конструирует экземпляр {@code AdminActions} с указанным хранилищем.
     *
     * @param userService    сервис пользователей
     * @param auditService   сервис аудита
     * @param mService       сервис показаний счетчиков
     * @param typesService   сервис типов счетчиков
     */
    public AdminActions(UserService userService, AuditService auditService,
                        MeterReadingService mService, MeterTypesService typesService){
        this.userService = userService;
        this.auditService = auditService;
        this.mService = mService;
        this.typesService = typesService;
    }

    /**
     * Выводит на экран актуальные показания всех пользователей для текущего месяца.
     *
     * @param username имя текущего администратора
     */
    public void viewActualReadingsOfUsers(String username) {
        ConsoleOut.printLine("Актуальные показания всех пользователей: ");
        for (MeterReading m : mService.findAll()){
            // не показываем показания user'а созданного системой
            if(!m.getUser().getUsername().equals("default")) {
                if (m.getDate().getMonthValue() == LocalDateTime.now().getMonthValue()) {
                    ConsoleOut.printLine("\t- " + m);
                }
            }
        }
        auditService.save(new Audit(username, "Просмотр актуальных показаний всех пользователей", LocalDateTime.now()));
//        commandList(username);
    }

    /**
     * Выводит на экран историю показаний пользователей за указанный месяц.
     *
     * @param month    номер месяца
     * @param username имя текущего администратора
     */
    public void viewReadingsHistoryForMonth(int month, String username) {
        ConsoleOut.printLine("Все показания пользователей за "+month+" - месяц");
        for(MeterReading meterReading : mService.findAll()){
            if (meterReading.getDate().getMonthValue() == month) {
                ConsoleOut.printLine("\t- " + meterReading);
            }
        }
        auditService.save(new Audit(username, "Просмотр истории показаний пользователя за конкретный месяц", LocalDateTime.now()));
//        commandList(username);
    }


    /**
     * Выводит на экран историю показаний конкретного пользователя.
     *
     * @param username    имя пользователя, чьи показания нужно просмотреть
     * @param currentUser имя текущего администратора
     */
    public void viewReadingsHistoryOfUser(String username, String currentUser){
        ConsoleOut.printLine("Все показания пользователя "+username);
        for (MeterReading m : mService.findAll()){
            if (m.getUser().getUsername().equals(username)) {
                ConsoleOut.printLine("\t~ " + m);
            }
        }
        auditService.save(new Audit(username, "Просмотр истории всех показаний конкретного пользователя",
                LocalDateTime.now()));
//        commandList(currentUser);
    }

    /**
     * Добавление нового тип счетчика
     *
     * @param username    имя пользователя, чьи показания нужно просмотреть
     */
    public void setNewType(String username){
        if (userService.isAdmin(username)){
            ConsoleOut.printLine("Название нового типа счетчика: ");
            String newType = scanner.nextLine();
            if(typesService.saveType(newType)){
                auditService.save(new Audit(username, "Добавление счетчика", LocalDateTime.now()));
            }else{
                ConsoleOut.printLine("Попробуйте еще раз");
                setNewType(username);
            }
//            commandList(username);
        }
    }

    /**
     * Выводит на экран историю аудитов конкретного пользователя.
     *
     * @param adminName имя администратора
     */
    public void viewAudit(String adminName) {
        ConsoleOut.printLine("Имя пользователя: ");
        String username = scanner.next();
        ConsoleOut.printLine("Аудиты пользователя "+username+":");
        for (Audit a : auditService.findAll()){
            if (a.getUsername().equals(username)){
                ConsoleOut.printLine(a);
            }
        }
//        commandList(adminName);
    }
}