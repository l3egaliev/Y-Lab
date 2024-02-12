package kg.rakhim.classes.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddReadingDTO {
    @NotNull(message = "Имя админа не может быть пустым")
    @Size(min = 3, message = "Имя админа - от 3 символов")
    private String admin;
    @NotNull(message = "Тип счетчика не может быть пустым")
    @Size(min = 3, max = 40, message = "Тип счетчика - от 3 до 40 символов")
    private String type;
}
