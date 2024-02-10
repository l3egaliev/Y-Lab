package kg.rakhim.classes.service;

import kg.rakhim.classes.dao.interfaces.MeterTypesDAOIn;
import kg.rakhim.classes.models.MeterType;
import kg.rakhim.classes.out.ConsoleOut;
import kg.rakhim.classes.repository.MeterTypesRepository;
import kg.rakhim.classes.repository.impl.MeterTypeRepositoryImpl;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с типами счетчиков.
 */
public class MeterTypesService {
    private final MeterTypeRepositoryImpl meterTypesRepository;

    /**
     * Создает экземпляр класса MeterTypesService с указанным DAO для типов счетчиков.
     *
     * @param meterTypesRepository Repository для работы с данными о типах счетчиков
     */
    public MeterTypesService(MeterTypeRepositoryImpl meterTypesRepository) {
        this.meterTypesRepository = meterTypesRepository;
    }

    // TODO
    public Optional<MeterType> findById(int id) {
        return meterTypesRepository.findById(id);
    }

    public List<MeterType> findAll() {
        return meterTypesRepository.findAll();
    }

    public void save(MeterType type) {
        meterTypesRepository.save(type);
    }

    /**
     * Сохраняет новый тип счетчика.
     *
     * @param newType новый тип счетчика
     * @return 1, если тип успешно сохранен; 0, если тип уже существует
     */
    public boolean saveType(String newType) {
        boolean result = false;
        if (!newType.isEmpty()) {
            if (meterTypesRepository.isExists(newType)) {
                ConsoleOut.printLine("Такой тип уже существует");
                result = false;
            } else {
                save(new MeterType(newType));
                ConsoleOut.printLine("Новый тип успешно добавлен");
                result = true;
            }
        }
        return result;
    }
}