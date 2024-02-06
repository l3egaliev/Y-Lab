package kg.rakhim.classes.dao;

import kg.rakhim.classes.context.ApplicationContext;
import kg.rakhim.classes.dao.interfaces.BaseDAO;
import kg.rakhim.classes.models.Audit;
import lombok.Data;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для взаимодействия с таблицей аудита в базе данных.
 */
@Data
public class AuditDAO implements BaseDAO<Audit, Integer> {

    private final Connection connection = ConnectionLoader.getConnection();
    private final UserDAO userDAO = (UserDAO) ApplicationContext.getContext("userDAO");

    /**
     * Получает аудит по заданному идентификатору.
     *
     * @param id идентификатор аудита
     * @return объект аудита
     */
    @Override
    public Audit get(int id) {
        String sql = "select * from entities.audits where id=?";
        try (PreparedStatement p = connection.prepareStatement(sql)) {
            p.setInt(1, id);
            try (ResultSet resultSet = p.executeQuery()) {
                if (resultSet.next()) {
                    Audit audit = new Audit();
                    audit.setId(resultSet.getInt("id"));
                    audit.setAction(resultSet.getString("action"));
                    audit.setUsername(username(resultSet.getInt("user_id")));
                    audit.setTime(resultSet.getTimestamp("time"));
                    return audit;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Получает все записи аудита из базы данных.
     *
     * @return список объектов аудита
     */
    @Override
    public List<Audit> getAll() {
        List<Audit> res = new ArrayList<>();
        try (PreparedStatement p = connection.prepareStatement("select * from entities.audits");
             ResultSet resultSet = p.executeQuery()) {
            while (resultSet.next()) {
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

    /**
     * Сохраняет аудит в базе данных.
     *
     * @param audit объект аудита
     */
    @Override
    public void save(Audit audit) {
        try (PreparedStatement p = connection.prepareStatement("INSERT INTO entities.audits(user_id, action, time) VALUES (?,?,?)")) {
            p.setInt(1, userDAO.getUser(audit.getUsername()).getId());
            p.setString(2, audit.getAction());
            p.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Получает имя пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return имя пользователя
     */
    private String username(int id) {
        return userDAO.get(id).getUsername();
    }
}