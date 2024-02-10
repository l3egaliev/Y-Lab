package kg.rakhim.classes.service;

import kg.rakhim.classes.dao.UserDAO;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.repository.UserRepository;
import kg.rakhim.classes.repository.impl.UserRepositoryImpl;

import java.util.List;
import java.util.Optional;

/**
 * Сервисный класс для работы с пользователями.
 * Реализует интерфейс UserRepository.
 */
public class UserService  {
    private final UserRepositoryImpl userRepository;

    /**
     * Конструктор для создания экземпляра класса UserService.
     *
     * @param userRepository объект класса UserRepository для взаимодействия с данными о пользователях
     */
    public UserService(UserRepositoryImpl userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Возвращает пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return объект Optional, содержащий пользователя, если он найден; пустой объект, если пользователь не найден
     */
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    /**
     * Возвращает пользователя по его имени пользователя.
     *
     * @param username имя пользователя
     * @return объект User, соответствующий переданному имени пользователя
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return список объектов User, представляющих всех пользователей в системе
     */
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * Проверяет, является ли пользователь администратором.
     *
     * @param username имя пользователя
     * @return true, если пользователь имеет роль ADMIN; false в противном случае
     */
    public boolean isAdmin(String username) {
        return userRepository.isAdmin(username);
    }

    /**
     * Сохраняет пользователя.
     *
     * @param user объект User, который необходимо сохранить
     */
    public void save(User user) {
        user.setRole("USER");
        userRepository.save(user);
    }

    /**
     * Возвращает идентификатор пользователя.
     *
     * @param user объект User
     * @return идентификатор пользователя
     */
    public int getUserId(User user) {
        return userRepository.findByUsername(user.getUsername()).get().getId();
    }
}
