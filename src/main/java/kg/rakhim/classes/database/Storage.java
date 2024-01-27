/**
 * Класс {@code Storage} предоставляет хранилище для данных о пользователях, показаниях счетчиков и аудитах.
 * <p>
 * Имеет поля для хранения списков пользователей, показаний счетчиков и аудитов.
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
 */
package kg.rakhim.classes.database;

import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.models.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс {@code Storage} предоставляет хранилище для данных о пользователях, показаниях счетчиков и аудитах.
 */
@Getter
@Setter
public class Storage {
    private List<User> users = new ArrayList<>();
    private List<MeterReading> meterReadings = new ArrayList<>();
    private List<Audit> audits = new ArrayList<>();

    /**
     * Конструктор для создания экземпляра класса {@code Storage}.
     * При запуске приложения создается учетная запись для администратора и добавляется 3 типа счетчика.
     */
    public Storage() {
        User admin = new User("admin", "adminpass", UserRole.ADMIN);
        users.add(admin);
        meterReadings.add(new MeterReading("горячая вода"));
        meterReadings.add(new MeterReading("холодная вода"));
        meterReadings.add(new MeterReading("отопление"));
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
