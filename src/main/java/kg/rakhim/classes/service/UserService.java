package kg.rakhim.classes.service;

import kg.rakhim.classes.dao.UserDAO;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.models.UserRole;
import kg.rakhim.classes.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService implements UserRepository {
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    // TODO
    @Override
    public Optional<User> findById(int id) {
        return Optional.of(userDAO.get(id));
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.getUser(username);
    }

    @Override
    public List<User> findAll() {
        return userDAO.getAll();
    }


    @Override
    public boolean isAdmin(String username) {
        User user = userDAO.getUser(username);
        if (user.getRole().equals("ADMIN")){
            return true;
        }
        return false;
    }

    @Override
    public void save(Object user) {
        userDAO.save((User) user);
    }
}
