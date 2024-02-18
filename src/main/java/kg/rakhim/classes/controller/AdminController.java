package kg.rakhim.classes.controller;

import jakarta.validation.Valid;
import kg.rakhim.classes.dto.TypeDTO;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.service.MeterReadingService;
import kg.rakhim.classes.service.MeterTypesService;
import kg.rakhim.classes.utils.ErrorSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final MeterTypesService typesService;
    private final MeterReadingService mService;

    @Autowired
    public AdminController(MeterTypesService typesService, MeterReadingService mService) {
        this.typesService = typesService;
        this.mService = mService;
    }

    @PostMapping("/newType")
    public ResponseEntity<Map<String, Object>> addType(@RequestBody @Valid TypeDTO dto,
                                                       BindingResult b){
        if (b.hasErrors()){
            List<String> errors = ErrorSender.returnErrorsToClient(b);
            return ResponseEntity.badRequest().body(Map.of("message", errors));
        }
        List<String> err = new ArrayList<>();
        boolean res = typesService.saveType(dto.getType(),err);
        if (!res){
            return ResponseEntity.badRequest().body(Map.of("message", err));
        }
        return ResponseEntity.ok(Map.of("message", "Новый тип добавлен"));
    }

    @GetMapping("/readings")
    public ResponseEntity<List<MeterReading>> readings(@RequestParam(value = "month", required = false) Integer month,
                                                       @RequestParam(value = "username", required = false) String username){
        if ((username==null || username.isEmpty()) && month==null){
            return ResponseEntity.ok(mService.actualReadingsOfAllUsers());
        }else if(username == null || username.isEmpty()){
            return ResponseEntity.ok(mService.readingsOfAllUsersForMonth(month));
        }else if(month == null){
           return ResponseEntity.ok(mService.historyOfUserReadings(username));
        } else {
            return ResponseEntity.ok(mService.readingsOfOneUserForMonth(username, month));
        }
    }
}