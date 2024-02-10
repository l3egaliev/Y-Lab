package kg.rakhim.classes.context;

import kg.rakhim.classes.dao.*;
import kg.rakhim.classes.service.*;

import java.util.HashMap;
import java.util.Map;

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
        CONTEXT.put("meterReadingService", new MeterReadingService((MeterReadingDAO) getContext("meterReadingDAO"),
                new MeterTypesService((MeterTypesDAO) getContext("meterTypeDAO"))));
        CONTEXT.put("meterTypeService", new MeterTypesService((MeterTypesDAO) getContext("meterTypeDAO")));
        CONTEXT.put("userService", new UserService((UserDAO) getContext("userDAO")));
        CONTEXT.put("auditService", new AuditService((AuditDAO) getContext("auditDAO")));
        CONTEXT.put("registerService", new RegisterService((UserService) getContext("userService"),
                (AuditService) getContext("auditService")));
    }

    public static Object getContext(String o){
        return CONTEXT.get(o);
    }

}
