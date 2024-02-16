package kg.rakhim.classes.service;

import kg.rakhim.classes.dao.UserDAO;
import kg.rakhim.classes.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервисный класс для работы с пользователями.
 * Реализует интерфейс UserRepository.
 */
@Service
public class UserService  {
    private final UserDAO userDAO;

    /**
     * Конструктор для создания экземпляра класса UserService.
     *
     * @param userDAO объект класса UserRepository для взаимодействия с данными о пользователях
     */
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Возвращает пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return объект Optional, содержащий пользователя, если он найден; пустой объект, если пользователь не найден
     */
    public Optional<User> findById(int id) {
        return userDAO.get(id);
    }

    /**
     * Возвращает пользователя по его имени пользователя.
     *
     * @param username имя пользователя
     * @return объект User, соответствующий переданному имени пользователя
     */
    public Optional<User> findByUsername(String username) {
        return userDAO.getUser(username);
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return список объектов User, представляющих всех пользователей в системе
     */
    public List<User> findAll() {
        return userDAO.getAll();
    }

    /**
     * Проверяет, является ли пользователь администратором.
     *
     * @param username имя пользователя
     * @return true, если пользователь имеет роль ADMIN; false в противном случае
     */
    public boolean isAdmin(String username) {
        Optional<User> user = userDAO.getUser(username);
        return user.map(value -> value.getRole().equals("ADMIN")).orElse(false);
    }

    /**
     * Сохраняет пользователя.
     *
     * @param user объект User, который необходимо сохранить
     */
    public void save(User user) {
        user.setRole("USER");
        userDAO.save(user);
    }
}
