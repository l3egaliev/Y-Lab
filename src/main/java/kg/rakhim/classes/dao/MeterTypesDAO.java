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

public class MeterTypesDAO implements MeterTypesDAOIn {
    @Getter
    @Setter
    private String jdbcUrl;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    private static Connection connection = ConnectionLoader.getConnection();

    public int typeId(MeterType type){
        Integer id = null;
        try{
            PreparedStatement p = connection.prepareStatement("SELECT type_id from entities.meter_types where type = ?");
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

    @Override
    public MeterType get(int id){
        PreparedStatement p = null;
        MeterType meterType = new MeterType();
        try{
            p = connection.prepareStatement("select * from entities.meter_types where type_id = ?");
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

    @Override
    public List<MeterType> getAll() {
        PreparedStatement p = null;
        List<MeterType> types = new ArrayList<>();
        try{
            p = connection.prepareStatement("select * from entities.meter_types;");
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

    @Override
    public boolean isExists(String type){
        try{
            PreparedStatement p = connection.prepareStatement("select * from entities.meter_types where type=?");
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
