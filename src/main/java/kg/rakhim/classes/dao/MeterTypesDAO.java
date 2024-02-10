package kg.rakhim.classes.dao;

import kg.rakhim.classes.dao.interfaces.BaseDAO;
import kg.rakhim.classes.dao.interfaces.MeterTypesDAOIn;
import kg.rakhim.classes.models.MeterType;
import lombok.Getter;
import lombok.Setter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с типами счетчиков в базе данных.
 */
public class MeterTypesDAO implements MeterTypesDAOIn {

    /**
     * Соединение с базой данных.
     */
    private static final Connection connection = ConnectionLoader.getConnection();

    @Getter
    @Setter
    private String jdbcUrl;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;

    /**
     * Получение идентификатора типа счетчика.
     *
     * @param type объект MeterType
     * @return идентификатор типа счетчика
     */
    public int typeId(MeterType type){
        Integer id = null;
        try{
            PreparedStatement p = connection.prepareStatement("SELECT type_id FROM entities.meter_types WHERE type = ?");
            p.setString(1, type.getType());
            ResultSet resultSet = p.executeQuery();
            while (resultSet.next()){
                id = resultSet.getInt("type_id");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Получение типа счетчика по его идентификатору.
     *
     * @param id идентификатор типа счетчика
     * @return объект MeterType
     */
    @Override
    public MeterType get(int id){
        MeterType meterType = new MeterType();
        try{
            PreparedStatement p = connection.prepareStatement("SELECT * FROM entities.meter_types WHERE type_id = ?");
            p.setInt(1, id);
            ResultSet resultSet = p.executeQuery();
            while (resultSet.next()){
                meterType.setType(resultSet.getString("type"));
                meterType.setId(resultSet.getInt("type_id"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return meterType;
    }

    /**
     * Получение списка всех типов счетчиков.
     *
     * @return список объектов MeterType
     */
    @Override
    public List<MeterType> getAll() {
        List<MeterType> types = new ArrayList<>();
        try{
            PreparedStatement p = connection.prepareStatement("SELECT * FROM entities.meter_types");
            ResultSet resultSet = p.executeQuery();
            while(resultSet.next()){
                MeterType type = new MeterType();
                type.setId(resultSet.getInt("type_id"));
                type.setType(resultSet.getString("type"));
                types.add(type);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return types;
    }

    /**
     * Сохранение типа счетчика в базе данных.
     *
     * @param type объект MeterType для сохранения
     */
    @Override
    public void save(MeterType type) {
        try{
            PreparedStatement p = connection.prepareStatement("INSERT INTO entities.meter_types(type) VALUES(?)");
            p.setString(1, type.getType());
            p.executeUpdate();
        }catch (SQLException s){
            s.printStackTrace();
        }
    }

    /**
     * Проверка существования типа счетчика в базе данных.
     *
     * @param type тип счетчика
     * @return true, если тип счетчика существует; в противном случае - false
     */
    @Override
    public boolean isExists(String type){
        try{
            PreparedStatement p = connection.prepareStatement("SELECT * FROM entities.meter_types WHERE type=?");
            p.setString(1,type);
            ResultSet resultSet = p.executeQuery();
            while(resultSet.next()){
                if (resultSet.getString("type") != null){
                    return true;
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}