//package kg.rakhim.classes.controller;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//
//import kg.rakhim.classes.dto.AuthorizeDTO;
//import kg.rakhim.classes.models.User;
//import kg.rakhim.classes.service.RegisterService;
//import kg.rakhim.classes.utils.ErrorSender;
//import kg.rakhim.classes.utils.MapToUser;
//import kg.rakhim.classes.utils.UserValidator;
//
//@ExtendWith(MockitoExtension.class)
//public class AuthControllerTest {
//
//    @Mock
//    private RegisterService registerService;
//
//    @Mock
//    MapToUser mapToUser;
//
//    @Mock
//    UserValidator validator;
//
//    @Mock
//    private BindingResult bindingResult;
//
//    @InjectMocks
//    private AuthController authController;
//
//    private AuthorizeDTO dto;
//
//    @BeforeEach
//    public void setup() {
//        dto = new AuthorizeDTO();
//        dto.setUsername("testuser");
//        dto.setPassword("testpassword");
//    }
//
//    @Test
//    @DisplayName("Testing method login")
//    public void testLogin() {
//        when(registerService.loginUser(any(User.class))).thenReturn(true);
//
//        ResponseEntity<Map<String, Object>> response = authController.login(dto, bindingResult);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).containsEntry("message", "Вы успешно вошли в систему");
//    }
//
//    @Test
//    @DisplayName("Testing method register")
//    public void testRegister() {
//        when(bindingResult.hasErrors()).thenReturn(false);
//
//        ResponseEntity<Map<String, Object>> response = authController.register(dto, bindingResult);
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).containsEntry("message", "Регистрация успешна");
//    }
//
//
//}
//
