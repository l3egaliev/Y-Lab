package kg.rakhim.classes.service;

import kg.rakhim.classes.dto.ReadingResponseDTO;
import kg.rakhim.classes.models.MeterReading;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.auditable.annotations.EnableXXX;
import ru.auditable.data.UserData;
import ru.auditable.data.UserInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@EnableXXX
public class AdminService {
    private final UserService userService;
    private final UserData userData;
    private final MeterReadingService meterReadingService;

    @Autowired
    public AdminService(UserService userService, UserData userData, MeterReadingService meterReadingService) {
        this.userService = userService;
        this.userData = userData;
        this.meterReadingService = meterReadingService;
    }

    public List<ReadingResponseDTO> historyOfUserReadings(String username){
        UserInfo userInfo = userData.getCurrentUser();
        List<ReadingResponseDTO> res = new ArrayList<>();
        if (userService.isAdmin(userInfo.getUsername())) {
            meterReadingService.findByUsername(username).forEach(m -> {
                res.add(ReadingResponseDTO.builder()
                        .user(m.getUser().getUsername())
                        .value(m.getReadingValue())
                        .type(m.getMeterType().getType())
                        .dateTime(m.getDateTime()).build());
            });
            userInfo.setActions(Map.of("historyOfUserReadings",
                    "Просмотр всю историю показаний конкретного пользователя"));
        }else{
            res.add(ReadingResponseDTO.builder().errorMessage("У вас нет прав доступа.").build());
        }
        return res;
    }

    public List<ReadingResponseDTO> readingsOfOneUserForMonth(String username, Integer month){
        UserInfo userInfo = userData.getCurrentUser();
        List<ReadingResponseDTO> res = new ArrayList<>();
        if (userService.isAdmin(userInfo.getUsername())){
            meterReadingService.findByUsername(username).forEach(v -> {
                if (v.getDateTime().getMonthValue() == month){
                    res.add(ReadingResponseDTO.builder()
                            .user(v.getUser().getUsername())
                            .value(v.getReadingValue())
                            .type(v.getMeterType().getType())
                            .dateTime(v.getDateTime()).build());
                }
            });
            userInfo.setActions(Map.of("readingsOfOneUserForMonth", "Просмотр показаний " +
                    "пользователя за указанный месяц"));
        }else{
            res.add(ReadingResponseDTO.builder().errorMessage("У вас нет прав доступа.").build());
        }
        return res;
    }

    public List<ReadingResponseDTO> readingsOfAllUsersForMonth(Integer month){
        UserInfo userInfo = userData.getCurrentUser();
        List<ReadingResponseDTO> res = new ArrayList<>();
        if (userService.isAdmin(userInfo.getUsername())) {
            meterReadingService.findAll().forEach(v -> {
                if(v.getDateTime().getMonthValue() == month){
                    res.add(ReadingResponseDTO.builder()
                            .user(v.getUser().getUsername())
                            .value(v.getReadingValue())
                            .type(v.getMeterType().getType())
                            .dateTime(v.getDateTime()).build());
                }
            });
            userInfo.setActions(Map.of("readingsOfAllUsersForMonth", "Просмотр показаний всех" +
                    " пользователей за указанный месяц"));
        }else{
            res.add(ReadingResponseDTO.builder().errorMessage("У вас нет прав доступа.").build());
        }
        return res;
    }

    public List<ReadingResponseDTO> actualReadingsOfAllUsers(){
        UserInfo userInfo = userData.getCurrentUser();
        List<ReadingResponseDTO> res = new ArrayList<>();
        if (userService.isAdmin(userInfo.getUsername())) {
            meterReadingService.findAll().forEach(v -> {
                if (v.getDateTime().getMonthValue() == LocalDateTime.now().getMonthValue()) {
                    res.add(ReadingResponseDTO.builder()
                            .user(v.getUser().getUsername())
                            .value(v.getReadingValue())
                            .type(v.getMeterType().getType())
                            .dateTime(v.getDateTime()).build());
                }
            });
            userInfo.setActions(Map.of("actualReadingsOfAllUsers", "Просмотр актуальных " +
                    "показаний всех пользователей"));
        }else{
            res.add(ReadingResponseDTO.builder().errorMessage("У вас нет прав доступа.").build());
        }
        return res;
    }
}
