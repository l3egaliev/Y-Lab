package kg.rakhim.classes.database;

import kg.rakhim.classes.models.Audit;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AuditStorage {
    private List<Audit> audits = new ArrayList<>();
}
