package kg.rakhim.classes.context;

import kg.rakhim.classes.dao.*;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.repository.BaseRepository;
import kg.rakhim.classes.service.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ApplicationContext {
    private static final Map<String, Object> CONTEXT = new HashMap<>();

    public static void loadContext(){
        loadDAO();
        loadService();
    }

    private static void loadDAO(){
        CONTEXT.put("meterReadingDAO", new MeterReadingDAO());
        CONTEXT.put("meterTypeDAO", new MeterTypesDAO());
        CONTEXT.put("userDAO", new UserDAO());
        CONTEXT.put("auditDAO", new AuditDAO());
    }

    private static void loadService(){
        CONTEXT.put("meterReadingService", new MeterReadingService());
        CONTEXT.put("meterTypeService", new MeterTypesService());
        CONTEXT.put("userService", new UserService());
        CONTEXT.put("auditService", new AuditService());
        CONTEXT.put("registerService", new RegisterService());
    }

    public static Object getContext(String o){
        return CONTEXT.get(o);
    }

}
