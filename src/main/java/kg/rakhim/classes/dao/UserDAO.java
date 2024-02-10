package kg.rakhim.classes.dao;

import kg.rakhim.classes.dao.interfaces.BaseDAO;
import kg.rakhim.classes.dao.migration.ConnectionLoader;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.models.UserRole;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс для работы с пользователями в базе данных.
 */
@Data
public class UserDAO implements BaseDAO<User, Integer> {

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
     * Получение пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return объект User
     */
    @Override
    public Optional<User> get(int id) {
        String sql = "SELECT * FROM entities.users WHERE id=?";
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
        return Optional.of(user);
    }

    /**
     * Получение списка всех пользователей.
     *
     * @return список объектов User
     */
    @Override
    public List<User> getAll() {
        List<User> res = new ArrayList<>();
        PreparedStatement p = null;
        try {
            p = connection.prepareStatement("SELECT * FROM entities.users");
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

    /**
     * Сохранение пользователя в базе данных.
     *
     * @param user объект User для сохранения
     */
    @Override
    public void save(User user) {
        PreparedStatement p = null;
        try{
            p = connection.prepareStatement("INSERT INTO entities.users(username, password, role) VALUES (?,?,?)");
            p.setString(1,user.getUsername());
            p.setString(2, user.getPassword());
            if (user.getRole().equals("ADMIN")){
                p.setInt(3, 1);
            }else{
                p.setInt(3, 2);
            }
            p.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Получение пользователя по его имени.
     *
     * @param username имя пользователя
     * @return объект User
     */
    public Optional<User> getUser(String username){
        String sql = "SELECT * FROM entities.users WHERE username=?";
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
        return Optional.of(user);
    }

    /**
     * Получение идентификатора пользователя по его имени.
     *
     * @param username имя пользователя
     * @return идентификатор пользователя
     */
    public Integer userId(String username){
        try{
            PreparedStatement p = connection.prepareStatement("SELECT id FROM entities.users WHERE username = ?");
            p.setString(1, username);
            ResultSet resultSet = p.executeQuery();
            while(resultSet.next()){
                return resultSet.getInt("id");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Получение роли пользователя по её идентификатору.
     *
     * @param role_id идентификатор роли
     * @return объект UserRole
     * @throws SQLException если возникает ошибка при выполнении SQL-запроса
     */
    private UserRole getRole(int role_id) throws SQLException {
        UserRole userRole = new UserRole();
        PreparedStatement p = connection.prepareStatement("SELECT * FROM entities.users_role WHERE role_id = ?");
        p.setInt(1,role_id);
        ResultSet resultSet = p.executeQuery();
        while (resultSet.next()){
            userRole.setRole(resultSet.getString("role"));
            userRole.setId(resultSet.getInt("role_id"));
        }
        return userRole;
    }
}