/**
 * Класс {@code Storage} предоставляет хранилище для данных о пользователях, показаниях счетчиков и аудитах.
 * <p>
 * Имеет поля для хранения списков пользователей, показаний счетчиков, тип счетчиков и аудитов.
 * При инициализации создает учетную запись для администратора и создает 3 счетчика.
 * </p>
 * <p>
 * Реализованы геттеры, сеттеры и метод {@code getUser(String username)} для получения пользователя по имени.
 * </p>
 *
 * @author Рахим Нуралиев
 * @version 1.0
 * @since 2024-01-26
 * @see User
 * @see MeterReading
 * @see Audit
 * @see MeterType
 */
package kg.rakhim.classes.database;

import kg.rakhim.classes.models.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс {@code Storage} предоставляет хранилище для данных о пользователях, показаниях счетчиков и аудитах.
 */
@Getter
@Setter
public class Storage {
    private List<User> users = new ArrayList<>();
    private List<MeterReading> meterReadings = new ArrayList<>();
    private List<Audit> audits = new ArrayList<>();
    private Set<MeterType> meterTypes = new HashSet<>();

    /**
     * Конструктор для создания экземпляра класса {@code Storage}.
     * При запуске приложения создается учетная запись для администратора и добавляется 3 типа счетчика.
     */
    public Storage() {
        User admin = new User("admin", "adminpass", UserRole.ADMIN);
        users.add(admin);
        meterTypes.add(new MeterType("горячая вода"));
        meterTypes.add(new MeterType("холодная вода"));
        meterTypes.add(new MeterType("отопление"));
    }

    /**
     * Метод для получения пользователя по имени.
     *
     * @param username имя пользователя
     * @return объект User с заданным именем или null, если пользователь не найден
     */
    public User getUser(String username) {
        for (User u : this.getUsers()) {
            if (u.getUsername().equals(username))
                return u;
        }
        return null;
    }
}
