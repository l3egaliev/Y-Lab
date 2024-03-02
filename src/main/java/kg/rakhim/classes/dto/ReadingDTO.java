package kg.rakhim.classes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Schema(description = "Объект для подачи показания")
public class ReadingDTO {

    @Min(value = 1000, message = "Формат значение счетчика - 4 цифр, нап. 1234")
    @Max(value = 9999, message = "Формат значение счетчика - 4 цифр, нап. 1234")
    private int value;
    @NotNull(message = "Тип счетчика не может быть пустым")
    private String meterType;
}
