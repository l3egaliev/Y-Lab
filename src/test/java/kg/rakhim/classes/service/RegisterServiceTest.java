package kg.rakhim.classes.service;

import kg.rakhim.classes.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterServiceTest {
    private UserService userService;
    private AuditService auditService;
    private RegisterService registerService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        auditService = mock(AuditService.class);
        registerService = new RegisterService(userService, auditService);
    }

    @Test
    void testRegisterUser() {
        User newUser = new User("testUser", "testPassword", "USER");
        when(userService.findAll()).thenReturn(new ArrayList<>());
        assertEquals(1, registerService.registerUser(newUser));
    }

    @Test
    void testLoginUser() {

        User existingUser = new User("existingUser", "existingPassword", "USER");

        when(userService.findAll()).thenReturn(Collections.singletonList(existingUser));

        assertTrue(registerService.loginUser("existingUser", "existingPassword"));
        assertFalse(registerService.loginUser("nonexistentUser", "password"));

        verify(userService, times(2)).findAll();
    }
}
