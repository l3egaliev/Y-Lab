package kg.rakhim.classes.utils;

import kg.rakhim.classes.dto.ReadingDTO;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.models.MeterType;
import kg.rakhim.classes.models.User;

public class ReadingMapper {
    public static MeterReading convertFromDto(ReadingDTO dto){
        MeterReading meterReading = new MeterReading();
        meterReading.setValue(dto.getValue());
        meterReading.setUser(new User(dto.getUsername()));
        meterReading.setMeterType(new MeterType(dto.getMeterType()));
        return meterReading;
    }
}
