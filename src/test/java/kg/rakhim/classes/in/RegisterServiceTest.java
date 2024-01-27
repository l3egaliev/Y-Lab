package kg.rakhim.classes.in;

import kg.rakhim.classes.database.Storage;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.models.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class RegisterServiceTest {
    private Storage storageMock;
    private RegisterService registerService;

    @BeforeEach
    void setUp() {
        storageMock = mock(Storage.class);
        registerService = new RegisterService(storageMock);
    }

    @Test
    void testRegisterUser() {
        User newUser = new User("testUser", "testPassword", UserRole.USER);

        // Устанавливаем поведение мока
        when(storageMock.getUsers()).thenReturn(new ArrayList<>());

        // Вызываем метод тестируемого объекта
        assertTrue(registerService.registerUser(newUser), "Регистрация пользователя должна вернуть true");
    }

    @Test
    void testLoginUser() {

        User existingUser = new User("existingUser", "existingPassword", UserRole.USER);

        when(storageMock.getUsers()).thenReturn(Collections.singletonList(existingUser));

        assertTrue(registerService.loginUser("existingUser", "existingPassword"));
        assertFalse(registerService.loginUser("nonexistentUser", "password"));

        verify(storageMock, times(2)).getUsers();
    }
}
