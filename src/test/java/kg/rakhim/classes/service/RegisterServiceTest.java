//package kg.rakhim.classes.service;
//
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
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//
//@Testcontainers
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class RegisterServiceTest {
//
//    private RegisterService registerService;
//
//    @Mock
//    private UserService userService;
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
//
//        String jdbcUrl = postgresContainer.getJdbcUrl();
//        String username = postgresContainer.getUsername();
//        String password = postgresContainer.getPassword();
//
//        registerService = new RegisterService(userService);
//    }
//
////    @Test
////    @DisplayName("Testing method registerUser()")
////    void testRegisterUser() {
////        User newUser = new User("test", "testPassword", "USER");
////        Map<Boolean, JSONObject> result = registerService.registerUser(newUser);
////        assertThat(result).containsKey(true);
////    }
//
//    @Test
//    @DisplayName("Testing method loginUser()")
//    void testLoginUser() {
//        User existingUser = new User("existing_user", "existingPassword", "USER");
//        when(userService.findAll()).thenReturn(Collections.singletonList(existingUser));
////        Map<Boolean, JSONObject> result1 = registerService.loginUser("existing_user", "existingPassword");
////        Map<Boolean, JSONObject> result2 = registerService.loginUser("nonexistentUser", "password");
//////        when(userService.findAll()).thenReturn(Collections.singletonList(existingUser));
////
////        assertThat(result1).containsKey(true);
////        assertThat(result2).containsKey(false);
//    }
//}
