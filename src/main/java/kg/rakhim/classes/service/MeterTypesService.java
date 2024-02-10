package kg.rakhim.classes.service;

import kg.rakhim.classes.dao.interfaces.MeterTypesDAOIn;
import kg.rakhim.classes.models.MeterType;
import kg.rakhim.classes.out.ConsoleOut;
import kg.rakhim.classes.repository.MeterTypesRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с типами счетчиков.
 */
public class MeterTypesService implements MeterTypesRepository {
    private final MeterTypesDAOIn meterTypesDAO;

    /**
     * Создает экземпляр класса MeterTypesService с указанным DAO для типов счетчиков.
     *
     * @param meterTypesDAO DAO для работы с данными о типах счетчиков
     */
    public MeterTypesService(MeterTypesDAOIn meterTypesDAO) {
        this.meterTypesDAO = meterTypesDAO;
    }

    // TODO
    @Override
    public Optional<MeterType> findById(int id) {
        return Optional.of(meterTypesDAO.get(id));
    }

    @Override
    public List<MeterType> findAll() {
        return meterTypesDAO.getAll();
    }

    @Override
    public void save(MeterType type) {
        meterTypesDAO.save(type);
    }

    /**
     * Сохраняет новый тип счетчика.
     *
     * @param newType новый тип счетчика
     * @return 1, если тип успешно сохранен; 0, если тип уже существует
     */
    public int saveType(String newType){
        if (meterTypesDAO.isExists(newType)){
            ConsoleOut.printLine("Такой тип уже существует");
            return 0;
        }else {
            save(new MeterType(newType));
            ConsoleOut.printLine("Новый тип успешно добавлен");
            return 1;
        }
    }
}