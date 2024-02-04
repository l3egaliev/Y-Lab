package kg.rakhim.classes.dao;

import kg.rakhim.classes.dao.migration.LoadProperties;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.models.UserRole;
import lombok.Data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class UserDAO implements BaseDAO<User, Integer>{
    private static Connection connection = ConnectionLoader.getConnection();
    @Override
    public User get(int id) {
        String sql = "select * from entities.users where id=?";
        PreparedStatement p = null;
        User user = new User();
        try {
            p = connection.prepareStatement(sql);
            p.setInt(1, id);
            ResultSet resultSet = p.executeQuery();
            while (resultSet.next()){
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(getRole(resultSet.getInt("role")).getRole());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> res = new ArrayList<>();
        PreparedStatement p = null;
        try {
            p = connection.prepareStatement("select * from entities.users");
            ResultSet resultSet = p.executeQuery();
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUsername(resultSet.getString("username"));
                user.setRole(getRole(resultSet.getInt("role")).getRole());
                user.setPassword(resultSet.getString("password"));
                res.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void save(User user) {
        PreparedStatement p = null;
        try{
            p = connection.prepareStatement("INSERT INTO entities.users(username, password, role) VALUES (?,?,?)");
            p.setString(1,user.getUsername());
            p.setString(2, user.getPassword());
            p.setInt(3, 1);
            p.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public User getUser(String username){
        String sql = "select * from entities.users where username=?";
        PreparedStatement p = null;
        User user = new User();
        try {
            p = connection.prepareStatement(sql);
            p.setString(1, username);
            ResultSet r = p.executeQuery();
            while (r.next()){
                user.setId(r.getInt("id"));
                user.setUsername(r.getString("username"));
                user.setPassword(r.getString("password"));
                user.setRole(getRole(r.getInt("role")).getRole());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    private UserRole getRole(int role_id) throws SQLException {
        UserRole userRole = new UserRole();
        PreparedStatement p = connection.prepareStatement("select * from entities.users_role where role_id = ?");
        p.setInt(1,role_id);
        ResultSet resultSet = p.executeQuery();
        while (resultSet.next()){
           userRole.setRole(resultSet.getString("role"));
           userRole.setId(resultSet.getInt("role_id"));
        }
        return userRole;
    }
}
