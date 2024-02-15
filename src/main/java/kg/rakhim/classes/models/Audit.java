/**
 * Класс {@code Audit} представляет аудитовую запись о действиях пользователей в системе.
 * <p>
 * Имеет поля для хранения имени пользователя, выполненного действия и времени его выполнения.
 * </p>
 * <p>
 * Реализованы геттеры, сеттеры, конструктор и переопределен метод {@code toString()} для удобного представления информации.
 * </p>
 *
 * @author Рахим Нуралиев
 * @version 1.0
 * @since 2024-01-26
 */
package kg.rakhim.classes.models;

import java.sql.Timestamp;

/**
 * Класс {@code Audit} представляет аудитовую запись о действиях пользователей в системе.
 */
public class Audit {
    private int id;
    private String username;
    private String action;
    private Timestamp time;

    public Audit(String username, String action) {
        this.username = username;
        this.action = action;
    }

    public Audit(int auditId, String testAction) {
        this.id = auditId;
        this.action = testAction;
    }

    public Audit() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
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
