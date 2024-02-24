package kg.rakhim.classes.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kg.rakhim.classes.dto.ReadingResponseDTO;
import kg.rakhim.classes.dto.TypeDTO;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.service.AdminService;
import kg.rakhim.classes.service.MeterTypesService;
import kg.rakhim.classes.utils.ErrorSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@Tag(name = "Контроллер для действий админа")
public class AdminController {
    private final MeterTypesService typesService;
    private final AdminService adminService;

    @Autowired
    public AdminController(MeterTypesService typesService, AdminService adminService) {
        this.typesService = typesService;
        this.adminService = adminService;
    }

    @PostMapping("/newType")
    @Operation(description = "Добавить новый тип для счетчика")
    public ResponseEntity<Map<String, Object>> addType(@RequestBody @Valid TypeDTO dto,
                                                       BindingResult b) {
        if (b.hasErrors()) {
            List<String> errors = ErrorSender.returnErrorsToClient(b);
            return ResponseEntity.badRequest().body(Map.of("message", errors));
        }
        List<String> err = new ArrayList<>();
        boolean res = typesService.saveType(dto.getType(), err);
        if (!res) {
            return ResponseEntity.badRequest().body(Map.of("message", err));
        }
        return ResponseEntity.ok(Map.of("message", "Новый тип добавлен"));
    }

    @GetMapping("/readings")
    @Operation(description = "Получение показаний пользователей из БД")
    public ResponseEntity<Map<String, Object>> readings(@RequestParam(value = "month", required = false) Integer month,
                                                        @RequestParam(value = "username", required = false) String username) {
        if ((username == null || username.isEmpty()) && month == null) {
            return actualReadings();
        } else if (username == null || username.isEmpty()) {
            return readingsForMonth(month);
        } else if (month == null) {
            return userHistoryReadings(username);
        } else {
            return userReadingsForMonth(username, month);
        }
    }

    public ResponseEntity<Map<String, Object>> actualReadings() {
        List<ReadingResponseDTO> res = adminService.actualReadingsOfAllUsers();
        if (res.isEmpty()) {
            return new ResponseEntity<>(Map.of("response", Collections.emptyList()), HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(Map.of("Актуальные показания пользователей", adminService.actualReadingsOfAllUsers()));
    }

    public ResponseEntity<Map<String, Object>> readingsForMonth(Integer month){
        List<ReadingResponseDTO> res = adminService.readingsOfAllUsersForMonth(month);
        if (res.isEmpty()){
            return new ResponseEntity<>(Map.of("response", Collections.emptyList()), HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(Map.of("Показания всех пользователей за "+month+" - месяц", res));
    }

    public ResponseEntity<Map<String, Object>> userHistoryReadings(String username){
        List<ReadingResponseDTO> res = adminService.historyOfUserReadings(username);
        if (res.isEmpty()){
            return new ResponseEntity<>(Map.of("response", Collections.emptyList()), HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(Map.of("Все показания "+username+"'a", res));
    }

    public ResponseEntity<Map<String, Object>> userReadingsForMonth(String username, Integer month){
        List<ReadingResponseDTO> res = adminService.readingsOfOneUserForMonth(username, month);
        if (res.isEmpty()){
            return new ResponseEntity<>(Map.of("response", Collections.emptyList()), HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(Map.of("Все показания "+username+"'a за "+month+" - месяц", res));
    }
}