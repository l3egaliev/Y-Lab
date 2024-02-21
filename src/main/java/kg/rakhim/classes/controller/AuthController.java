package kg.rakhim.classes.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.validation.Valid;
import kg.rakhim.classes.dto.AuthorizeDTO;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.service.RegisterService;
import kg.rakhim.classes.utils.ErrorSender;
import kg.rakhim.classes.utils.MapToUser;
import kg.rakhim.classes.utils.UserValidator;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tags(value = {@Tag(name = "Контроллер для Авторизации/Регистрации пользователей")})
public class AuthController {
    private final RegisterService registerService;
    private final UserValidator validator;
    private final MapToUser mapToUser = Mappers.getMapper(MapToUser.class);
    @Autowired
    public AuthController(RegisterService registerService, UserValidator validator) {
        this.registerService = registerService;
        this.validator = validator;
    }
    @PostMapping("/login")
    @Operation(description = "Войти в систему")
    public ResponseEntity<Map<String, Object>> login(@RequestBody @Valid AuthorizeDTO dto,
                                                     BindingResult b){
        User user = mapToUser.dtoToUser(dto);
        boolean result = registerService.loginUser(user);
        if (b.hasErrors()){
            List<String> errors = ErrorSender.returnErrorsToClient(b);
            return ResponseEntity.badRequest().body(Map.of("message", errors));
        }else if(!result){
            return ResponseEntity.badRequest().body(Map.of("message", "Некорректные данные"));
        }
        return ResponseEntity.ok(Map.of("message", "Вы успешно вошли в систему"));
    }

    @PostMapping("/registration")
    @Operation(description = "Зарегистрироваться в системе")
    public ResponseEntity<Map<String, Object>> register(@RequestBody @Valid AuthorizeDTO dto,
                                                        BindingResult br){
        User user = mapToUser.dtoToUser(dto);
        validator.validate(user, br);
        if (br.hasErrors()){
            List<String> errors = ErrorSender.returnErrorsToClient(br);
            return ResponseEntity.badRequest().body(Map.of("message", errors));
        }
        registerService.registerUser(user);
        return ResponseEntity.ok(Map.of("message", "Регистрация успешна"));
    }
}
