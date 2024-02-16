package kg.rakhim.classes.repository.impl;

import kg.rakhim.classes.dao.MeterTypesDAO;
import kg.rakhim.classes.models.MeterType;
import kg.rakhim.classes.repository.MeterTypesRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MeterTypeRepositoryImpl implements MeterTypesRepository {
    private final MeterTypesDAO meterTypesDAO;

    public MeterTypeRepositoryImpl(MeterTypesDAO meterTypesDAO) {
        this.meterTypesDAO = meterTypesDAO;
    }

    @Override
    public Optional<MeterType> findById(int id) {
        return meterTypesDAO.get(id);
    }

    @Override
    public List<MeterType> findAll() {
        return meterTypesDAO.getAll();
    }

    @Override
    public void save(MeterType e) {
        meterTypesDAO.save(e);
    }

    public boolean isExists(String type){
        return meterTypesDAO.isExists(type);
    }
    public Integer getTypeId(MeterType type){
        return meterTypesDAO.typeId(type);
    }
}
