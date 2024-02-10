package kg.rakhim.classes.service;

import kg.rakhim.classes.dao.UserDAO;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.repository.UserRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервисный класс для работы с пользователями.
 * Реализует интерфейс UserRepository.
 */
public class UserService implements UserRepository {
    private final UserDAO userDAO;

    /**
     * Конструктор для создания экземпляра класса UserService.
     *
     * @param dao объект класса UserDAO для взаимодействия с данными о пользователях
     */
    public UserService(UserDAO dao) {
        this.userDAO = dao;
    }

    /**
     * Возвращает пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return объект Optional, содержащий пользователя, если он найден; пустой объект, если пользователь не найден
     */
    @Override
    public Optional<User> findById(int id) {
        return Optional.of(userDAO.get(id));
    }

    /**
     * Возвращает пользователя по его имени пользователя.
     *
     * @param username имя пользователя
     * @return объект User, соответствующий переданному имени пользователя
     */
    @Override
    public User findByUsername(String username) {
        return userDAO.getUser(username);
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return список объектов User, представляющих всех пользователей в системе
     */
    @Override
    public List<User> findAll() {
        return userDAO.getAll();
    }

    /**
     * Проверяет, является ли пользователь администратором.
     *
     * @param username имя пользователя
     * @return true, если пользователь имеет роль ADMIN; false в противном случае
     */
    @Override
    public boolean isAdmin(String username) {
        User user = userDAO.getUser(username);
        return user.getRole().equals("ADMIN");
    }

    /**
     * Сохраняет пользователя.
     *
     * @param user объект User, который необходимо сохранить
     */
    @Override
    public void save(Object user) {
        userDAO.save((User) user);
    }

    /**
     * Возвращает идентификатор пользователя.
     *
     * @param user объект User
     * @return идентификатор пользователя
     */
    public int getUserId(User user) {
        return userDAO.getUser(user.getUsername()).getId();
    }
}
