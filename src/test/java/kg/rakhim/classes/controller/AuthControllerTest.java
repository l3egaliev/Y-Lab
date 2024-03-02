package kg.rakhim.classes.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.validation.BindingResult;

import kg.rakhim.classes.dto.AuthorizeDTO;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.service.RegisterService;
import kg.rakhim.classes.utils.MapToUser;
import kg.rakhim.classes.utils.UserValidator;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegisterService registerService;

    @MockBean
    private MapToUser mapToUser;

    @MockBean
    private UserValidator validator;

    private AuthorizeDTO dto;

    @BeforeEach
    public void setup() {
        dto = new AuthorizeDTO("testuser", "testpassword");
    }

    @Test
    @DisplayName("Testing method login")
    public void testLogin() throws Exception {
        when(registerService.loginUser(any(User.class))).thenReturn(true);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"testpassword\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(content).contains("{\"message\":\"Вы успешно вошли в систему\"}");
    }

    @Test
    @DisplayName("Testing method register")
    public void testRegister() throws Exception {
        doNothing().when(validator).validate(any(), any());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/auth/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"testuser\", \"password\": \"testpassword\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(content).contains("\"message\":\"Регистрация успешна\"");
    }
}