package ru.auditable.service;

import org.springframework.stereotype.Service;
import ru.auditable.dao.AuditDAO;
import ru.auditable.model.Audit;

import java.util.List;
import java.util.Optional;

@Service
public class AuditService {
    private final AuditDAO auditDAO;

    /**
     * Конструктор, принимающий объект доступа к данным аудита.
     *
     * @param auditDAO объект доступа к данным аудита
     */
    public AuditService(AuditDAO auditDAO) {
        this.auditDAO = auditDAO;
    }

    /**
     * Поиск аудита по идентификатору.
     *
     * @param id идентификатор аудита
     * @return объект аудита, обернутый в Optional
     */
    public Optional<Audit> findById(int id) {
        return auditDAO.get(id);
    }

    /**
     * Получение всех записей аудита.
     *
     * @return список объектов аудита
     */
    public List<Audit> findAll() {
        return auditDAO.getAll();
    }

    /**
     * Сохранение записи аудита.
     *
     * @param e объект аудита для сохранения
     */
    public void save(Audit e) {
        auditDAO.save(e);
    }
}