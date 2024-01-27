package kg.rakhim.classes.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String username;
    private String password;
    private UserRole role;

    public User(String username){
        this.username = username;
    }

    @Override
    public String toString() {
        return username;
    }
}
