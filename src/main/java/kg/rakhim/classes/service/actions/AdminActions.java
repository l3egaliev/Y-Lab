package kg.rakhim.classes.service.actions;

import kg.rakhim.classes.context.ApplicationContext;
import kg.rakhim.classes.models.*;
import kg.rakhim.classes.out.ConsoleOut;
import kg.rakhim.classes.service.AuditService;
import kg.rakhim.classes.service.MeterReadingService;
import kg.rakhim.classes.service.MeterTypesService;
import kg.rakhim.classes.service.UserService;
import kg.rakhim.classes.service.actions.admins.EditorReadings;
import kg.rakhim.classes.service.actions.admins.ViewerReadings;
import org.json.JSONObject;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

//import static kg.rakhim.classes.in.ConsoleIn.commandList;

/**
 * Класс {@code AdminActions} предоставляет методы для административных действий, связанных с показаниями счетчиков.
 */
public class AdminActions {
    private final UserService userService;
    private final MeterReadingService mService;
    private final MeterTypesService typesService;
    private final ViewerReadings viewerReadings;
    private final EditorReadings editorReadings;

    /**
     * Конструирует экземпляр {@code AdminActions} с указанным хранилищем.
     */
    public AdminActions(){
        this.userService = (UserService) ApplicationContext.getContext("userService");
        this.mService = (MeterReadingService) ApplicationContext.getContext("meterReadingService");
        this.typesService = (MeterTypesService) ApplicationContext.getContext("meterTypeService");
        this.viewerReadings = new ViewerReadings(mService);
        this.editorReadings = new EditorReadings(userService, typesService);
    }
    public Map<Boolean, JSONObject> addNewType(String admin, String type){
        return editorReadings.setNewType(admin, type);
    }
    public List<JSONObject> actualReadingsOfAllUsers(){
        return viewerReadings.viewActualReadingsOfUsers();
    }
    public List<JSONObject> readingsOfAllUsersForMonth(int month){
        return viewerReadings.viewReadingsHistoryForMonth(month);
    }
    public List<JSONObject> readingHistoryOfUser(String username){
        return viewerReadings.viewReadingsHistoryOfUser(username);
    }
//    /**
//     * Выводит на экран историю аудитов конкретного пользователя.
//     *
//     * @param adminName имя администратора
//     */
//    public void viewAudit(String adminName) {
//        ConsoleOut.printLine("Имя пользователя: ");
//        String username = scanner.next();
//        ConsoleOut.printLine("Аудиты пользователя "+username+":");
//        for (Audit a : auditService.findAll()){
//            if (a.getUsername().equals(username)){
//                ConsoleOut.printLine(a);
//            }
//        }
////        commandList(adminName);
//    }
}