package kg.rakhim.classes.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class Audit {
    private String username;
    private String action;
    private LocalDateTime time;


    @Override
    public String toString() {
        return "Audit{" +
                "username = '" + username + '\'' +
                ", action = '" + action + '\'' +
                ", time = " + time +
                '}';
    }
}
