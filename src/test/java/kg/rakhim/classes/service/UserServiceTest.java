package kg.rakhim.classes.service;

import kg.rakhim.classes.dao.UserDAO;
import kg.rakhim.classes.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@SpringBootTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {
    @Autowired
    private UserService service;

    @MockBean
    private UserDAO mockUserDAO;

    @DisplayName("Testing method findByUsername()")
    @Test
    void testFindByUsername() {
        User existingUser = new User("existingUser", "existingPassword", "USER");

        when(mockUserDAO.getUser("existingUser")).thenReturn(Optional.of(existingUser));
        User resultUser = service.findByUsername("existingUser").get();

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

        List<User> result = service.findAll();
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
