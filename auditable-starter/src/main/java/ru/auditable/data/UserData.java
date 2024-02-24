package ru.auditable.data;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
public class UserData {
    private final AtomicReference<UserInfo> currentUser = new AtomicReference<>();

    public void setCurrentUser(UserInfo userInfo) {
        currentUser.set(userInfo);
    }

    public UserInfo getCurrentUser() {
        return currentUser.get();
    }

    public void clear() {
        currentUser.set(null);
    }
}
