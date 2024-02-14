package kg.rakhim.classes.context;

import kg.rakhim.classes.dao.AuditDAO;
import kg.rakhim.classes.dao.MeterReadingDAO;
import kg.rakhim.classes.dao.MeterTypesDAO;
import kg.rakhim.classes.dao.UserDAO;
import kg.rakhim.classes.repository.impl.AuditRepositoryImpl;
import kg.rakhim.classes.repository.impl.MeterReadingRepositoryImpl;
import kg.rakhim.classes.repository.impl.MeterTypeRepositoryImpl;
import kg.rakhim.classes.repository.impl.UserRepositoryImpl;
import kg.rakhim.classes.service.*;
import org.modelmapper.ModelMapper;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    private static final Map<String, Object> CONTEXT = new HashMap<>();

    public static void loadContext(){
        loadService();
        loadMapper();
    }
    private static void loadMapper(){
        CONTEXT.put("modelMapper", new ModelMapper());
    }
    private static void loadService(){
        CONTEXT.put("auditService", new AuditService(new AuditRepositoryImpl(new AuditDAO())));
        CONTEXT.put("auditsService", new AuditService(new AuditRepositoryImpl(new AuditDAO())));
        CONTEXT.put("userService", new UserService(new UserRepositoryImpl(new UserDAO())));
        CONTEXT.put("meterTypeService", new MeterTypesService(new MeterTypeRepositoryImpl(new MeterTypesDAO())));
        CONTEXT.put("registerService", new RegisterService((UserService) getContext("userService")));
        CONTEXT.put("meterReadingService",
                new MeterReadingService(new MeterReadingRepositoryImpl(new MeterReadingDAO())));
    }
    public static Object getContext(String o){
        return CONTEXT.get(o);
    }
}
