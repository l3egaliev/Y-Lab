package kg.rakhim.classes.dao;

import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.models.MeterType;

import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class MeterReadingDAO implements BaseDAO<MeterReading, Integer>{
    private static Connection connection = ConnectionLoader.getConnection();
    private UserDAO userDAO = new UserDAO();
    private MeterTypesDAO meterTypesDAO = new MeterTypesDAO();

    @Override
    public MeterReading get(int id) {
        PreparedStatement p = null;
        MeterReading meterReading = new MeterReading();
        try{
            p = connection.prepareStatement("select * from entities.meter_readings where id = ?");
            p.setInt(1, id);
            ResultSet r = p.executeQuery();
            while(r.next()){
                readingFromSql(r, meterReading);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return meterReading;
    }

    @Override
    public List<MeterReading> getAll() {
        PreparedStatement p = null;
        List<MeterReading> readings = new ArrayList<>();
        try{
            p = connection.prepareStatement("select * from entities.meter_readings;");
            ResultSet resultSet = p.executeQuery();
            while(resultSet.next()){
                MeterReading meterReading = new MeterReading();
                readingFromSql(resultSet, meterReading);
                readings.add(meterReading);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return readings;
    }

    private void readingFromSql(ResultSet resultSet, MeterReading meterReading) throws SQLException {
        meterReading.setMeterType(meterTypesDAO.get(resultSet.getInt("meter_type")));
        meterReading.setId(resultSet.getInt("id"));
        meterReading.setDate(resultSet.getTimestamp("date_time").toLocalDateTime());
        meterReading.setValue(resultSet.getInt("value"));
        meterReading.setUser(userDAO.get(resultSet.getInt("user_id")));
    }

    @Override
    public void save(MeterReading meterReading) {
        PreparedStatement p;
        String sql = "INSERT INTO entities.meter_readings(meter_type, user_id, value, date_time)" +
                "VALUES (?,?,?,?)";
        try {
            p = connection.prepareStatement(sql);
            p.setInt(1, meterTypesDAO.typeId(meterReading.getMeterType()));
            p.setInt(2, meterReading.getUser().getId());
            p.setInt(3, meterReading.getValue());
            p.setDate(4, new Date(Timestamp.valueOf(LocalDateTime.now()).getTime()));
            p.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    

}
