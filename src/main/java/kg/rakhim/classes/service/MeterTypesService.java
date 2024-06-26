package kg.rakhim.classes.service;

import ru.auditable.data.UserData;
import kg.rakhim.classes.dao.MeterTypesDAO;
import kg.rakhim.classes.models.MeterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.auditable.annotations.EnableXXX;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Сервис для работы с типами счетчиков.
 */
@Service
@EnableXXX
public class MeterTypesService {
    private final MeterTypesDAO meterTypesDAO;
    private final UserService userService;
    private final UserData userData;
    /**
     * Создает экземпляр класса MeterTypesService с указанным DAO для типов счетчиков.
     *
     * @param meterTypesDAO Repository для работы с данными о типах счетчиков
     */
    @Autowired
    public MeterTypesService(MeterTypesDAO meterTypesDAO, UserService userService, UserData userData) {
        this.meterTypesDAO = meterTypesDAO;
        this.userService = userService;
        this.userData = userData;
    }
    public Optional<MeterType> findById(int id) {
        return meterTypesDAO.get(id);
    }

    public List<MeterType> findAll() {
        return meterTypesDAO.getAll();
    }

    public void save(MeterType type) {
        meterTypesDAO.save(type);
    }

    /**
     * Сохраняет новый тип счетчика.
     *
     * @param newType новый тип счетчика
     * @param err     Ошибки для ответа
     * @return true, если тип успешно сохранен; false, если тип уже существует или user'а нет прав
     */
    public boolean saveType(String newType, List<String> err) {
        if (isExistsType(newType)) {
            err.add("Такой тип уже существует");
            return false;
        }else if (userService.isAdmin(userData.getCurrentUser().getUsername())){
            save(new MeterType(newType));
            userData.getCurrentUser().setActions(Map.of("saveType", "Добавление нового счетчика"));
            return true;
        }
        err.add("У вас нет прав для добавления счетчика");
        return false;
    }
    public boolean isExistsType(String type){
        return meterTypesDAO.isExists(type);
    }
}