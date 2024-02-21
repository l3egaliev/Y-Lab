package kg.rakhim.classes.dao;

import kg.rakhim.classes.dao.interfaces.MeterTypesDAOIn;
import kg.rakhim.classes.models.MeterType;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Класс для работы с типами счетчиков в базе данных.
 */
@Getter
@Component
public class MeterTypesDAO implements MeterTypesDAOIn {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MeterTypesDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Получение идентификатора типа счетчика.
     *
     * @param type объект MeterType
     * @return идентификатор типа счетчика
     */
    public Integer typeId(MeterType type){
        String sql = "SELECT type_id FROM entities.meter_types WHERE type = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, type.getType());
    }

    /**
     * Получение типа счетчика по его идентификатору.
     *
     * @param id идентификатор типа счетчика
     * @return объект MeterType
     */
    @Override
    public Optional<MeterType> get(int id){
        String sql = "SELECT * FROM entities.meter_types WHERE type_id = ?";
        MeterType meterType = jdbcTemplate.query(sql, new Object[]{id}, new BeanPropertyRowMapper<>(MeterType.class))
                .stream().findAny().orElse(null);
        if (meterType == null){
            return Optional.empty();
        }
        return Optional.of(meterType);
    }

    /**
     * Получение списка всех типов счетчиков.
     *
     * @return список объектов MeterType
     */
    @Override
    public List<MeterType> getAll() {
       String sql = "SELECT * FROM entities.meter_types";
       return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MeterType.class));
    }

    /**
     * Сохранение типа счетчика в базе данных.
     *
     * @param type объект MeterType для сохранения
     */
    @Override
    public void save(MeterType type) {
        String sql = "INSERT INTO entities.meter_types(type) VALUES(?)";
        jdbcTemplate.update(sql, type.getType());
    }

    /**
     * Проверка существования типа счетчика в базе данных.
     *
     * @param type тип счетчика
     * @return true, если тип счетчика существует; в противном случае - false
     */
    @Override
    public boolean isExists(String type){
       String sql = "SELECT * FROM entities.meter_types WHERE type=?";
        MeterType meterType = jdbcTemplate.query(sql, new Object[]{type}, new BeanPropertyRowMapper<>(MeterType.class))
                .stream().findAny().orElse(null);
        return meterType != null;
    }
}