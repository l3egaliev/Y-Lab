package kg.rakhim.classes.database;

import kg.rakhim.classes.models.MeterReading;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MeterReadingStorage {
    List<MeterReading> meterReadings = new ArrayList<>();
}
