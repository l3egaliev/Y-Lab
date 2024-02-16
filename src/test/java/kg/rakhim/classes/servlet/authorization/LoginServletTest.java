//package kg.rakhim.classes.servlet.authorization;
//
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import kg.rakhim.classes.dto.AuthorizeDTO;
//import kg.rakhim.classes.service.RegisterService;
//import org.json.JSONObject;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//public class LoginServletTest {
//    @Mock
//    private RegisterService registerService;
//
//    @Mock
//    private HttpServletRequest request;
//
//    @Mock
//    private HttpServletResponse response;
//
//    @Mock
//    private ObjectMapper objectMapper;
//
//    @InjectMocks
//    private LoginServlet servlet;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @DisplayName("Testing doPost() method of LoginServlet")
//    @Test
//    void testDoPost() throws IOException, ServletException {
//        // Arrange
//        String username = "testUser";
//        String password = "testPassword";
//        AuthorizeDTO dto = new AuthorizeDTO(username, password);
//        String json = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";
//
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(json.getBytes());
//        when(request.getInputStream()).thenReturn(new ServletInputStreamWrapper(inputStream));
//
//        Map<Boolean, JSONObject> expectedResult = new HashMap<>();
//        expectedResult.put(true, new JSONObject());
//
//        when(registerService.loginUser(username, password)).thenReturn(expectedResult);
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        when(response.getWriter()).thenReturn(new PrintWriter(outputStream));
//
//        // Act
//        servlet.doPost(request, response);
//
//        // Assert
//        verify(objectMapper).readValue((JsonParser) any(), eq(AuthorizeDTO.class));
//        verify(registerService).loginUser(username, password);
//
//        String responseString = outputStream.toString();
//        assertThat(responseString).isNotEmpty(); // Check if response is not empty
//        // You may need to assert the content of the response depending on your implementation
//        // assertThat(responseString).isEqualTo(expectedResponse);
//    }
//}
//
