package kg.rakhim.classes.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import kg.rakhim.classes.dto.TypeDTO;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.service.AdminService;
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
@Api(value = "/admin", tags = "Контроллер для действий админа")
public class AdminController {
    private final MeterTypesService typesService;
    private final AdminService adminService;

    @Autowired
    public AdminController(MeterTypesService typesService, AdminService adminService) {
        this.typesService = typesService;
        this.adminService = adminService;
    }

    @PostMapping("/newType")
    @ApiOperation(
            value = "Добавить новый тип для счетчика",
            httpMethod = "POST",
            produces = "application/json",
            response = ResponseEntity.class
    )
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
    @ApiOperation(
            value = "Получение показаний пользователей из БД",
            httpMethod = "GET",
            produces = "application/json",
            response = ResponseEntity.class
    )
    public ResponseEntity<Map<String, Object>> readings(@RequestParam(value = "month", required = false) Integer month,
                                                        @RequestParam(value = "username", required = false) String username) {
        if ((username == null || username.isEmpty()) && month == null) {
            return actualReadings();
        } else if (username == null || username.isEmpty()) {
            return ResponseEntity.ok(Map.of("response", adminService.readingsOfAllUsersForMonth(month)));
        } else if (month == null) {
            return ResponseEntity.ok(Map.of("response", adminService.historyOfUserReadings(username)));
        } else {
            return ResponseEntity.ok(Map.of("response", adminService.readingsOfOneUserForMonth(username, month)));
        }
    }

    public ResponseEntity<Map<String, Object>> actualReadings() {
        List<MeterReading> res = adminService.actualReadingsOfAllUsers();
        if (res.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(Map.of("response", adminService.actualReadingsOfAllUsers()));
    }
}
