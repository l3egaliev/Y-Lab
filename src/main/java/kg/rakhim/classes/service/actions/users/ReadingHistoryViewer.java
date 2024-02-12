package kg.rakhim.classes.service.actions.users;

import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.service.MeterReadingService;
import org.json.JSONObject;

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
                readings.add(new JSONObject().put("Показание", m));
            }else {
                return Collections.emptyList();
            }
        }
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
        return res;
    }
}
