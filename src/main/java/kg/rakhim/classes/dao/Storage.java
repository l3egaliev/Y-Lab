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
package kg.rakhim.classes.dao;

import lombok.Data;

/**
 * Класс {@code Storage} предоставляет хранилище для данных о пользователях, показаниях счетчиков и аудитах.
 */
@Data
public class Storage {
    private UserDAO userDAO = new UserDAO();
    private AuditDAO auditStorage = new AuditDAO();
    private MeterReadingStorage meterReadingStorage = new MeterReadingStorage();
    private MeterTypesStorage meterTypesStorage = new MeterTypesStorage();
}
