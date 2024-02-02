package kg.rakhim.classes.service;

import kg.rakhim.classes.database.UserStorage;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.models.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class UserServiceTest {
    private UserService userService;
    private UserStorage userStorage;

    @BeforeEach
    void setUp(){
        userStorage = new UserStorage();
        userService = new UserService(userStorage);
    }

    @DisplayName("Testing method findByUsername()")
    @Test
    void testFindByUsername() {
        User existingUser = new User("existingUser", "existingPassword", UserRole.USER);
        userService.save(existingUser);

        User resultUser = userService.findByUsername("existingUser").get();
        assertSame(existingUser, resultUser);
    }

    @DisplayName("Testing method findAll()")
    @Test
    void testFindAll(){
        User user1 = new User("user1", "user1pass", UserRole.USER);
        User user2 = new User("user2", "user2pass", UserRole.ADMIN);
        userService.save(user1);
        userService.save(user2);
        assertThat(userService.findAll()).hasSize(3); // 3 потому что при запуске программы создается user админ.
    }

    @DisplayName("Testing method isAdmin()")
    @Test
    void testIsAdmin(){
        User userAdmin = new User("user", "password", UserRole.ADMIN);
        userService.save(userAdmin);
        assertTrue(userService.isAdmin(userAdmin.getUsername()));
    }
}
