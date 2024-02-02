package kg.rakhim.classes.service;

import kg.rakhim.classes.database.UserStorage;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.models.UserRole;
import kg.rakhim.classes.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserService implements UserRepository {
    private final UserStorage userStorage;

    public UserService(UserStorage storage) {
        this.userStorage = storage;
    }

    // TODO
    @Override
    public Optional<User> findById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        for (User u : userStorage.getUsers()){
            if (u.getUsername().equals(username)){
                return Optional.of(u);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return userStorage.getUsers();
    }


    @Override
    public boolean isAdmin(String username) {
        return findByUsername(username).get().getRole().equals(UserRole.ADMIN);
    }

    @Override
    public void save(Object user) {
        userStorage.getUsers().add((User) user);
    }
}
