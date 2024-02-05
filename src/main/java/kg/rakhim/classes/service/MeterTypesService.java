package kg.rakhim.classes.service;

import kg.rakhim.classes.context.ApplicationContext;
import kg.rakhim.classes.dao.MeterTypesDAO;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.models.MeterType;
import kg.rakhim.classes.out.ConsoleOut;
import kg.rakhim.classes.repository.MeterTypesRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MeterTypesService implements MeterTypesRepository {
    private final MeterTypesDAO meterTypesDAO;
    public MeterTypesService() {
        this.meterTypesDAO = (MeterTypesDAO) ApplicationContext.getContext("meterTypeDAO");
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
