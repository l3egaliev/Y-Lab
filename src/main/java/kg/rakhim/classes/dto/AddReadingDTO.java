package kg.rakhim.classes.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AddReadingDTO {
    @NotNull(message = "Имя админа не может быть пустым")
    @Size(min = 3, message = "Имя админа - от 3 символов")
    private String admin;
    @NotNull(message = "Тип счетчика не может быть пустым")
    @Size(min = 3, max = 40, message = "Тип счетчика - от 3 до 40 символов")
    private String type;

    public AddReadingDTO(String admin, String type) {
        this.admin = admin;
        this.type = type;
    }

    public AddReadingDTO() {
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
