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
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Класс {@code Storage} предоставляет хранилище для данных о пользователях, показаниях счетчиков и аудитах.
 */
@Data
public class Storage {
    private UserStorage userStorage = new UserStorage();
    private AuditStorage auditStorage = new AuditStorage();
    private MeterReadingStorage meterReadingStorage = new MeterReadingStorage();
    private MeterTypesStorage meterTypesStorage = new MeterTypesStorage();
}
