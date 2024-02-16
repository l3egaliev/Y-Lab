package kg.rakhim.classes.service;

import kg.rakhim.classes.dao.MeterTypesDAO;
import kg.rakhim.classes.models.MeterType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с типами счетчиков.
 */
@Service
public class MeterTypesService {
    private final MeterTypesDAO meterTypesDAO;

    /**
     * Создает экземпляр класса MeterTypesService с указанным DAO для типов счетчиков.
     *
     * @param meterTypesDAO Repository для работы с данными о типах счетчиков
     */
    public MeterTypesService(MeterTypesDAO meterTypesDAO) {
        this.meterTypesDAO = meterTypesDAO;
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
     * @return 1, если тип успешно сохранен; 0, если тип уже существует
     */
    public boolean saveType(String newType) {
        if (!newType.isEmpty()) {
            if (meterTypesDAO.isExists(newType)) {
                return false;
            } else {
                save(new MeterType(newType));
                return  true;
            }
        }
        return false;
    }
    public boolean isExistsType(String type){
        return meterTypesDAO.isExists(type);
    }
}