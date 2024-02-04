package kg.rakhim.classes.service;

import kg.rakhim.classes.dao.MeterTypesStorage;
import kg.rakhim.classes.models.MeterType;
import kg.rakhim.classes.repository.MeterTypesRepository;

import java.util.List;
import java.util.Optional;

public class MeterTypesService implements MeterTypesRepository {
    private final MeterTypesStorage meterTypesStorage;

    public MeterTypesService(MeterTypesStorage meterTypesStorage) {
        this.meterTypesStorage = meterTypesStorage;
    }

    // TODO
    @Override
    public Optional<MeterType> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<MeterType> findAll() {
        return meterTypesStorage.getMeterTypes();
    }

    @Override
    public void save(MeterType e) {
        meterTypesStorage.getMeterTypes().add(e);
    }
}
