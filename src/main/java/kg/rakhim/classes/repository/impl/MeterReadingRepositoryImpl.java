package kg.rakhim.classes.repository.impl;

import kg.rakhim.classes.dao.MeterReadingDAO;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.repository.MeterReadingRepository;

import java.util.List;
import java.util.Optional;

public class MeterReadingRepositoryImpl implements MeterReadingRepository {
    private final MeterReadingDAO meterReadingDAO;

    public MeterReadingRepositoryImpl(MeterReadingDAO meterReadingDAO) {
        this.meterReadingDAO = meterReadingDAO;
    }

    @Override
    public Optional<MeterReading> findById(int id) {
        return meterReadingDAO.get(id);
    }

    @Override
    public List<MeterReading> findAll() {
        return meterReadingDAO.getAll();
    }

    @Override
    public void save(MeterReading e) {
        meterReadingDAO.save(e);
    }
}