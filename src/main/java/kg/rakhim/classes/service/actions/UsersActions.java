package kg.rakhim.classes.service.actions;

import kg.rakhim.classes.context.ApplicationContext;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.service.MeterReadingService;
import kg.rakhim.classes.service.MeterTypesService;
import kg.rakhim.classes.service.UserService;
import kg.rakhim.classes.service.actions.users.ReadingHistoryViewer;
import kg.rakhim.classes.service.actions.users.ReadingSender;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

//import static kg.rakhim.classes.in.ConsoleIn.commandList;

/**
 * Класс {@code UsersActions} предоставляет методы для действий пользователей в отношении показаний счетчиков.
 */
public class UsersActions {
    private final UserService userService;
    private final MeterReadingService mService;
    private final MeterTypesService typesService;
    private final ReadingSender readingSender;
    private final ReadingHistoryViewer viewer;

    /**
     * Конструирует экземпляр {@code UsersActions}.
     *
     */
    public UsersActions() {
        this.userService = (UserService) ApplicationContext.getContext("userService");
        this.mService = (MeterReadingService) ApplicationContext.getContext("meterReadingService");
        this.typesService = (MeterTypesService) ApplicationContext.getContext("meterTypeService");
        this.readingSender = new ReadingSender(userService, mService, typesService);
        viewer = new ReadingHistoryViewer(mService);
    }

    public Map<Boolean, JSONObject> submitNewReading(MeterReading meterReading){
        return readingSender.sendCounterReading(meterReading);
    }
    public List<JSONObject> viewActualReadings(String username){
        return viewer.viewCurrentReadings(username);
    }
    public List<JSONObject> viewReadingsForMonth(String username, int monthValue){
        return viewer.viewReadingHistoryForMonth(username, monthValue);
    }
    public List<JSONObject> viewAllHistory(String username){
        return viewer.viewAllReadingHistory(username);
    }
}