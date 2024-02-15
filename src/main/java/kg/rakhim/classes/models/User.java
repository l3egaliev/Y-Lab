/**
 * Класс {@code User} представляет объект пользователя системы.
 * <p>
 * Имеет поля для хранения имени пользователя, пароля и роли пользователя.
 * </p>
 * <p>
 * Реализованы геттеры, сеттеры, конструкторы и переопределен метод {@code toString()} для удобного представления информации.
 * </p>
 *
 * @author Рахим Нуралиев
 * @version 1.0
 * @since 2024-01-26
 */
package kg.rakhim.classes.models;

/**
 * Класс {@code User} представляет объект пользователя системы.
 */
public class User {
    private Integer id;
    private String username;
    private String password;
    private String role;

    /**
     * Конструктор, который используется в объекте MeterReading
     */
    public User(String username, String password, String role){
        this.username = username;
        this.password = password;
        this.role  = role;
    }
    public User(String username){
        this.username = username;
    }

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Переопределение метода {@code toString()} для удобного представления информации о пользователе.
     *
     * @return строковое представление объекта {@code User}
     */
    @Override
    public String toString() {
        return username;
    }
}
