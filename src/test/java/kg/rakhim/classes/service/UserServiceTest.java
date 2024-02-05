package kg.rakhim.classes.service;

import kg.rakhim.classes.dao.UserDAO;
import kg.rakhim.classes.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private UserService userService;
    private UserDAO userDAO;

    @BeforeEach
    void setUp(){
        userDAO = new UserDAO();
        userService = new UserService(userDAO);
    }

    @DisplayName("Testing method findByUsername()")
    @Test
    void testFindByUsername() {
        User existingUser = new User("existingUser", "existingPassword", "USER");
        userService.save(existingUser);
        User resultUser = userService.findByUsername("existingUser");
        assertEquals(existingUser.getUsername(), resultUser.getUsername());
    }

    @DisplayName("Testing method findAll()")
    @Test
    void testFindAll(){
        User user1 = new User("user1", "user1pass", "USER");
        User user2 = new User("user2", "user2pass", "ADMIN");
        userService.save(user1);
        userService.save(user2);
        assertThat(userService.findAll()).hasSize(3); // 3 потому что при запуске программы создается user админ.
    }

    @DisplayName("Testing method isAdmin()")
    @Test
    void testIsAdmin(){
        User userAdmin = new User("user", "password", "ADMIN");
        userService.save(userAdmin);
        assertTrue(userService.isAdmin(userAdmin.getUsername()));
    }
}
