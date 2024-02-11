package kg.rakhim.classes.service;

import kg.rakhim.classes.dao.MeterReadingDAO;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.models.MeterType;
import kg.rakhim.classes.out.ConsoleOut;
import kg.rakhim.classes.repository.MeterReadingRepository;
import kg.rakhim.classes.repository.impl.MeterReadingRepositoryImpl;

import java.time.LocalDateTime;
import java.util.*;

//import static kg.rakhim.classes.in.ConsoleIn.commandList;

/**
 * Сервис для работы с показаниями счетчиков.
 */
public class MeterReadingService {
    private final MeterReadingRepositoryImpl meterReadingRepository;
    /**
     * Создает экземпляр класса MeterReadingService с указанным DAO и сервисом типов счетчиков.
     *
     * @param meterReadingRepository     Repository для работы с данными о показаниях счетчиков
     */
    public MeterReadingService(MeterReadingRepositoryImpl meterReadingRepository) {
        this.meterReadingRepository = meterReadingRepository;
    }
    public Optional<MeterReading> findById(int id) {
        return meterReadingRepository.findById(id);
    }

    public List<MeterReading> findAll() {
        return meterReadingRepository.findAll();
    }

    public void save(MeterReading e) {
        meterReadingRepository.save(e);
    }
}