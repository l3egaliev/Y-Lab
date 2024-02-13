package kg.rakhim.classes.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AuthorizeDTO {
    @NotNull
    @Size(min = 3, max = 50, message = "В имени пользователя должно быть от 3 до 50 символов")
    private String username;
    @NotNull
    @Size(min = 8, max = 40, message = "Пароль должен быть от 8 до 40 символов")
    private String password;

    public AuthorizeDTO() {
    }

    public AuthorizeDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
