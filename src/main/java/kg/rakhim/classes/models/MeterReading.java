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

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Класс {@code MeterReading} представляет объект, содержащий информацию о показаниях счетчика.
 */
@Data
@NoArgsConstructor
public class MeterReading {
    private Integer id;
    private User user;
    private int readingValue;
    private MeterType meterType;
    @DateTimeFormat(pattern = "DD.MM.YYYY:hh.mm")
    private LocalDateTime dateTime;

    public MeterReading(User user, int readingValue, MeterType meterType){
        this.user = user;
        this.readingValue = readingValue;
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
                ", значение = " + readingValue +
                ", тип показания = " + meterType +
                ", дата подачи = " + dateTime +
                "}";
    }
}
