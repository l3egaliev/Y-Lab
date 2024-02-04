package kg.rakhim.classes.service;

import kg.rakhim.classes.dao.AuditDAO;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.repository.AuditRepository;

import java.util.List;
import java.util.Optional;

public class AuditService implements AuditRepository {
    private final AuditDAO auditDAO;

    public AuditService() {
        this.auditDAO = new AuditDAO();
    }

    // TODO
    @Override
    public Optional<Audit> findById(int id) {
            return Optional.of(auditDAO.get(id));
    }

    @Override
    public List<Audit> findAll() {
        return auditDAO.getAll();
    }

    @Override
    public void save(Audit e) {
        auditDAO.save(e);
    }
}
