package kg.rakhim.classes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Объект для регистрации/авторизации")
public class AuthorizeDTO {
    @NotNull
    @Size(min = 3, max = 50, message = "В имени пользователя должно быть от 3 до 50 символов")
    private String username;
    @NotNull
    @Size(min = 8, max = 40, message = "Пароль должен быть от 8 до 40 символов")
    private String password;
}
