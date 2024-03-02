package kg.rakhim.classes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Объект для добавления нового счетчика")
public class TypeDTO {
    @NotNull(message = "Тип счетчика не может быть пустым")
    @Size(min = 3, max = 40, message = "От 3 до 40 символов")
    private String type;
}
