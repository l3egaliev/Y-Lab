package kg.rakhim.classes.service;

import kg.rakhim.classes.annotations.AuditableAction;
import kg.rakhim.classes.annotations.Loggable;
import kg.rakhim.classes.context.UserContext;
import kg.rakhim.classes.context.UserDetails;
import kg.rakhim.classes.dao.MeterReadingDAO;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.models.User;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
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
@Loggable
public class MeterReadingService {
    private final MeterReadingDAO meterReadingDAO;
    private final UserContext userContext;
    /**
     * Создает экземпляр класса MeterReadingService с указанным DAO и сервисом типов счетчиков.
     *
     * @param meterReadingDAO     Repository для работы с данными о показаниях счетчиков
     */
    @Autowired
    public MeterReadingService(MeterReadingDAO meterReadingDAO, UserContext userContext) {
        this.meterReadingDAO = meterReadingDAO;
        this.userContext = userContext;
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

    public boolean saveReading(MeterReading e) {
        UserDetails userDetails = userContext.getCurrentUser();
        List<MeterReading> readings = meterReadingDAO.getByUser(userDetails.getUsername());
        for (MeterReading r : readings){
            if (r.getDateTime().getMonthValue() == LocalDateTime.now().getMonthValue()
            && r.getMeterType().getType().equals(e.getMeterType().getType())){
                return false;
            }
        }
        e.setUser(new User(userDetails.getUsername()));
        meterReadingDAO.save(e);
        userDetails.setAction(Map.of("saveReading", "Подача показания"));
        return true;
    }
    public List<MeterReading> allHistoryOfUser(){
        UserDetails userDetails = userContext.getCurrentUser();
        userDetails.setAction(Map.of("allHistoryOfUser", "Просмотр всю историю показаний"));
        return meterReadingDAO.getByUser(userDetails.getUsername());
    }

    public List<MeterReading> findActualReadings(){
        UserDetails userDetails = userContext.getCurrentUser();
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
        UserDetails userDetails = userContext.getCurrentUser();
        userDetails.setAction(Map.of("findForMonth", "Просмотр показаний за указанный месяц"));
        List<MeterReading> res = new ArrayList<>();
        for (MeterReading m : meterReadingDAO.getByUser(userDetails.getUsername())){
            if (m.getDateTime().getMonthValue() == month){
                res.add(m);
            }
        }
        return res;
    }
}