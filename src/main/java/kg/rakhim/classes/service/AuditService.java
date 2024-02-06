package kg.rakhim.classes.service;

import kg.rakhim.classes.dao.AuditDAO;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.repository.AuditRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с аудитом, реализующий интерфейс {@link AuditRepository}.
 */
public class AuditService implements AuditRepository {
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
    @Override
    public Optional<Audit> findById(int id) {
        return Optional.of(auditDAO.get(id));
    }

    /**
     * Получение всех записей аудита.
     *
     * @return список объектов аудита
     */
    @Override
    public List<Audit> findAll() {
        return auditDAO.getAll();
    }

    /**
     * Сохранение записи аудита.
     *
     * @param e объект аудита для сохранения
     */
    @Override
    public void save(Audit e) {
        auditDAO.save(e);
    }
}