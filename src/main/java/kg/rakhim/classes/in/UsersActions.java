package kg.rakhim.classes.in;

import kg.rakhim.classes.database.Storage;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.models.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static kg.rakhim.classes.in.MeterReadingService.commandList;

public class UsersActions {
    private final Storage storage;
    private final Scanner scanner;

    public UsersActions(Scanner scanner, Storage storage) {
        this.scanner = scanner;
        this.storage = storage;
    }

    public void submitCounterReading(String username) {
        MeterReading meterReading = new MeterReading();
        meterReading.setUser(storage.getUser(username));
        System.out.println("Для подачи показаний вводите следующие данные: ");
        scanTypeOfMeterReading(meterReading, username);
        System.out.println("\t- Значение (формат: 4 цифр, пример - 1000)");
        int value = Integer.parseInt(scanner.next());
        meterReading.setValue(value);
        meterReading.setDate(LocalDate.from(LocalDateTime.now()));

        // Проверить не отправлял ли пользователь в этом месяце показаний
        for (MeterReading m : storage.getMeterReadings()) {
            if (m.getUser().getUsername().equals(username) &&
                    m.getDate().getMonthValue() == LocalDateTime.now().getMonthValue()
                    && m.getMeterType().equals(meterReading.getMeterType())) {
                System.out.println("Вы в этом месяце уже отправляли показание: " + meterReading.getMeterType() + "\n---\n" + m);
                commandList(username);
            }
        }
        storage.getMeterReadings().add(meterReading);
        storage.getAudits().add(new Audit(username, "Подача показания: " + meterReading, LocalDateTime.now()));
        System.out.println("Показание успешно отправлено");

        commandList(username);
    }

    public void scanTypeOfMeterReading(MeterReading meterReading, String username) {
        // Создание карты для хранения соответствия первой буквы и типа показания
        Map<String, String> letterAndType = new HashMap<>();
        System.out.print("\t- Тип показания (");
        // Вывод доступных типов показаний и соответствующих первых букв
        for (MeterReading m : storage.getMeterReadings()) {
            String firstLetter = String.valueOf(m.getMeterType().charAt(0));
            System.out.print("" + m.getMeterType() + " - " + firstLetter + " | ");
            letterAndType.put(firstLetter, m.getMeterType());
        }

        System.out.println(")");

        // Получение выбора от пользователя
        String type = scanner.next().toLowerCase();

        // Установка выбранного типа показания в объекте MeterReading
        for (String key : letterAndType.keySet()) {
            if (key.equals(type)) {
                String selectedType = letterAndType.get(key);
                meterReading.setMeterType(selectedType);
            }else if(!letterAndType.containsKey(type)){
                System.out.println("Неправильное значение");
                submitCounterReading(username);
            }
        }
    }


    public void viewCurrentReadings(String username) {
        System.out.println("\nВаши актуальные показания: ");
        for (MeterReading m : storage.getMeterReadings()) {
            if (m.getUser().getUsername().equals(username) &&
                    m.getDate().getMonthValue() == LocalDateTime.now().getMonthValue()) {
                System.out.println("\t- " + m);
            }
        }
        storage.getAudits().add(new Audit(username, "Просмотр актуальных показаний", LocalDateTime.now()));
        commandList(username);
    }


    public void viewReadingHistoryForMonth(String username) {
        System.out.println("За какой месяц хотите посмотреть? (формат: 1-12)");
        int month = scanner.nextInt();
        System.out.println("Ваши показания за " + month + " - месяц:");
        for (MeterReading m : storage.getMeterReadings()) {
            if (storage.getUser(username).equals(m.getUser())) {
                if (m.getDate().getMonthValue() == month) {
                    System.out.println(" - " + m);
                }
            }
        }
        storage.getAudits().add(new Audit(username, "Просмотр истории за конкретный месяц", LocalDateTime.now()));
        commandList(username);
    }


    public void viewReadingHistory(String username) {
        User user = storage.getUser(username);
        System.out.println("Все показания пользователя " + user + ":");
        for (MeterReading m : storage.getMeterReadings()) {
            if (m.getUser().equals(user)) {
                System.out.println("\n \t - " + m);
            }
        }
        storage.getAudits().add(new Audit(username, "Просмотр истории всех показаний", LocalDateTime.now()));
        commandList(username);
    }
}
