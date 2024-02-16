package kg.rakhim.classes.dao;

import kg.rakhim.classes.dao.interfaces.BaseDAO;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.models.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Класс для работы с пользователями в базе данных.
 */
@Component
public class UserDAO implements BaseDAO<User, Integer> {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Получение пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return объект User
     */
    @Override
    public Optional<User> get(int id) {
        User result = jdbcTemplate.query("SELECT * from entities.users where id = ?",
                        new Object[]{id} ,new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
        if (result == null){
            return Optional.empty();
        }
        return Optional.of(result);
    }

    /**
     * Получение списка всех пользователей.
     *
     * @return список объектов User
     */
    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("Select * from entities.users",
                new BeanPropertyRowMapper<>(User.class));
    }

    /**
     * Сохранение пользователя в базе данных.
     *
     * @param user объект User для сохранения
     */
    @Override
    public void save(User user) {
        jdbcTemplate.update("INSERT INTO entities.users(username, password, role) VALUES (?, ?, ?)",
                user.getUsername(), user.getPassword(), role(user.getRole()));
    }

    /**
     * Получение пользователя по его имени.
     *
     * @param username имя пользователя
     * @return объект User
     */
    public Optional<User> getUser(String username){
        String sql = "SELECT * FROM entities.users WHERE username=?";
        User user = jdbcTemplate.query(sql, new Object[]{username} ,new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
        if (user == null){
            return Optional.empty();
        }
        return Optional.of(user);
    }

    /**
     * Получение идентификатора пользователя по его имени.
     *
     * @return идентификатор пользователя
     */
    public Integer userId(String user){
        return jdbcTemplate.queryForObject("SELECT id FROM entities.users WHERE username = ?", Integer.class, user);
    }
    private Integer role(String role){
        String sql = "select role_id from entities.users_role where role=?";
        return jdbcTemplate.queryForObject(sql, Integer.class, role);
    }
}