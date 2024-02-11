package kg.rakhim.classes.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ReadingDTO {
    @NotNull(message = "Имя пользователя не может быть пустым")
    private String username;
    @Size(min=4, max=4, message="Формат значение счетчика - 4 цифр нап. 1234")
    private int value;
    @NotNull(message = "Тип счетчика не может быть пустым")
    private String meterType;
}
