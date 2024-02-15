package kg.rakhim.classes.servlet.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.rakhim.classes.dto.AuthorizeDTO;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.service.RegisterService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RegisterServletTest {

    @Mock
    private RegisterService registerService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private RegisterServlet servlet;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDoPost_Success() throws IOException, ServletException {
        // Arrange
        String jsonInput = "{\"username\":\"test_user\",\"password\":\"test_password\"}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(jsonInput.getBytes());
        when(request.getInputStream()).thenReturn(new ServletInputStreamWrapper(inputStream));

        AuthorizeDTO dto = new AuthorizeDTO();
        dto.setUsername("test_user");
        dto.setPassword("test_password");

        User user = new User();
        user.setUsername("test_user");
        user.setPassword("test_password");

        Map<Boolean, JSONObject> result = new HashMap<>();
        result.put(true, new JSONObject().put("message", "User successfully registered"));

        when(registerService.registerUser(any(User.class))).thenReturn(result);

        // Act
        servlet.doPost(request, response);

        // Assert
        verify(response).setCharacterEncoding("UTF-8");
        verify(response, times(1)).setContentType("application/json");
        verify(response, times(1)).setStatus(HttpServletResponse.SC_OK);
        verify(response).getWriter();
    }

    @Test
    void testDoPost_Failure() throws IOException, ServletException {
        // Arrange
        String jsonInput = "{\"username\":\"test_user\",\"password\":\"test_password\"}";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(jsonInput.getBytes());
        when(request.getInputStream()).thenReturn(new ServletInputStreamWrapper(inputStream));

        AuthorizeDTO dto = new AuthorizeDTO();
        dto.setUsername("test_user");
        dto.setPassword("test_password");

        User user = new User();
        user.setUsername("test_user");
        user.setPassword("test_password");

        Map<Boolean, JSONObject> result = new HashMap<>();
        result.put(false, new JSONObject().put("error", "User registration failed"));

        when(registerService.registerUser(any(User.class))).thenReturn(result);

        // Act
        servlet.doPost(request, response);

        // Assert
        verify(response).setCharacterEncoding("UTF-8");
        verify(response, times(1)).setContentType("application/json");
        verify(response, times(1)).setStatus(HttpServletResponse.SC_BAD_REQUEST);
        verify(response).getWriter();
    }
}
