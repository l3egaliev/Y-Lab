package kg.rakhim.classes.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TypeDTO {
    @NotNull(message = "Тип счетчика не может быть пустым")
    @Size(min = 3, max = 40, message = "От 3 до 40 символов")
    private String type;
}
