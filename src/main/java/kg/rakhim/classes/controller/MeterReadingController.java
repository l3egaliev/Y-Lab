package kg.rakhim.classes.controller;

import jakarta.validation.Valid;
import kg.rakhim.classes.dto.ReadingDTO;
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
public class MeterReadingController {
    private final MeterReadingService mService;
    private final ReadingValidator validator;

    @Autowired
    public MeterReadingController(MeterReadingService mService, ReadingValidator validator) {
        this.mService = mService;
        this.validator = validator;
    }

    @GetMapping("/actual")
    public ResponseEntity<List<MeterReading>> actualReadings(){
        if (mService.findActualReadings().isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(mService.findActualReadings());
    }

    @GetMapping()
    public ResponseEntity<List<MeterReading>> readings(@RequestParam(value = "month", required = false) Integer month){
        if (month == null){
            return ResponseEntity.ok().body(mService.allHistoryOfUser());
        }else if (mService.findForMonth(month).isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mService.findForMonth(month));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addReading(@RequestBody @Valid ReadingDTO dto,
                                          BindingResult b) {
        MeterReading meterReading = ReadingMapper.convertFromDto(dto);
        validator.validate(meterReading, b);
        if (b.hasErrors()) {
            return ResponseEntity.badRequest().body(Map.of("message",
                    ErrorSender.returnErrorsToClient(b)));
        }
        boolean res = mService.saveReading(meterReading);
        if (!res){
            return ResponseEntity.badRequest().body(Map.of("message",
                    "Вы в этом месяце уже отправляли показание за - "+meterReading.getMeterType().getType()));
        }
        return ResponseEntity.ok(Map.of("message", "Успешно"));
    }
}
