//package kg.rakhim.classes.service;
//
//import kg.rakhim.classes.context.UserContext;
//import kg.rakhim.classes.models.User;
//import org.json.JSONObject;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.Collections;
//import java.util.Map;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.*;
//
//@Testcontainers
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class RegisterServiceTest {
//
//    private RegisterService registerService;
//
//    @Mock
//    private UserService userService;
//    @Mock
//    private UserContext userContext;
//
//    @Container
//    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
//            .withDatabaseName("ylab")
//            .withUsername("postgres")
//            .withPassword("postgres");
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        registerService = new RegisterService(userService, userContext);
//    }
//
//    @Test
//    @DisplayName("Testing method registerUser()")
//    void testRegisterUser() {
//        User newUser = new User("test", "testPassword", "USER");
//        registerService.registerUser(newUser);
//        verify(userService).save(newUser);
//    }
//
//    @Test
//    @DisplayName("Testing method loginUser()")
//    void testLoginUser() {
//        User existingUser = new User("existing_user", "existingPassword", "USER");
//        when(userService.findAll()).thenReturn(Collections.singletonList(existingUser));
//        boolean result1 = registerService.loginUser(existingUser);
//        boolean result2 = registerService.loginUser(new User("nonexistentUser", "password", "USER"));
//        when(userService.findByUsername(existingUser.getUsername())).thenReturn(Optional.of(existingUser));
//
//        assertThat(result1).isTrue();
//        assertThat(result2).isFalse();
//
//        verify(userService, times(2)).findByUsername(existingUser.getUsername());
//    }
//}
