package kg.rakhim.classes.context;

import java.util.concurrent.atomic.AtomicReference;

public class UserContext {
    private static final AtomicReference<UserDetails> currentUser = new AtomicReference<>();

    public static void setCurrentUser(UserDetails userDetails) {
        currentUser.set(userDetails);
    }

    public static UserDetails getCurrentUser() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.set(null);
    }
}
