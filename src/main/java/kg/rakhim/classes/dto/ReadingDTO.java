package kg.rakhim.classes.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReadingDTO {
    @NotNull(message = "Имя пользователя не может быть пустым")
    private String username;
    @Size(min=4, max=4, message="Формат значение счетчика - 4 цифр нап. 1234")
    private int value;
    @NotNull(message = "Тип счетчика не может быть пустым")
    private String meterType;

    public ReadingDTO(String username, int value, String meterType) {
        this.username = username;
        this.value = value;
        this.meterType = meterType;
    }

    public ReadingDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
