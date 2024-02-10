package kg.rakhim.classes.repository.impl;

import kg.rakhim.classes.dao.AuditDAO;
import kg.rakhim.classes.dao.UserDAO;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.repository.AuditRepository;

import java.util.List;
import java.util.Optional;

public class AuditRepositoryImpl implements AuditRepository {
    private final AuditDAO auditDAO;

    public AuditRepositoryImpl(AuditDAO auditDAO) {
        this.auditDAO = auditDAO;
    }

    @Override
    public Optional<Audit> findById(int id) {
        return auditDAO.get(id);
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
