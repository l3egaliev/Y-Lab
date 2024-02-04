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
package kg.rakhim.classes.service.actions;

import kg.rakhim.classes.context.ApplicationContext;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.models.MeterType;
import kg.rakhim.classes.out.ConsoleOut;
import kg.rakhim.classes.service.AuditService;
import kg.rakhim.classes.service.MeterReadingService;
import kg.rakhim.classes.service.MeterTypesService;
import kg.rakhim.classes.service.UserService;

import java.time.LocalDateTime;
import java.util.*;

import static kg.rakhim.classes.in.ConsoleIn.commandList;

/**
 * Класс {@code UsersActions} предоставляет методы для действий пользователей в отношении показаний счетчиков.
 */
public class UsersActions {
    private static final Scanner scanner = new Scanner(System.in);
    private final UserService userService;
    private final AuditService auditService;
    private final MeterReadingService mService;
    private final MeterTypesService typesService;

    /**
     * Конструирует экземпляр {@code UsersActions} с указанным сканером и хранилищем.
     *
     */
    public UsersActions() {
        this.userService = (UserService) ApplicationContext.getContext("userService");
        this.auditService = (AuditService) ApplicationContext.getContext("auditService");
        this.mService = (MeterReadingService) ApplicationContext.getContext("meterReadingService");
        this.typesService = (MeterTypesService) ApplicationContext.getContext("meterTypeService");
    }

    /**
     * Реализация подачи показаний.
     *
     * @param username имя пользователя, подающего показания
     */
    public void submitCounterReading(String username) {
        MeterReading meterReading = new MeterReading();
        meterReading.setUser(userService.findByUsername(username));
        ConsoleOut.printLine("Для подачи показаний вводите следующие данные: ");
        scanTypeOfMeterReading(meterReading, username);
        ConsoleOut.printLine("\t- Значение (формат: 4 цифр, пример - 1000)");
        int value = Integer.parseInt(scanner.next());
        meterReading.setValue(value);
        meterReading.setDate(LocalDateTime.now());

        // Проверить не отправлял ли пользователь в этом месяце показаний
        for (MeterReading m : mService.findAll()) {
            if (m.getUser().getUsername().equals(username) &&
                    m.getDate().getMonthValue() == LocalDateTime.now().getMonthValue()
                    && m.getMeterType().equals(meterReading.getMeterType())) {
                ConsoleOut.printLine("Вы в этом месяце уже отправляли показание: " + meterReading.getMeterType() + "\n---\n" + m);
                commandList(username);
            }
        }
        mService.save(meterReading);
        auditService.save(new Audit(username, "Подача показания: " + meterReading, LocalDateTime.now()));
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
        for (MeterType m : typesService.findAll()) {
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
        for (MeterReading m : mService.findAll()) {
            if (m.getUser().getUsername().equals(username) &&
                    m.getDate().getMonthValue() == LocalDateTime.now().getMonthValue()) {
                ConsoleOut.printLine("\t- " + m);
            }
        }
        auditService.save(new Audit(username, "Просмотр актуальных показаний", LocalDateTime.now()));
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
        for (MeterReading m : mService.findAll()) {
            if (m.getUser().getUsername().equals(username)) {
                if (m.getDate().getMonthValue() == month) {
                    ConsoleOut.printLine(" - " + m);
                }
            }
        }
        auditService.save(new Audit(username, "Просмотр истории за конкретный месяц", LocalDateTime.now()));
        commandList(username);
    }

    /**
     * Реализация просмотра истории всех поданных показаний.
     *
     * @param username имя пользователя, просматривающего историю
     */
    public void viewReadingHistory(String username) {
        ConsoleOut.printLine("История показаний:");
        for (MeterReading m : mService.findAll()) {
            if (m.getUser().getUsername().equals(username)) {
                ConsoleOut.printLine("\t - " + m);
            }
        }
        auditService.save(new Audit(username, "Просмотр истории всех показаний", LocalDateTime.now()));
        commandList(username);
    }
}
