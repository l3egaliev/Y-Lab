package kg.rakhim.classes.models;

import lombok.Data;

import javax.lang.model.element.NestingKind;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserRole {
    private int id;
    private String role;
}
