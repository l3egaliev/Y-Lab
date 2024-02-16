package kg.rakhim.classes.service;

import kg.rakhim.classes.context.UserContext;
import kg.rakhim.classes.context.UserDetails;
import kg.rakhim.classes.dao.MeterReadingDAO;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.models.User;
import org.mapstruct.control.MappingControl;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import static kg.rakhim.classes.in.ConsoleIn.commandList;

/**
 * Сервис для работы с показаниями счетчиков.
 */
@Service
public class MeterReadingService {
    private final MeterReadingDAO meterReadingDAO;
    private final MeterTypesService typesService;
    /**
     * Создает экземпляр класса MeterReadingService с указанным DAO и сервисом типов счетчиков.
     *
     * @param meterReadingDAO     Repository для работы с данными о показаниях счетчиков
     */
    public MeterReadingService(MeterReadingDAO meterReadingDAO, MeterTypesService typesService) {
        this.meterReadingDAO = meterReadingDAO;
        this.typesService = typesService;
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
        String username = UserContext.getCurrentUser().getUsername();
        List<MeterReading> res = new ArrayList<>();
        for (MeterReading m : meterReadingDAO.getByUser(username)){
            if (m.getDateTime().getMonthValue() == LocalDateTime.now().getMonthValue()){
                res.add(m);
            }
        }
        return res;
    }
    public List<MeterReading> findForMonth(int month){
        String username = UserContext.getCurrentUser().getUsername();
        List<MeterReading> res = new ArrayList<>();
        for (MeterReading m : meterReadingDAO.getByUser(username)){
            if (m.getDateTime().getMonthValue() == month){
                res.add(m);
            }
        }
        return res;
    }
}