package kg.rakhim.classes.dao;

import kg.rakhim.classes.context.ApplicationContext;
import kg.rakhim.classes.dao.interfaces.BaseDAO;
import kg.rakhim.classes.dao.migration.ConnectionLoader;
import kg.rakhim.classes.dao.migration.LoadProperties;
import kg.rakhim.classes.models.MeterReading;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс для взаимодействия с таблицей показаний счетчиков в базе данных.
 */
@NoArgsConstructor
public class MeterReadingDAO implements BaseDAO<MeterReading, Integer> {
    @Getter
    @Setter
    private String jdbcUrl;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;

    private final Connection connection = ConnectionLoader.getConnection();
    private final UserDAO userDAO = new UserDAO();
    private final MeterTypesDAO meterTypesDAO = new MeterTypesDAO();
    /**
     * Получает показания счетчика по заданному идентификатору.
     *
     * @param id идентификатор показаний счетчика
     * @return объект показаний счетчика
     */
    @Override
    public Optional<MeterReading> get(int id) {
        String sql = "select * from entities.meter_readings where id = ?";
        try (PreparedStatement p = connection.prepareStatement(sql)) {
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()) {
                MeterReading meterReading = new MeterReading();
                while (r.next()) {
                    readingFromSql(r, meterReading);
                }
                return Optional.of(meterReading);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Получает все показания счетчиков из базы данных.
     *
     * @return список объектов показаний счетчиков
     */
    @Override
    public List<MeterReading> getAll() {
        List<MeterReading> readings = new ArrayList<>();
        try (PreparedStatement p = connection.prepareStatement("select * from entities.meter_readings");
             ResultSet resultSet = p.executeQuery()) {
            while (resultSet.next()) {
                MeterReading meterReading = new MeterReading();
                readingFromSql(resultSet, meterReading);
                readings.add(meterReading);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return readings;
    }

    /**
     * Сохраняет показания счетчика в базе данных.
     *
     * @param meterReading объект показаний счетчика
     */
    @Override
    public void save(MeterReading meterReading) {
        String sql = "INSERT INTO entities.meter_readings(meter_type, user_id, value, date_time) VALUES (?,?,?,?)";
        try (PreparedStatement p = connection.prepareStatement(sql)) {
            p.setInt(1, meterTypesDAO.typeId(meterReading.getMeterType()));
            p.setInt(2, userDAO.userId(meterReading.getUser().getUsername()));
            p.setInt(3, meterReading.getValue());
            p.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Заполняет объект показаний счетчика данными из ResultSet.
     *
     * @param resultSet    ResultSet с данными из базы данных
     * @param meterReading объект показаний счетчика
     * @throws SQLException если возникает ошибка при доступе к данным ResultSet
     */
    private void readingFromSql(ResultSet resultSet, MeterReading meterReading) throws SQLException {
        meterReading.setMeterType(meterTypesDAO.get(resultSet.getInt("meter_type")).get());
        meterReading.setId(resultSet.getInt("id"));
        meterReading.setDate(resultSet.getTimestamp("date_time").toLocalDateTime());
        meterReading.setValue(resultSet.getInt("value"));
        meterReading.setUser(userDAO.get(resultSet.getInt("user_id")).get());
    }
}