package kg.rakhim.classes.service.actions.users;

import kg.rakhim.classes.annotations.Auditable;
import kg.rakhim.classes.context.UserContext;
import kg.rakhim.classes.context.UserDetails;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.service.MeterReadingService;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.*;
@Auditable
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
        for (MeterReading m : mService.findByUsername(username)) {
            if (m.getDate().getMonthValue() == LocalDateTime.now().getMonthValue()) {
                readings.add(new JSONObject().put("Показание", m));
            }else {
                return Collections.emptyList();
            }
        }
        UserDetails userDetails = UserContext.getCurrentUser();
        userDetails.setAction(Map.of("viewCurrentReadings", "Просмотр актуальных показаний"));
        return readings;
    }

    /**
     * Реализация просмотра истории подачи показаний за конкретный месяц.
     *
     * @param username имя пользователя, просматривающего историю
     */
    public List<JSONObject> viewReadingHistoryForMonth(String username, int month) {
        List<JSONObject> result = new ArrayList<>();
        JSONObject message = new JSONObject();
        for (MeterReading m : mService.findAll()) {
            if (m.getUser().getUsername().equals(username)
            && m.getDate().getMonthValue() == month) {
                message.put("Показание", m);
                result.add(message);
            }
        }
        UserDetails userDetails = UserContext.getCurrentUser();
        System.out.println(userDetails.getUsername());
        userDetails.setAction(Map.of("viewReadingHistoryForMonth", "Просмотр истории подачи " +
                "показаний за конкретный месяц"));
        return result;
    }

    /**
     * Реализация просмотра истории всех поданных показаний.
     *
     * @param username имя пользователя, просматривающего историю
     */
    public List<JSONObject> viewAllReadingHistory(String username) {
        List<JSONObject> res = new ArrayList<>();
        for (MeterReading m : mService.findAll()) {
            if (m.getUser().getUsername().equals(username)) {
                JSONObject message = new JSONObject();
                message.put("Показание", m);
                res.add(message);
            }
        }
        UserDetails userDetails = UserContext.getCurrentUser();
        userDetails.setAction(Map.of("viewCurrentReadings", "Просмотр истории всех поданных показаний"));
        return res;
    }
}
