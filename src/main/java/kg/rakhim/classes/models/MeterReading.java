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

import java.time.LocalDateTime;

/**
 * Класс {@code MeterReading} представляет объект, содержащий информацию о показаниях счетчика.
 */
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

    public MeterReading() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public MeterType getMeterType() {
        return meterType;
    }

    public void setMeterType(MeterType meterType) {
        this.meterType = meterType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
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
