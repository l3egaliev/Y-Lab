package kg.rakhim.classes.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public class ReadingDTO {

    @Min(value = 1000, message = "Формат значение счетчика - 4 цифр, нап. 1234")
    @Max(value = 9999, message = "Формат значение счетчика - 4 цифр, нап. 1234")
    private int value;
    @NotNull(message = "Тип счетчика не может быть пустым")
    private String meterType;

    public ReadingDTO(int value, String meterType) {
        this.value = value;
        this.meterType = meterType;
    }

    public ReadingDTO() {
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getMeterType() {
        return meterType;
    }

    public void setMeterType(String meterType) {
        this.meterType = meterType;
    }
}
