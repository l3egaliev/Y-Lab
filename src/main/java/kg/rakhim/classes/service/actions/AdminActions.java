/**
 * Класс {@code AdminActions} представляет собой административные действия, которые администратор может выполнять
 * относительно показаний счетчиков. Он включает в себя методы для просмотра актуальных показаний всех пользователей,
 * просмотра истории показаний за определенный месяц, просмотра истории показаний конкретного пользователя
 * и добавления нового тип счетчика.
 * <p>
 * Этот класс зависит от класса {@link Storage} для доступа и манипулирования данными о показаниях счетчиков.
 * </p>
 *
 * @author Рахим Нуралиев
 * @version 1.0
 * @since 2024-01-26
 * @see Storage
 * @see MeterReading
 */
package kg.rakhim.classes.service.actions;

import kg.rakhim.classes.context.ApplicationContext;
import kg.rakhim.classes.dao.*;
import kg.rakhim.classes.models.*;
import kg.rakhim.classes.out.ConsoleOut;
import kg.rakhim.classes.service.AuditService;
import kg.rakhim.classes.service.MeterReadingService;
import kg.rakhim.classes.service.MeterTypesService;
import kg.rakhim.classes.service.UserService;

import java.time.LocalDateTime;
import java.util.Scanner;

import static kg.rakhim.classes.in.ConsoleIn.commandList;

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
     */
    public AdminActions(){
        this.userService = (UserService) ApplicationContext.getContext("userService");
        this.auditService = (AuditService) ApplicationContext.getContext("auditService");
        this.mService = (MeterReadingService) ApplicationContext.getContext("meterReadingService");
        this.typesService = (MeterTypesService) ApplicationContext.getContext("meterTypeService");
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
        commandList(username);
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
        auditService.save(new Audit(username, "Просмотр историю показаний пользователя за конкретный месяц", LocalDateTime.now()));
        commandList(username);
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
        commandList(currentUser);
    }

    /**
     * Добавление нового тип счетчика
     *
     * @param username    имя пользователя, чьи показания нужно просмотреть
     */
    public void setNewType(String username){
        if (userService.isAdmin(username)){
            ConsoleOut.printLine("Название нового типа счетчика: ");
            String newType = scanner.next();
            for (MeterType m : typesService.findAll()) {
                if (!(m.getType().equals(newType))){
                    ConsoleOut.printLine("Новый тип показания успешно добавлен");
                    typesService.save(new MeterType(newType));
                }else {
                    ConsoleOut.printLine("Такой тип уже существует");
                }
                break;
            }
            commandList(username);
            auditService.save(new Audit(username, "Добавление счетчика", LocalDateTime.now()));
        }
    }

    /**
     * Выводит на экран историю аудитов конкретного пользователя.
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
        commandList(adminName);
    }
}
