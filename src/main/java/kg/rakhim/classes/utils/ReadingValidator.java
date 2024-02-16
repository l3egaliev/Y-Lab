package kg.rakhim.classes.utils;

import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.service.MeterTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ReadingValidator implements Validator {
    private final MeterTypesService typesService;

    @Autowired
    public ReadingValidator(MeterTypesService typesService) {
        this.typesService = typesService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(MeterReading.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeterReading reading = (MeterReading) target;
        if (!typesService.isExistsType(reading.getMeterType().getType())){
            errors.rejectValue("meterType", "", "Неизвестный тип счетчика");
        }
    }
}
