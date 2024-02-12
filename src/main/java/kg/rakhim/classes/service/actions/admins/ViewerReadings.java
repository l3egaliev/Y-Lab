package kg.rakhim.classes.service.actions.admins;

import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.out.ConsoleOut;
import kg.rakhim.classes.service.MeterReadingService;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ViewerReadings {
    private final MeterReadingService mService;

    public ViewerReadings(MeterReadingService mService) {
        this.mService = mService;
    }

    /**
     * Выводит на экран актуальные показания всех пользователей для текущего месяца.
     */
    public List<JSONObject> viewActualReadingsOfUsers() {
        List<JSONObject> res = new ArrayList<>();
        for (MeterReading m : mService.findAll()){
                if (m.getDate().getMonthValue() == LocalDateTime.now().getMonthValue()) {
                    JSONObject message = new JSONObject();
                    message.put("Показание", m);
                    res.add(message);
                }
            }
        return res;
    }

    /**
     * Выводит на экран историю показаний пользователей за указанный месяц.
     *
     * @param month    номер месяца
     */
    public List<JSONObject> viewReadingsHistoryForMonth(int month) {
        List<JSONObject> res = new ArrayList<>();
        for(MeterReading meterReading : mService.findAll()){
            if (meterReading.getDate().getMonthValue() == month) {
                JSONObject message = new JSONObject();
                message.put("Показание", meterReading);
                res.add(message);
            }
        }
        return res;
    }
    /**
     * Выводит на экран историю показаний конкретного пользователя.
     *
     * @param username    имя пользователя, чьи показания нужно просмотреть
     */
    public List<JSONObject> viewReadingsHistoryOfUser(String username){
        List<JSONObject> res = new ArrayList<>();
        for (MeterReading m : mService.findAll()){
            if (m.getUser().getUsername().equals(username)) {
                JSONObject message = new JSONObject();
                message.put("Показание", m);
                res.add(message);
            }
        }
        return res;
    }
}
