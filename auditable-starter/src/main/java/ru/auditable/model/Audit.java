package ru.auditable.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Класс {@code Audit} представляет аудитовую запись о действиях пользователей в системе.
 */
@Data
@NoArgsConstructor
public class Audit {
    private int id;
    private String username;
    private String action;
    private Timestamp time;

    public Audit(String username, String action) {
        this.username = username;
        this.action = action;
    }
    /**
     * Переопределение метода {@code toString()} для удобного представления информации об аудитовой записи.
     *
     * @return строковое представление объекта {@code Audit}
     */
    @Override
    public String toString() {
        return "Audit{" +
                "username = '" + username + '\'' +
                ", action = '" + action + '\'' +
                ", time = " + time +
                '}';
    }
}
