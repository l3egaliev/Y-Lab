package ru.auditable.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private String username;
    private Map<String, String> actions;
    public UserInfo(String username){
        this.username = username;
    }
}
