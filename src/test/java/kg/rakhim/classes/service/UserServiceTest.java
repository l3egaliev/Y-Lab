//package kg.rakhim.classes.service;
//
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
//import kg.rakhim.classes.dao.UserDAO;
//import kg.rakhim.classes.models.User;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertSame;
//import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
//import static org.mockito.Mockito.*;
//@Testcontainers
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//public class UserServiceTest {
//    private UserService service;
//    @Mock
//    private UserDAO mockUserDAO;
//
//    @Container
//    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest")
//            .withDatabaseName("ylab")
//            .withUsername("postgres")
//            .withPassword("postgres");
//
//    @BeforeEach
//    void setUp(){
//        MockitoAnnotations.openMocks(this);
//        service = new UserService(mockUserDAO);
//    }
//
//    @DisplayName("Testing method findByUsername()")
//    @Test
//    void testFindByUsername() {
//        User existingUser = new User("existingUser", "existingPassword", "USER");
//
//        when(mockUserDAO.getUser("existingUser")).thenReturn(Optional.of(existingUser));
//        User resultUser = service.findByUsername("existingUser").get();
//
//        assertSame(existingUser, resultUser);
//
//        verify(mockUserDAO, times(1)).getUser("existingUser");
//    }
//
//    @DisplayName("Testing method findAll()")
//    @Test
//    void testFindAll(){
//        List<User> expectedUsers = new ArrayList<>();
//        expectedUsers.add(new User("user1", "user1pass", "USER"));
//        expectedUsers.add(new User("user2", "user2pass", "USER"));
//
//        when(mockUserDAO.getAll()).thenReturn(expectedUsers);
//
//        UserService userService = new UserService(mockUserDAO);
//        List<User> result = userService.findAll();
//        assertThat(result).containsExactlyElementsOf(expectedUsers);
//        verify(mockUserDAO, times(1)).getAll();
//    }
//
//    @DisplayName("Testing method save()")
//    @Test
//    void testSave(){
//        User userAdmin = new User("user", "password", "ADMIN");
//        service.save(userAdmin);
//        verify(mockUserDAO, times(1)).save(userAdmin);
//    }
//}