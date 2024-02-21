package kg.rakhim.classes.context;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
@Setter
@Getter
public class UserDetails {
    private String username;
    private Map<String, String> actions = new HashMap<>();

    public UserDetails(String username) {
        this.username = username;
    }

    public UserDetails() {
    }

    public UserDetails(String username, Map<String, String> actions) {
        this.username = username;
        this.actions = actions;
    }

    public Map<String, String> getAction() {
        return actions;
    }

    public void setAction(Map<String, String> actions) {
        this.actions = actions;
    }
}
