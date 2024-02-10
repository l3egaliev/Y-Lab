package kg.rakhim.classes.context;

import kg.rakhim.classes.dao.*;
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
        loadDAO();
        loadRepository();
        loadService();
        loadMapper();
    }

    private static void loadDAO(){
        CONTEXT.put("meterReadingDAO", new MeterReadingDAO());
        CONTEXT.put("meterTypeDAO", new MeterTypesDAO());
        CONTEXT.put("userDAO", new UserDAO());
        CONTEXT.put("auditDAO", new AuditDAO());
    }

    private static void loadService(){
        CONTEXT.put("meterTypeService", new MeterTypesService((MeterTypeRepositoryImpl) getContext("meterTypeRepository")));
        CONTEXT.put("userService", new UserService((UserRepositoryImpl) getContext("userRepository")));
        CONTEXT.put("auditService", new AuditService((AuditRepositoryImpl) getContext("auditRepository")));
        CONTEXT.put("registerService", new RegisterService((UserService) getContext("userService"),
                (AuditService) getContext("auditService")));
        CONTEXT.put("meterReadingService",
                new MeterReadingService((MeterReadingRepositoryImpl) getContext("meterReadingRepository"),
                        (MeterTypesService) getContext("meterTypeService")));
    }

    private static void loadRepository(){
        CONTEXT.put("meterReadingRepository", new MeterReadingRepositoryImpl((MeterReadingDAO) getContext("meterReadingDAO")));
        CONTEXT.put("auditRepository", new AuditRepositoryImpl((AuditDAO) getContext("auditDAO")));
        CONTEXT.put("meterTypeRepository", new MeterTypeRepositoryImpl((MeterTypesDAO) getContext("meterTypeDAO")));
        CONTEXT.put("userRepository", new UserRepositoryImpl((UserDAO) getContext("userDAO")));
    }

    private static void loadMapper(){
        CONTEXT.put("modelMapper", new ModelMapper());
    }

    public static Object getContext(String o){
        return CONTEXT.get(o);
    }

}
