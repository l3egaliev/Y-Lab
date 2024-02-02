package kg.rakhim.classes.repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository<User> extends BaseRepository{
    Optional<User> findByUsername(String username);

    boolean isAdmin(String username);
}
