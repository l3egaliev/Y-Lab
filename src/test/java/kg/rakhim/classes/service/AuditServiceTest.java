//package kg.rakhim.classes.service;
//
//import kg.rakhim.classes.dao.AuditDAO;
//import kg.rakhim.classes.models.Audit;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//
//public class AuditServiceTest {
//
//    @Test
//    public void testFindById() {
//        int auditId = 1;
//        Audit expectedAudit = new Audit(auditId, "Test");
//
//        AuditDAO mockAuditDAO = mock(AuditDAO.class);
//        when(mockAuditDAO.get(auditId)).thenReturn(Optional.of(expectedAudit));
//
//        AuditService auditService = new AuditService(mockAuditDAO);
//
//        Optional<Audit> result = auditService.findById(auditId);
//
//        assertThat(result).isPresent().contains(expectedAudit);
//        verify(mockAuditDAO, times(1)).get(auditId);
//    }
//
//    @Test
//    public void testFindAll() {
//        List<Audit> expectedAudits = new ArrayList<>();
//        expectedAudits.add(new Audit(1, "Test1"));
//        expectedAudits.add(new Audit(2, "Test2"));
//
//        AuditDAO mockAuditDAO = mock(AuditDAO.class);
//        when(mockAuditDAO.getAll()).thenReturn(expectedAudits);
//
//        AuditService auditService = new AuditService(mockAuditDAO);
//
//        List<Audit> result = auditService.findAll();
//
//        assertThat(result).containsExactlyElementsOf(expectedAudits);
//        verify(mockAuditDAO, times(1)).getAll();
//    }
//
//    @Test
//    public void testSave() {
//        Audit auditToSave = new Audit(1, "Test");
//        AuditDAO mockAuditDAO = mock(AuditDAO.class);
//        AuditService auditService = new AuditService(mockAuditDAO);
//        auditService.save(auditToSave);
//        verify(mockAuditDAO, times(1)).save(auditToSave);
//    }
//}
