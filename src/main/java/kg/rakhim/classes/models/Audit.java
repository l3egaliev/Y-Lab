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
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Класс {@code Audit} представляет аудитовую запись о действиях пользователей в системе.
 */
@Setter
@Getter
@AllArgsConstructor
public class Audit {
    private String username;
    private String action;
    private LocalDateTime time;

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
