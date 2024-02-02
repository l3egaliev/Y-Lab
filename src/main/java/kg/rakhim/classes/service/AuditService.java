package kg.rakhim.classes.service;

import kg.rakhim.classes.database.AuditStorage;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.repository.AuditRepository;

import java.util.List;
import java.util.Optional;

public class AuditService implements AuditRepository {
    private final AuditStorage auditStorage;

    public AuditService(AuditStorage auditStorage) {
        this.auditStorage = auditStorage;
    }

    // TODO
    @Override
    public Optional<Audit> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<Audit> findAll() {
        return auditStorage.getAudits();
    }

    @Override
    public void save(Audit e) {
        auditStorage.getAudits().add(e);
    }
}
