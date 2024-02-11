package kg.rakhim.classes.service.actions;

import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.out.ConsoleOut;
import kg.rakhim.classes.service.MeterReadingService;
import org.json.JSONObject;

import java.sql.ClientInfoStatus;
import java.time.LocalDateTime;
import java.util.*;

public class ReadingHistoryViewer {
    private final MeterReadingService mService;

    public ReadingHistoryViewer(MeterReadingService mService) {
        this.mService = mService;
    }

    /**
     * Реализация просмотра актуальных показаний.
     *
     * @param username имя пользователя, просматривающего показания
     */
    public List<JSONObject> viewCurrentReadings(String username) {
        List<JSONObject> readings = new ArrayList<>();
        for (MeterReading m : mService.findAll()) {
            if (m.getUser().getUsername().equals(username) &&
                    m.getDate().getMonthValue() == LocalDateTime.now().getMonthValue()) {
                readings.add(new JSONObject().put("Показание:", m));
            }else {
                return Collections.emptyList();
            }
        }
        return readings;
//        auditService.save(new Audit(username,"Просмотр актуальных показаний", LocalDateTime.now()));
//        commandList(username);
    }

//    /**
//     * Реализация просмотра истории подачи показаний за конкретный месяц.
//     *
//     * @param username имя пользователя, просматривающего историю
//     */
//    public void viewReadingHistoryForMonth(String username) {
//        ConsoleOut.printLine("За какой месяц хотите посмотреть? (формат: 1-12)");
//        int month = scanner.nextInt();
//        ConsoleOut.printLine("Ваши показания за " + month + " - месяц:");
//        for (MeterReading m : mService.findAll()) {
//            if (m.getUser().getUsername().equals(username)) {
//                if (m.getDate().getMonthValue() == month) {
//                    ConsoleOut.printLine(" - " + m);
//                }
//            }
//        }
////        auditService.save(new Audit(username, "Просмотр истории за конкретный месяц", LocalDateTime.now()));
////        commandList(username);
//    }
//
//    /**
//     * Реализация просмотра истории всех поданных показаний.
//     *
//     * @param username имя пользователя, просматривающего историю
//     */
//    public void viewReadingHistory(String username) {
//        ConsoleOut.printLine("История показаний:");
//        for (MeterReading m : mService.findAll()) {
//            if (m.getUser().getUsername().equals(username)) {
//                ConsoleOut.printLine("\t - " + m);
//            }
//        }
////        auditService.save(new Audit(username,"Просмотр истории всех показаний", LocalDateTime.now()));
////        commandList(username);
//    }
}
