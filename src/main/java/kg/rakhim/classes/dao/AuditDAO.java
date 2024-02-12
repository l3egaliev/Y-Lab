package kg.rakhim.classes.dao;

import kg.rakhim.classes.context.ApplicationContext;
import kg.rakhim.classes.dao.interfaces.BaseDAO;
import kg.rakhim.classes.dao.migration.ConnectionLoader;
import kg.rakhim.classes.dao.migration.LoadProperties;
import kg.rakhim.classes.models.Audit;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс для взаимодействия с таблицей аудита в базе данных.
 */
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
    public Optional<Audit> get(int id) {
        String sql = "select * from entities.audits where id=?";
        try (PreparedStatement p = connection.prepareStatement(sql)) {
            p.setInt(1, id);
            try (ResultSet resultSet = p.executeQuery()) {
                if (resultSet.next()) {
                    Audit audit = new Audit();
                    audit.setId(resultSet.getInt("id"));
                    audit.setAction(resultSet.getString("action"));
                    audit.setUsername(resultSet.getString("username"));
                    audit.setTime(resultSet.getTimestamp("time"));
                    return Optional.of(audit);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
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
                audit.setUsername(resultSet.getString("username"));
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
        try (PreparedStatement p = connection.prepareStatement("INSERT INTO entities.audits(username, action, time) VALUES (?,?,?)")) {
            p.setString(1, audit.getUsername());
            p.setString(2, audit.getAction());
            p.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            p.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}