package kg.rakhim.classes.service;

import kg.rakhim.classes.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.auditable.annotations.EnableXXX;
import ru.auditable.data.UserContext;
import ru.auditable.data.UserDetails;

import java.util.Map;
import java.util.Optional;


/**
 * Класс {@code RegisterService} предоставляет сервис для регистрации и авторизации пользователей.
 */
@Service
@EnableXXX
public class RegisterService {
    private final UserService userService;
    private final UserContext userContext;

    /**
     * Конструктор для создания экземпляра класса {@code RegisterService}.
     */
    @Autowired
    public RegisterService(UserService userService, UserContext userContext) {
        this.userService = userService;
        this.userContext = userContext;
    }

    /**
     * Регистрация нового пользователя.
     *
     * @param user объект User для регистрации
     */
    public void registerUser(User user) {
        user.setRole("USER");
        userService.save(user);
        userContext.setCurrentUser(new UserDetails(user.getUsername(),
                Map.of("registerUser", "Регистрация")));
    }

    /**
     * Авторизация пользователя.
     *
     * @return true, если пользователь аутентифицирован; false, если не найден или пароль неверен
     */
    public boolean loginUser(User user) {
        boolean res;
        Optional<User> u = userService.findByUsername(user.getUsername());
        res = u.isPresent() && u.get().getPassword().equals(user.getPassword());
        userContext.setCurrentUser(new UserDetails(user.getUsername(),
                Map.of("loginUser","Вход в систему")));
        return res;
    }
}
