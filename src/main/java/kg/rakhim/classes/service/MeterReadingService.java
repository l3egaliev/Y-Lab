package kg.rakhim.classes.service;

import kg.rakhim.classes.annotations.AuditableAction;
import kg.rakhim.classes.context.UserContext;
import kg.rakhim.classes.context.UserDetails;
import kg.rakhim.classes.dao.MeterReadingDAO;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.models.User;
import org.mapstruct.control.MappingControl;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.*;

//import static kg.rakhim.classes.in.ConsoleIn.commandList;

/**
 * Сервис для работы с показаниями счетчиков.
 */
@Service
@AuditableAction
public class MeterReadingService {
    private final MeterReadingDAO meterReadingDAO;
    private final UserService userService;
    /**
     * Создает экземпляр класса MeterReadingService с указанным DAO и сервисом типов счетчиков.
     *
     * @param meterReadingDAO     Repository для работы с данными о показаниях счетчиков
     */
    public MeterReadingService(MeterReadingDAO meterReadingDAO, UserService userService) {
        this.meterReadingDAO = meterReadingDAO;
        this.userService = userService;
    }
    public Optional<MeterReading> findById(int id) {
        return meterReadingDAO.get(id);
    }

    public List<MeterReading> findAll() {
        return meterReadingDAO.getAll();
    }

    public boolean save(MeterReading e) {
        String username = UserContext.getCurrentUser().getUsername();
        List<MeterReading> readings = meterReadingDAO.getByUser(username);
        for (MeterReading r : readings){
            if (r.getDateTime().getMonthValue() == LocalDateTime.now().getMonthValue()
            && r.getMeterType().getType().equals(e.getMeterType().getType())){
                return false;
            }
        }
        e.setUser(new User(username));
        meterReadingDAO.save(e);
        return true;
    }
    public List<MeterReading> findByUsername(){
        return meterReadingDAO.getByUser(UserContext.getCurrentUser().getUsername());
    }

    public List<MeterReading> findActualReadings(){
        UserDetails userDetails = UserContext.getCurrentUser();
        userDetails.setAction(Map.of("findActualReadings", "Просмотр актуальных показаний"));
        List<MeterReading> res = new ArrayList<>();
        for (MeterReading m : meterReadingDAO.getByUser(userDetails.getUsername())){
            if (m.getDateTime().getMonthValue() == LocalDateTime.now().getMonthValue()){
                res.add(m);
            }
        }
        return res;
    }
    public List<MeterReading> findForMonth(int month){
        UserDetails userDetails = UserContext.getCurrentUser();
        userDetails.setAction(Map.of("findForMonth", "Просмотр показаний за указанный месяц"));
        List<MeterReading> res = new ArrayList<>();
        for (MeterReading m : meterReadingDAO.getByUser(userDetails.getUsername())){
            if (m.getDateTime().getMonthValue() == month){
                res.add(m);
            }
        }
        return res;
    }

    public List<MeterReading> historyOfUserReadings(String username){
        UserDetails userDetails = UserContext.getCurrentUser();
        userDetails.setAction(Map.of("historyOfUserReadings", "Просмотр всю историю показаний"));
        String admin = UserContext.getCurrentUser().getUsername();
        if (userService.isAdmin(admin)) {
            return meterReadingDAO.getByUser(username);
        }
        return Collections.emptyList();
    }

    public List<MeterReading> readingsOfOneUserForMonth(String username, int month){
        List<MeterReading> result = new ArrayList<>();
        if (userService.isAdmin(UserContext.getCurrentUser().getUsername())){
            for (MeterReading m : meterReadingDAO.getByUser(username)){
                if (m.getDateTime().getMonthValue() == month){
                    result.add(m);
                }
            }
        }
        return result;
    }

    public List<MeterReading> readingsOfAllUsersForMonth(int month){
        List<MeterReading> result = new ArrayList<>();
        if (userService.isAdmin(UserContext.getCurrentUser().getUsername())) {
            for (MeterReading m : meterReadingDAO.getAll()) {
                if (m.getDateTime().getMonthValue() == month) {
                    result.add(m);
                }
            }
        }
        return result;
    }

    public List<MeterReading> actualReadingsOfAllUsers(){
        List<MeterReading> result = new ArrayList<>();
        if (userService.isAdmin(UserContext.getCurrentUser().getUsername())) {
            for (MeterReading m : meterReadingDAO.getAll()) {
                if (m.getDateTime().getMonthValue() == LocalDateTime.now().getMonthValue()) {
                    result.add(m);
                }
            }
        }
        return result;
    }
}