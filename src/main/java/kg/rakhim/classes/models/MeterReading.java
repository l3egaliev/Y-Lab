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

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Класс {@code MeterReading} представляет объект, содержащий информацию о показаниях счетчика.
 */
@Getter
@Setter
public class MeterReading {
    private User user;
    private int value;
    private MeterType meterType;
    private LocalDate date;


    public MeterReading(){}

    /**
     * Переопределение метода {@code toString()} для удобного представления информации о показаниях счетчика.
     *
     * @return строковое представление объекта {@code MeterReading}
     */
    @Override
    public String toString() {
        return "Показание{" +
                "Пользователь = " + user +
                ", значение = " + value +
                ", тип показания = " + meterType +
                ", дата подачи = " + date +
                "}";
    }
}
