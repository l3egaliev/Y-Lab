/**
 * Класс {@code MeterReading} представляет собой объект, содержащий информацию о показаниях счетчика.
 * <p>
 * Имеет поля для хранения пользователя, значения показаний, типа счетчика и даты подачи показаний.
 * </p>
 * <p>
 * Реализованы геттеры, сеттеры и переопределен метод {@code toString()} для удобного представления информации.
 * </p>
 *
 * @author Рахим Нуралиев
 * @version 1.0
 * @since 2024-01-26
 * @see User
 */
package kg.rakhim.classes.models;

import kg.rakhim.classes.dao.MeterTypesDAO;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Класс {@code MeterReading} представляет объект, содержащий информацию о показаниях счетчика.
 */
@Getter
@Setter
@NoArgsConstructor
public class MeterReading {
    private Integer id;
    private User user;
    private int value;
    private MeterType meterType;
    private LocalDateTime date;

    public MeterReading(User user, int value, MeterType meterType){
        this.user = user;
        this.value = value;
        this.meterType = meterType;
    }
    /**
     * Переопределение метода {@code toString()} для удобного представления информации о показаниях счетчика.
     *
     * @return строковое представление объекта {@code MeterReading}
     */
    @Override
    public String toString() {
        return "{" +
                "Пользователь = " + user +
                ", значение = " + value +
                ", тип показания = " + meterType +
                ", дата подачи = " + date +
                "}";
    }
}
