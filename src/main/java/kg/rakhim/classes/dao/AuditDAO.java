package kg.rakhim.classes.dao;

import kg.rakhim.classes.context.ApplicationContext;
import kg.rakhim.classes.dao.migration.LoadProperties;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.service.UserService;
import lombok.Data;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class AuditDAO implements BaseDAO<Audit, Integer>{
    private Connection connection = ConnectionLoader.getConnection();
    private UserDAO userDAO = (UserDAO) ApplicationContext.getContext("userDAO");

    @Override
    public Audit get(int id) {
        String sql = "select * from entities.audits where id=?";
        PreparedStatement p = null;
        Audit audit = new Audit();
        try {
            p = connection.prepareStatement(sql);
            p.setInt(1, id);
            ResultSet resultSet = p.executeQuery();
            while (resultSet.next()){
                audit.setId(resultSet.getInt("id"));
                audit.setAction(resultSet.getString("action"));
                audit.setUsername(username(resultSet.getInt("user_id")));
                audit.setTime(resultSet.getTimestamp("time"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return audit;
    }

    @Override
    public List<Audit> getAll() {
        List<Audit> res = new ArrayList<>();
        PreparedStatement p = null;
        try {
            p = connection.prepareStatement("select * from entities.audits");
            ResultSet resultSet = p.executeQuery();
            while (resultSet.next()){
                Audit audit = new Audit();
                audit.setId(resultSet.getInt("id"));
                audit.setAction(resultSet.getString("action"));
                audit.setUsername(username(resultSet.getInt("user_id")));
                audit.setTime(resultSet.getTimestamp("time"));
                res.add(audit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void save(Audit audit){
        PreparedStatement p = null;
        try{
            p = connection.prepareStatement("INSERT INTO entities.audits(user_id, action, time) VALUES (?,?,?)");
            p.setInt(1,userDAO.getUser(audit.getUsername()).getId());
            p.setString(2, audit.getAction());
            p.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            p.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private String username(int id){
        return userDAO.get(id).getUsername();
    }
}
