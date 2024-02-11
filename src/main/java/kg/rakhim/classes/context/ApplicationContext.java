package kg.rakhim.classes.context;

import kg.rakhim.classes.dao.*;
import kg.rakhim.classes.dao.migration.ConnectionLoader;
import kg.rakhim.classes.dao.migration.LoadProperties;
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
        loadConnection();
        loadService();
        loadMapper();
    }
    private static void loadService(){
        CONTEXT.put("meterTypeService", new MeterTypesService(new MeterTypeRepositoryImpl(new MeterTypesDAO())));
        CONTEXT.put("userService", new UserService(new UserRepositoryImpl(new UserDAO())));
        CONTEXT.put("auditService", new AuditService(new AuditRepositoryImpl(new AuditDAO())));
        CONTEXT.put("registerService", new RegisterService((UserService) getContext("userService"),
                (AuditService) getContext("auditService")));
        CONTEXT.put("meterReadingService",
                new MeterReadingService(new MeterReadingRepositoryImpl(new MeterReadingDAO())));
    }
    private static void loadMapper(){
        CONTEXT.put("modelMapper", new ModelMapper());
    }
    private static void loadConnection(){
        CONTEXT.put("properties", new LoadProperties());
        CONTEXT.put("connectionLoader", new ConnectionLoader());
    }
    public static Object getContext(String o){
        return CONTEXT.get(o);
    }

}
