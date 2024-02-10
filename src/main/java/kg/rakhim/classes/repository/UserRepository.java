package kg.rakhim.classes.repository;

import kg.rakhim.classes.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseRepository<User>{
    Optional<User> findByUsername(String username);

    boolean isAdmin(String username);
}
