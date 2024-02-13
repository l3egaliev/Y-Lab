package kg.rakhim.classes.service;

import kg.rakhim.classes.dao.AuditDAO;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.repository.impl.AuditRepositoryImpl;
import kg.rakhim.classes.repository.impl.UserRepositoryImpl;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RegisterServiceTest {

    private RegisterService registerService;

    @Mock
    private UserRepositoryImpl userRepository;

    @Mock
    private AuditDAO auditDAO;

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("ylab")
            .withUsername("postgres")
            .withPassword("postgres");

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        String jdbcUrl = postgresContainer.getJdbcUrl();
        String username = postgresContainer.getUsername();
        String password = postgresContainer.getPassword();

        UserService userService = new UserService(userRepository);
        AuditService auditService = new AuditService(new AuditRepositoryImpl(auditDAO));

        registerService = new RegisterService(userService, auditService);
    }

    @Test
    @DisplayName("Testing method registerUser()")
    void testRegisterUser() {
        User newUser = new User("test", "testPassword", "USER");
        Map<Boolean, JSONObject> result = registerService.registerUser(newUser);
        when(registerService.registerUser(newUser)).thenReturn(Map.of(true, new JSONObject()));
        assertThat(result).containsKey(true);
    }

//    @Test
//    @DisplayName("Testing method loginUser()")
//    void testLoginUser() {
//        // Arrange
//        User existingUser = new User("existing_user", "existingPassword", "USER");
//        when(userRepository.findByUsernameAndPassword("existing_user", "existingPassword")).thenReturn(existingUser);
//        when(userRepository.findByUsernameAndPassword("nonexistentUser", "password")).thenReturn(null);
//
//        // Act & Assert
//        assertTrue(registerService.loginUser("existing_user", "existingPassword"));
//        assertFalse(registerService.loginUser("nonexistentUser", "password"));
//    }
}
