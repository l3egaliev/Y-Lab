package kg.rakhim.classes.dao;

import kg.rakhim.classes.dao.interfaces.BaseDAO;
import kg.rakhim.classes.dao.migration.ConnectionLoader;
import kg.rakhim.classes.models.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс для взаимодействия с таблицей аудита в базе данных.
 */
@Component
public class AuditDAO implements BaseDAO<Audit, Integer> {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AuditDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Получает аудит по заданному идентификатору.
     *
     * @param id идентификатор аудита
     * @return объект аудита
     */
    @Override
    public Optional<Audit> get(int id) {
        String sql = "select * from entities.audits where id=?";
        Audit audit = jdbcTemplate.queryForObject(sql, Audit.class, id);
        if (audit == null){
            return Optional.empty();
        }
        return Optional.of(audit);
    }

    /**
     * Получает все записи аудита из базы данных.
     *
     * @return список объектов аудита
     */
    @Override
    public List<Audit> getAll() {
        String sql = "select * from entities.audits";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Audit.class));
    }

    /**
     * Сохраняет аудит в базе данных.
     *
     * @param audit объект аудита
     */
    @Override
    public void save(Audit audit) {
        String sql = "INSERT INTO entities.audits(username, action, time) VALUES (?,?,?)";
        jdbcTemplate.update(sql, audit.getUsername(), audit.getAction(), LocalDateTime.now());
    }
}