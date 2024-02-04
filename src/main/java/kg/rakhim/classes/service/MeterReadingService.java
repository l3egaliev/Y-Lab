package kg.rakhim.classes.service;

import kg.rakhim.classes.context.ApplicationContext;
import kg.rakhim.classes.dao.MeterReadingDAO;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.repository.MeterReadingRepository;

import java.util.List;
import java.util.Optional;

public class MeterReadingService implements MeterReadingRepository {
    private final MeterReadingDAO dao;

    public MeterReadingService() {
        this.dao = (MeterReadingDAO) ApplicationContext.getContext("meterReadingDAO");
    }

    // TODO
    @Override
    public Optional<MeterReading> findById(int id) {
        return Optional.of(dao.get(id));
    }

    @Override
    public List<MeterReading> findAll() {
        return dao.getAll();
    }

    @Override
    public void save(MeterReading e) {
        dao.save(e);
    }
}
