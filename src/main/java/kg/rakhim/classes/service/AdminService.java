package kg.rakhim.classes.service;

import kg.rakhim.classes.annotations.AuditableAction;
import kg.rakhim.classes.annotations.Loggable;
import kg.rakhim.classes.context.UserContext;
import kg.rakhim.classes.context.UserDetails;
import kg.rakhim.classes.models.MeterReading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@AuditableAction
@Loggable
public class AdminService {
    private final UserService userService;
    private final UserContext userContext;
    private final MeterReadingService meterReadingService;

    @Autowired
    public AdminService(UserService userService, UserContext userContext, MeterReadingService meterReadingService) {
        this.userService = userService;
        this.userContext = userContext;
        this.meterReadingService = meterReadingService;
    }

    public List<MeterReading> historyOfUserReadings(String username){
        UserDetails userDetails = userContext.getCurrentUser();
        userDetails.setAction(Map.of("historyOfUserReadings", "Просмотр всю историю показаний конкретного пользователя"));
        if (userService.isAdmin(userDetails.getUsername())) {
            return meterReadingService.findByUsername(username);
        }
        return Collections.emptyList();
    }

    public List<MeterReading> readingsOfOneUserForMonth(String username, Integer month){
        UserDetails userDetails = userContext.getCurrentUser();
        List<MeterReading> result = new ArrayList<>();
        if (userService.isAdmin(userDetails.getUsername())){
            meterReadingService.findByUsername(username).forEach(v -> {
                if (v.getDateTime().getMonthValue() == month){
                    result.add(v);
                }
            });
        }
        userDetails.setAction(Map.of("readingsOfOneUserForMonth", "Просмотр показаний " +
                "пользователя за указанный месяц"));
        return result;
    }

    public List<MeterReading> readingsOfAllUsersForMonth(Integer month){
        UserDetails userDetails = userContext.getCurrentUser();
        List<MeterReading> result = new ArrayList<>();
        if (userService.isAdmin(userDetails.getUsername())) {
            meterReadingService.findAll().forEach(v -> {
                if(v.getDateTime().getMonthValue() == month){
                    result.add(v);
                }
            });
        }
        userDetails.setAction(Map.of("readingsOfAllUsersForMonth", "Просмотр показаний всех" +
                " пользователей за указанный месяц"));
        return result;
    }

    public List<MeterReading> actualReadingsOfAllUsers(){
        UserDetails userDetails = userContext.getCurrentUser();
        List<MeterReading> result = new ArrayList<>();
        if (userService.isAdmin(userDetails.getUsername())) {
            meterReadingService.findAll().forEach(v -> {
                if (v.getDateTime().getMonthValue() == LocalDateTime.now().getMonthValue()) {
                    result.add(v);
                }
            });
        }
        userDetails.setAction(Map.of("actualReadingsOfAllUsers", "Просмотр актуальных " +
                "показаний всех пользователей"));
        return result;
    }
}
