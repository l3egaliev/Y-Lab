package kg.rakhim.classes.service;

import kg.rakhim.classes.dao.UserDAO;
import kg.rakhim.classes.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserService service;
    private UserDAO mockUserDAO;

    @BeforeEach
    void setUp(){
        mockUserDAO = mock(UserDAO.class);
        service = new UserService(mockUserDAO);
    }

    @DisplayName("Testing method findByUsername()")
    @Test
    void testFindByUsername() {
        User existingUser = new User("existingUser", "existingPassword", "USER");

        when(mockUserDAO.getUser("existingUser")).thenReturn(existingUser);
        User resultUser = service.findByUsername("existingUser");

        assertSame(existingUser, resultUser);

        verify(mockUserDAO, times(1)).getUser("existingUser");
    }

    @DisplayName("Testing method findAll()")
    @Test
    void testFindAll(){
        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(new User("user1", "user1pass", "USER"));
        expectedUsers.add(new User("user2", "user2pass", "USER"));

        when(mockUserDAO.getAll()).thenReturn(expectedUsers);

        UserService userService = new UserService(mockUserDAO);
        List<User> result = userService.findAll();
        assertThat(result).containsExactlyElementsOf(expectedUsers);
        verify(mockUserDAO, times(1)).getAll();
    }

    @DisplayName("Testing method save()")
    @Test
    void testSave(){
        User userAdmin = new User("user", "password", "ADMIN");
        service.save(userAdmin);
        verify(mockUserDAO, times(1)).save(userAdmin);
    }
}
