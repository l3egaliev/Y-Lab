package kg.rakhim.classes.service.actions;

import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.out.ConsoleOut;
import kg.rakhim.classes.service.AuditService;
import kg.rakhim.classes.service.MeterReadingService;
import kg.rakhim.classes.service.MeterTypesService;
import kg.rakhim.classes.service.UserService;

import java.time.LocalDateTime;
import java.util.*;

import static kg.rakhim.classes.in.ConsoleIn.commandList;

/**
 * Класс {@code UsersActions} предоставляет методы для действий пользователей в отношении показаний счетчиков.
 */
public class UsersActions {
    private static final Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    private final AuditService auditService;
    private final MeterReadingService mService;
    private final MeterTypesService typesService;

    /**
     * Конструирует экземпляр {@code UsersActions} с указанным сканером и хранилищем.
     *
     * @param userService    сервис пользователей
     * @param auditService   сервис аудита
     * @param mService       сервис показаний счетчиков
     * @param typesService   сервис типов счетчиков
     */
    public UsersActions(
            UserService userService,
            AuditService auditService,
            MeterReadingService mService,
            MeterTypesService typesService) {
        this.userService = userService;
        this.auditService = auditService;
        this.mService = mService;
        this.typesService = typesService;
    }

    public void submitNewReading(String username){
        mService.sendCounterReading(username, userService, auditService, scanner);
    }

    /**
     * Реализация просмотра актуальных показаний.
     *
     * @param username имя пользователя, просматривающего показания
     */
    public void viewCurrentReadings(String username) {
        ConsoleOut.printLine("\nВаши актуальные показания: ");
        for (MeterReading m : mService.findAll()) {
            if (m.getUser().getUsername().equals(username) &&
                    m.getDate().getMonthValue() == LocalDateTime.now().getMonthValue()) {
                ConsoleOut.printLine("\t- " + m);
            }
        }
        auditService.save(new Audit(username,"Просмотр актуальных показаний", LocalDateTime.now()));
        commandList(username);
    }

    /**
     * Реализация просмотра истории подачи показаний за конкретный месяц.
     *
     * @param username имя пользователя, просматривающего историю
     */
    public void viewReadingHistoryForMonth(String username) {
        ConsoleOut.printLine("За какой месяц хотите посмотреть? (формат: 1-12)");
        int month = scanner.nextInt();
        ConsoleOut.printLine("Ваши показания за " + month + " - месяц:");
        for (MeterReading m : mService.findAll()) {
            if (m.getUser().getUsername().equals(username)) {
                if (m.getDate().getMonthValue() == month) {
                    ConsoleOut.printLine(" - " + m);
                }
            }
        }
        auditService.save(new Audit(username, "Просмотр истории за конкретный месяц", LocalDateTime.now()));
        commandList(username);
    }

    /**
     * Реализация просмотра истории всех поданных показаний.
     *
     * @param username имя пользователя, просматривающего историю
     */
    public void viewReadingHistory(String username) {
        ConsoleOut.printLine("История показаний:");
        for (MeterReading m : mService.findAll()) {
            if (m.getUser().getUsername().equals(username)) {
                ConsoleOut.printLine("\t - " + m);
            }
        }
        auditService.save(new Audit(username,"Просмотр истории всех показаний", LocalDateTime.now()));
        commandList(username);
    }
}