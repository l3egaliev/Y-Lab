package kg.rakhim.classes.context;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class UserContext {
    private final AtomicReference<UserDetails> currentUser = new AtomicReference<>();

    public void setCurrentUser(UserDetails userDetails) {
        currentUser.set(userDetails);
    }

    public UserDetails getCurrentUser() {
        return currentUser.get();
    }

    public void clear() {
        currentUser.set(null);
    }
}
