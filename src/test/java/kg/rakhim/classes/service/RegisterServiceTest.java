package kg.rakhim.classes.service;

import kg.rakhim.classes.models.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.auditable.data.UserData;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RegisterServiceTest {
    @Autowired
    private RegisterService registerService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserData userData;

    @Test
    @DisplayName("Testing method registerUser()")
    void testRegisterUser() {
        User newUser = new User("test", "testPassword", "USER");
        registerService.registerUser(newUser);
        verify(userService).save(newUser);
    }

    @Test
    @DisplayName("Testing method loginUser()")
    void testLoginUser() {
        User existingUser = new User("existing_user", "existingPassword", "USER");
        when(userService.findByUsername("existing_user")).thenReturn(Optional.of(existingUser));
        boolean result1 = registerService.loginUser(existingUser);
        boolean result2 = registerService.loginUser(new User("nonexistentUser", "password", "USER"));

        assertThat(result1).isTrue();
        assertThat(result2).isFalse();

        verify(userService, times(1)).findByUsername(existingUser.getUsername());
    }
}
