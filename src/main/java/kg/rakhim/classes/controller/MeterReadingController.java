package kg.rakhim.classes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kg.rakhim.classes.dto.ReadingDTO;
import kg.rakhim.classes.dto.ReadingResponseDTO;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.service.MeterReadingService;
import kg.rakhim.classes.utils.ErrorSender;
import kg.rakhim.classes.utils.ReadingMapper;
import kg.rakhim.classes.utils.ReadingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/readings")
@Tag(name = "Контроллер для действий обычных пользователей")
public class MeterReadingController {
    private final MeterReadingService mService;
    private final ReadingValidator validator;

    @Autowired
    public MeterReadingController(MeterReadingService mService, ReadingValidator validator) {
        this.mService = mService;
        this.validator = validator;
    }

    @GetMapping("/actual")
    @Operation(description = "Посмотреть показания текущего месяца")
    public ResponseEntity<List<ReadingResponseDTO>> actualReadings(){
        if (mService.findActualReadings().isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(mService.findActualReadings());
    }

    @GetMapping()
    @Operation(description = "Посмотреть показания за конкретный месяц или всю историю показаний")
    public ResponseEntity<List<ReadingResponseDTO>> readings(@RequestParam(value = "month", required = false) Integer month){
        if (month == null){
            return ResponseEntity.ok().body(mService.allHistoryOfUser());
        }
        return ResponseEntity.ok(mService.findForMonth(month));
    }

    @PostMapping
    @Operation(description = "Подача показания")
    public ResponseEntity<Map<String, Object>> addReading(@RequestBody @Valid ReadingDTO dto,
                                          BindingResult b) {
        MeterReading meterReading = ReadingMapper.convertFromDto(dto);
        validator.validate(meterReading, b);
        if (b.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("message",
                    ErrorSender.returnErrorsToClient(b)));
        }
        Map<String, Object> res = mService.saveReading(meterReading);
        return ResponseEntity.ok(res);
    }
}
