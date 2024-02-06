/**
 * Класс {@code User} представляет объект пользователя системы.
 * <p>
 * Имеет поля для хранения имени пользователя, пароля и роли пользователя.
 * </p>
 * <p>
 * Реализованы геттеры, сеттеры, конструкторы и переопределен метод {@code toString()} для удобного представления информации.
 * </p>
 *
 * @author Рахим Нуралиев
 * @version 1.0
 * @since 2024-01-26
 */
package kg.rakhim.classes.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс {@code User} представляет объект пользователя системы.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String role;

    /**
     * Конструктор, который используется в объекте MeterReading
     */
    public User(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role  = role;
    }

    public User(String username) {
        this.username = username;
    }
    /**
     * Переопределение метода {@code toString()} для удобного представления информации о пользователе.
     *
     * @return строковое представление объекта {@code User}
     */
    @Override
    public String toString() {
        return username;
    }
}
