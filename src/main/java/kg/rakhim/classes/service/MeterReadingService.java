package kg.rakhim.classes.service;

import kg.rakhim.classes.dao.MeterReadingDAO;
import kg.rakhim.classes.dto.ReadingResponseDTO;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.auditable.annotations.EnableXXX;
import ru.auditable.data.UserData;
import ru.auditable.data.UserInfo;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Сервис для работы с показаниями счетчиков.
 */
@Service
@EnableXXX
public class MeterReadingService {
    private final MeterReadingDAO meterReadingDAO;
    private final UserData userData;
    /**
     * Создает экземпляр класса MeterReadingService с указанным DAO и сервисом типов счетчиков.
     *
     * @param meterReadingDAO     Repository для работы с данными о показаниях счетчиков
     */
    @Autowired
    public MeterReadingService(MeterReadingDAO meterReadingDAO, UserData userData) {
        this.meterReadingDAO = meterReadingDAO;
        this.userData = userData;
    }
    public Optional<MeterReading> findById(int id) {
        return meterReadingDAO.get(id);
    }

    public List<MeterReading> findAll() {
        return meterReadingDAO.getAll();
    }
    public List<MeterReading> findByUsername(String username){
        if (meterReadingDAO.getByUser(username).isEmpty()){
            return Collections.emptyList();
        }
        return meterReadingDAO.getByUser(username);
    }

    public Map<String, Object> saveReading(MeterReading e) {
        UserInfo userInfo = userData.getCurrentUser();
        if(userInfo != null && !userInfo.getUsername().isEmpty()){
            List<MeterReading> readings = meterReadingDAO.getByUser(userInfo.getUsername());
            for (MeterReading r : readings){
                if (r.getDateTime().getMonthValue() == LocalDateTime.now().getMonthValue()
                && r.getMeterType().getType().equals(e.getMeterType().getType())){
                    return Map.of("message", "Вы в этом месяце уже отправляли показание за - "+e.getMeterType().getType());
                }
            }
            e.setUser(new User(userInfo.getUsername()));
            meterReadingDAO.save(e);
            userInfo.setActions(Map.of("saveReading", "Подача показания"));
            return Map.of("message", "Успешно");
        }else{
            return Map.of("message", "Войдите в систему");
        }
    }
    public List<ReadingResponseDTO> allHistoryOfUser(){
        UserInfo userInfo = userData.getCurrentUser();
        List<ReadingResponseDTO> response = new ArrayList<>();
       if (userInfo != null && !userInfo.getUsername().isEmpty()) {
           meterReadingDAO.getByUser(userInfo.getUsername()).forEach(v -> {
               response.add(ReadingResponseDTO.builder()
                       .user(v.getUser().getUsername())
                       .value(v.getReadingValue())
                       .type(v.getMeterType().getType())
                       .dateTime(v.getDateTime()).build());
           });
           userInfo.setActions(Map.of("allHistoryOfUser", "Просмотр всю историю показаний"));
       }else{
           response.add(ReadingResponseDTO.builder().errorMessage("Войдите в систему").build());
       }
        return response;
    }

    public List<ReadingResponseDTO> findActualReadings(){
        UserInfo userInfo = userData.getCurrentUser();
        List<ReadingResponseDTO> res = new ArrayList<>();
        if (userInfo != null && !userInfo.getUsername().isEmpty()) {
            for (MeterReading m : meterReadingDAO.getByUser(userInfo.getUsername())) {
                if (m.getDateTime().getMonthValue() == LocalDateTime.now().getMonthValue()) {
                    res.add(ReadingResponseDTO.builder()
                            .user(m.getUser().getUsername())
                            .value(m.getReadingValue())
                            .type(m.getMeterType().getType())
                            .dateTime(m.getDateTime()).build());
                }
            }
            userInfo.setActions(Map.of("findActualReadings", "Просмотр актуальных показаний"));
        }else{
            res.add(ReadingResponseDTO.builder().errorMessage("Войдите в систему").build());
        }
        return res;
    }
    public List<ReadingResponseDTO> findForMonth(int month){
        UserInfo userInfo = userData.getCurrentUser();
        List<ReadingResponseDTO> res = new ArrayList<>();
        if (userInfo != null && !userInfo.getUsername().isEmpty()) {
            meterReadingDAO.getByUser(userInfo.getUsername()).forEach(m -> {
                if (m.getDateTime().getMonthValue() == month) {
                    res.add(ReadingResponseDTO.builder()
                            .user(m.getUser().getUsername())
                            .value(m.getReadingValue())
                            .type(m.getMeterType().getType())
                            .dateTime(m.getDateTime())
                            .build());
                }
            });
            userInfo.setActions(Map.of("findForMonth", "Просмотр показаний за указанный месяц"));
        }else{
            res.add(ReadingResponseDTO.builder().errorMessage("Войдите в систему").build());
        }
        return res;
    }
}