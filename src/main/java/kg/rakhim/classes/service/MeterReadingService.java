package kg.rakhim.classes.service;

import kg.rakhim.classes.database.MeterReadingStorage;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.repository.MeterReadingRepository;

import java.util.List;
import java.util.Optional;

public class MeterReadingService implements MeterReadingRepository {
    private final MeterReadingStorage meterReadingStorage;

    public MeterReadingService(MeterReadingStorage meterReadingStorage) {
        this.meterReadingStorage = meterReadingStorage;
    }

    // TODO
    @Override
    public Optional<MeterReading> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<MeterReading> findAll() {
        return meterReadingStorage.getMeterReadings();
    }

    @Override
    public void save(MeterReading e) {
        meterReadingStorage.getMeterReadings().add(e);
    }
}
