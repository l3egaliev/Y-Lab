package kg.rakhim.classes.repository.impl;

import kg.rakhim.classes.dao.UserDAO;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final UserDAO userDAO;

    public UserRepositoryImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Optional<User> findById(int id) {
        return userDAO.get(id);
    }

    @Override
    public List<User> findAll() {
        return userDAO.getAll();
    }

    @Override
    public void save(User e) {
        userDAO.save(e);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDAO.getUser(username);
    }

    @Override
    public boolean isAdmin(String username) {
        Optional<User> user = userDAO.getUser(username);
        return user.map(value -> value.getRole().equals("ADMIN")).orElse(false);
    }
}
