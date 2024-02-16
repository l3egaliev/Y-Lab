package kg.rakhim.classes.controller;

import jakarta.validation.Valid;
import kg.rakhim.classes.dto.AuthorizeDTO;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.service.RegisterService;
import kg.rakhim.classes.utils.ErrorSender;
import kg.rakhim.classes.utils.MapToUser;
import kg.rakhim.classes.utils.UserValidator;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
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
