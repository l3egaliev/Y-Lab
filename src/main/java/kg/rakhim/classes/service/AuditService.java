package kg.rakhim.classes.service;

import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.repository.AuditRepository;
import kg.rakhim.classes.repository.impl.AuditRepositoryImpl;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с аудитом, реализующий интерфейс {@link AuditRepository}.
 */
public class AuditService {
    private final AuditRepositoryImpl auditRepository;

    /**
     * Конструктор, принимающий объект доступа к данным аудита.
     *
     * @param auditRepository объект доступа к данным аудита
     */
    public AuditService(AuditRepositoryImpl auditRepository) {
        this.auditRepository = auditRepository;
    }

    /**
     * Поиск аудита по идентификатору.
     *
     * @param id идентификатор аудита
     * @return объект аудита, обернутый в Optional
     */
    public Optional<Audit> findById(int id) {
        return auditRepository.findById(id);
    }

    /**
     * Получение всех записей аудита.
     *
     * @return список объектов аудита
     */
    public List<Audit> findAll() {
        return auditRepository.findAll();
    }

    /**
     * Сохранение записи аудита.
     *
     * @param e объект аудита для сохранения
     */
    public void save(Audit e) {
        auditRepository.save(e);
    }
}