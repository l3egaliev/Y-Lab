package kg.rakhim.classes.database;

import kg.rakhim.classes.models.MeterType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MeterTypesStorage {
    private List<MeterType> meterTypes = new ArrayList<>();

    /**
     * Конструктор для создания экземпляра класса {@code MeterTypesStorage}.
     * При запуске приложения добавляется 3 типа счетчика
     */
    public MeterTypesStorage(){
        meterTypes.add(new MeterType("горячая вода"));
        meterTypes.add(new MeterType("холодная вода"));
        meterTypes.add(new MeterType("отопление"));
    }
}
