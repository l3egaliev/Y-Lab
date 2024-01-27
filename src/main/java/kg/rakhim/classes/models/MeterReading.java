package kg.rakhim.classes.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class MeterReading {
    private User user;
    private int value;
    private String meterType;
    private LocalDate date;

    public MeterReading(String t){
        this.meterType = t;
        this.user = new User("default");
        this.date = LocalDate.now();
        this.value = 0;
    }

    public MeterReading(){}

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
