package kg.rakhim.classes.service;

import kg.rakhim.classes.dao.AuditDAO;
import kg.rakhim.classes.dao.UserDAO;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.repository.impl.AuditRepositoryImpl;
import kg.rakhim.classes.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RegisterServiceTest {
    private RegisterService registerService;
    private UserDAO userDAO;

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("ylab")
            .withUsername("postgres")
            .withPassword("postgres");

    @BeforeEach
    void setUp() {
        String jdbcUrl = postgresContainer.getJdbcUrl();
        String username = postgresContainer.getUsername();
        String password = postgresContainer.getPassword();

        userDAO = new UserDAO();
        userDAO.setJdbcUrl(jdbcUrl);
        userDAO.setUsername(username);
        userDAO.setPassword(password);
        UserService userService = new UserService(new UserRepositoryImpl(userDAO));
        AuditDAO mockAuditDAO = mock(AuditDAO.class);
        registerService = new RegisterService(userService, new AuditService(new AuditRepositoryImpl(mockAuditDAO)));
    }

    @Test
    @DisplayName("Testing method registerUser()")
    void testRegisterUser() {
        User newUser = new User("test", "testPassword", "USER");
        assertEquals(1, registerService.registerUser(newUser));
    }

    @Test
    @DisplayName("Testing method loginUser()")
    void testLoginUser() {
        User existingUser = new User("existing_user", "existingPassword", "USER");
        userDAO.save(existingUser);

        assertTrue(registerService.loginUser("existing_user", "existingPassword"));
        assertFalse(registerService.loginUser("nonexistentUser", "password"));
    }
}
