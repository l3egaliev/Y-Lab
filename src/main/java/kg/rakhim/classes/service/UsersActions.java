/**
 * Класс {@code UsersActions} представляет собой действия, которые пользователи могут выполнять
 * в отношении показаний счетчиков. Включает методы для подачи показаний, просмотра актуальных показаний,
 * просмотра истории показаний за конкретный месяц и просмотра полной истории подачи показаний.
 * <p>
 * Этот класс зависит от класса {@link Storage} для доступа и манипулирования данными о пользователях и показаниях счетчиков.
 * </p>
 *
 * @author Рахим Нуралиев
 * @version 1.0
 * @since 2024-01-26
 * @see Storage
 * @see Audit
 * @see MeterReading
 * @see User
 * @see MeterReadingService
 */
package kg.rakhim.classes.service;

import kg.rakhim.classes.database.Storage;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.models.MeterType;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.out.ConsoleOut;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static kg.rakhim.classes.in.ConsoleIn.commandList;

/**
 * Класс {@code UsersActions} предоставляет методы для действий пользователей в отношении показаний счетчиков.
 */
public class UsersActions {
    private final Storage storage;
    private final Scanner scanner;

    /**
     * Конструирует экземпляр {@code UsersActions} с указанным сканером и хранилищем.
     *
     * @param scanner объект Scanner для ввода пользователя
     * @param storage объект Storage для доступа и манипуляции данными о пользователях и показаниях счетчиков
     */
    public UsersActions(Scanner scanner, Storage storage) {
        this.scanner = scanner;
        this.storage = storage;
    }

    /**
     * Реализация подачи показаний.
     *
     * @param username имя пользователя, подающего показания
     */
    public void submitCounterReading(String username) {
        MeterReading meterReading = new MeterReading();
        meterReading.setUser(storage.getUser(username));
        ConsoleOut.printLine("Для подачи показаний вводите следующие данные: ");
        scanTypeOfMeterReading(meterReading, username);
        ConsoleOut.printLine("\t- Значение (формат: 4 цифр, пример - 1000)");
        int value = Integer.parseInt(scanner.next());
        meterReading.setValue(value);
        meterReading.setDate(LocalDate.from(LocalDateTime.now()));

        // Проверить не отправлял ли пользователь в этом месяце показаний
        for (MeterReading m : storage.getMeterReadings()) {
            if (m.getUser().getUsername().equals(username) &&
                    m.getDate().getMonthValue() == LocalDateTime.now().getMonthValue()
                    && m.getMeterType().equals(meterReading.getMeterType())) {
                ConsoleOut.printLine("Вы в этом месяце уже отправляли показание: " + meterReading.getMeterType() + "\n---\n" + m);
                commandList(username);
            }
        }
        storage.getMeterReadings().add(meterReading);
        storage.getAudits().add(new Audit(username, "Подача показания: " + meterReading, LocalDateTime.now()));
        ConsoleOut.printLine("Показание успешно отправлено");

        commandList(username);
    }

    /**
     * Метод для сканирования и выбора типа показания счетчика.
     *
     * @param meterReading Объект MeterReading, для которого необходимо выбрать тип показания.
     */
    public void scanTypeOfMeterReading(MeterReading meterReading, String username) {
        // Создание карты для хранения соответствия первой буквы и типа показания
        Map<String, String> letterAndType = new HashMap<>();
        ConsoleOut.print("\t- Тип показания (");
        // Вывод доступных типов показаний и соответствующих первых букв
        for (MeterType m : storage.getMeterTypes()) {
            String firstLetter = String.valueOf(m.getType().charAt(0));
            ConsoleOut.print("" + m.getType() + " - " + firstLetter + " | ");
            letterAndType.put(firstLetter, m.getType());
        }

        ConsoleOut.printLine(")");

        // Получение выбора от пользователя
        String type = scanner.next().toLowerCase();

        // Установка выбранного типа показания в объекте MeterReading
        for (String key : letterAndType.keySet()) {
            if (key.equals(type)) {
                String selectedType = letterAndType.get(key);
                meterReading.setMeterType(new MeterType(selectedType));
            }else if(!letterAndType.containsKey(type)){
                ConsoleOut.printLine("Неправильное значение");
                submitCounterReading(username);
            }
        }
    }


    /**
     * Реализация просмотра актуальных показаний.
     *
     * @param username имя пользователя, просматривающего показания
     */
    public void viewCurrentReadings(String username) {
        ConsoleOut.printLine("\nВаши актуальные показания: ");
        for (MeterReading m : storage.getMeterReadings()) {
            if (m.getUser().getUsername().equals(username) &&
                    m.getDate().getMonthValue() == LocalDateTime.now().getMonthValue()) {
                ConsoleOut.printLine("\t- " + m);
            }
        }
        storage.getAudits().add(new Audit(username, "Просмотр актуальных показаний", LocalDateTime.now()));
        commandList(username);
    }

    /**
     * Реализация просмотра истории подачи показаний за конкретный месяц.
     *
     * @param username имя пользователя, просматривающего историю
     */
    public void viewReadingHistoryForMonth(String username) {
        ConsoleOut.printLine("За какой месяц хотите посмотреть? (формат: 1-12)");
        int month = scanner.nextInt();
        ConsoleOut.printLine("Ваши показания за " + month + " - месяц:");
        for (MeterReading m : storage.getMeterReadings()) {
            if (storage.getUser(username).equals(m.getUser())) {
                if (m.getDate().getMonthValue() == month) {
                    ConsoleOut.printLine(" - " + m);
                }
            }
        }
        storage.getAudits().add(new Audit(username, "Просмотр истории за конкретный месяц", LocalDateTime.now()));
        commandList(username);
    }

    /**
     * Реализация просмотра истории всех поданных показаний.
     *
     * @param username имя пользователя, просматривающего историю
     */
    public void viewReadingHistory(String username) {
        User user = storage.getUser(username);
        ConsoleOut.printLine("Все показания пользователя " + user + ":");
        for (MeterReading m : storage.getMeterReadings()) {
            if (m.getUser().equals(user)) {
                ConsoleOut.printLine("\n \t - " + m);
            }
        }
        storage.getAudits().add(new Audit(username, "Просмотр истории всех показаний", LocalDateTime.now()));
        commandList(username);
    }
}
