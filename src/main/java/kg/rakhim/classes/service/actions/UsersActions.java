package kg.rakhim.classes.service.actions;

import kg.rakhim.classes.context.ApplicationContext;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.out.ConsoleOut;
import kg.rakhim.classes.service.AuditService;
import kg.rakhim.classes.service.MeterReadingService;
import kg.rakhim.classes.service.MeterTypesService;
import kg.rakhim.classes.service.UserService;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.*;

//import static kg.rakhim.classes.in.ConsoleIn.commandList;

/**
 * Класс {@code UsersActions} предоставляет методы для действий пользователей в отношении показаний счетчиков.
 */
public class UsersActions {
    private static final Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    private final AuditService auditService;
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
        this.auditService = (AuditService) ApplicationContext.getContext("auditService");
        this.mService = (MeterReadingService) ApplicationContext.getContext("meterReadingService");
        this.typesService = (MeterTypesService) ApplicationContext.getContext("meterTypeService");
        this.readingSender = new ReadingSender(userService, mService, typesService);
        viewer = new ReadingHistoryViewer(mService);
    }

    public Map<Boolean, JSONObject> submitNewReading(MeterReading meterReading){
        return readingSender.sendCounterReading(meterReading);
    }
    public List<JSONObject> viewReadings(String username){
        return viewer.viewCurrentReadings(username);
    }
}