/**
 * Класс {@code Audit} представляет аудитовую запись о действиях пользователей в системе.
 * <p>
 * Имеет поля для хранения имени пользователя, выполненного действия и времени его выполнения.
 * </p>
 * <p>
 * Реализованы геттеры, сеттеры, конструктор и переопределен метод {@code toString()} для удобного представления информации.
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

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Класс {@code Audit} представляет аудитовую запись о действиях пользователей в системе.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Audit {
    private int id;
    private String username;
    private String action;
    private Timestamp time;

    public Audit(String username, String action, LocalDateTime now) {
        this.username = username;
        this.action = action;
        this.time = Timestamp.valueOf(now);
    }

    /**
     * Переопределение метода {@code toString()} для удобного представления информации об аудитовой записи.
     *
     * @return строковое представление объекта {@code Audit}
     */
    @Override
    public String toString() {
        return "Audit{" +
                "username = '" + username + '\'' +
                ", action = '" + action + '\'' +
                ", time = " + time +
                '}';
    }
}
