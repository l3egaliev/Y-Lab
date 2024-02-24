package kg.rakhim.classes.dao;

import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.models.MeterType;
import kg.rakhim.classes.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс для взаимодействия с таблицей показаний счетчиков в базе данных.
 */
@Component
public class MeterReadingDAO implements BaseDAO<MeterReading, Integer> {
    private final JdbcTemplate jdbcTemplate;
    private final UserDAO userDAO;
    private final MeterTypesDAO meterTypesDAO;

    @Autowired
    public MeterReadingDAO(JdbcTemplate jdbcTemplate, UserDAO userDAO, MeterTypesDAO meterTypesDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDAO = userDAO;
        this.meterTypesDAO = meterTypesDAO;
    }

    /**
     * Получает показания счетчика по заданному идентификатору.
     *
     * @param id идентификатор показаний счетчика
     * @return объект показаний счетчика
     */
    @Override
    public Optional<MeterReading> get(int id) {
        String sql = "select * from entities.meter_readings where id = ?";
        MeterReading res = jdbcTemplate.query(sql, new Object[]{id},
                new BeanPropertyRowMapper<>(MeterReading.class)).stream().findAny().orElse(null);
        if (res == null) {
            return Optional.empty();
        }
        return Optional.of(res);
    }

    /**
     * Получает все показания счетчиков из базы данных.
     *
     * @return список объектов показаний счетчиков
     */
    @Override
    public List<MeterReading> getAll() {
        String sql = "select id, reading_value from entities.meter_readings";
        List<MeterReading> meterReadings =  jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MeterReading.class));
        for (MeterReading m : meterReadings){
            m.setUser(userByReadingId(m.getId()));
            m.setMeterType(meterTypeByReadingId(m.getId()));
            m.setDateTime(dateByReadingId(m.getId()));
        }
        return meterReadings;
    }
    /**
     * Сохраняет показания счетчика в базе данных.
     *
     * @param meterReading объект показаний счетчика
     */
    @Override
    public void save(MeterReading meterReading) {
        String sql = "INSERT INTO entities.meter_readings(meter_type, user_id, reading_value, date_time) VALUES (?,?,?,?)";
        jdbcTemplate.update(sql, meterTypesDAO.typeId(meterReading.getMeterType()),
                userDAO.userId(meterReading.getUser().getUsername()),
                meterReading.getReadingValue(), Timestamp.valueOf(LocalDateTime.now()));
        System.out.println(meterTypesDAO.typeId(meterReading.getMeterType()));
    }

    public List<MeterReading> getByUser(String username) {
        String sql = "select id, reading_value from entities.meter_readings where user_id=?";
        User user;
        List<MeterReading> res = new ArrayList<>();
        if (userDAO.getUser(username).isPresent()){
            user = userDAO.getUser(username).get();
            List<MeterReading> req = jdbcTemplate.query(sql, new Object[]{user.getId()},
                    new BeanPropertyRowMapper<>(MeterReading.class));
            for (MeterReading m : req){
                m.setMeterType(meterTypeByReadingId(m.getId()));
                m.setUser(userByReadingId(m.getId()));
                m.setDateTime(dateByReadingId(m.getId()));
                res.add(m);
            }
        }

        return res;
    }
    private MeterType meterTypeByReadingId(int reading){
        String sql = "select meter_type from entities.meter_readings where id=?";
        Integer typeId = jdbcTemplate.queryForObject(sql, Integer.class, reading);
        Optional<MeterType> type = meterTypesDAO.get(typeId);
        return type.orElse(null);
    }
    private User userByReadingId(int reading){
        String sql = "select user_id from entities.meter_readings where id=?";
        Integer typeId = jdbcTemplate.queryForObject(sql, Integer.class, reading);
        Optional<User> type = userDAO.get(typeId);
        return type.orElse(null);
    }
    private LocalDateTime dateByReadingId(int reading){
        String sql = "select date_time from entities.meter_readings where id = ?";
        Timestamp date = jdbcTemplate.queryForObject(sql, Timestamp.class, reading);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return localDateTime;
    }
}