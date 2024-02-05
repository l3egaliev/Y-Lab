package kg.rakhim.classes.service;

import kg.rakhim.classes.context.ApplicationContext;
import kg.rakhim.classes.dao.MeterReadingDAO;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.models.MeterType;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.out.ConsoleOut;
import kg.rakhim.classes.repository.MeterReadingRepository;

import java.time.LocalDateTime;
import java.util.*;

import static kg.rakhim.classes.in.ConsoleIn.commandList;

public class MeterReadingService implements MeterReadingRepository {
    private final MeterReadingDAO dao;
    private final MeterTypesService typesService;

    public MeterReadingService(MeterReadingDAO dao, MeterTypesService typesService) {
        this.dao = dao;
        this.typesService = typesService;
    }

    // TODO
    @Override
    public Optional<MeterReading> findById(int id) {
        return Optional.of(dao.get(id));
    }

    @Override
    public List<MeterReading> findAll() {
        return dao.getAll();
    }

    @Override
    public void save(MeterReading e) {
        dao.save(e);
    }


    /**
     * Реализация подачи показаний.
     *
     * @param username имя пользователя, подающего показания
     */
    public void sendCounterReading(String username, UserService userService, AuditService auditService) {
        Scanner scanner = new Scanner(System.in);
        MeterReading meterReading = new MeterReading();
        meterReading.setUser(userService.findByUsername(username));
        ConsoleOut.printLine("Для подачи показаний вводите следующие данные: ");
        scanTypeOfMeterReading(meterReading, userService, auditService);
        ConsoleOut.printLine("\t- Значение (формат: 4 цифр, пример - 1000)");
        int value = Integer.parseInt(scanner.next());
        if(Integer.toString(value).length()<4){
            ConsoleOut.printLine("Неправильно! Не меньше 4 цифр");
            sendCounterReading(username, userService, auditService);
        }
        meterReading.setValue(value);
        meterReading.setDate(LocalDateTime.now());
        if (isExistsReading(meterReading) == 1){
            commandList(username);
        }else{
            save(meterReading);
            auditService.save(new Audit(username, "Подача показания: " + meterReading, LocalDateTime.now()));
            ConsoleOut.printLine("Показание успешно отправлено");
        }
        commandList(username);
    }

    /**
     * Метод для сканирования и выбора типа показания счетчика.
     *
     * @param meterReading Объект MeterReading, для которого необходимо выбрать тип показания.
     */
    public void scanTypeOfMeterReading(MeterReading meterReading, UserService userService, AuditService auditService) {
        Scanner scanner = new Scanner(System.in);
        Map<String, String> letterAndType = new HashMap<>();
        displayTypes(letterAndType);
        // Получение выбора от пользователя
        String type = scanner.next().toLowerCase();

        // Установка выбранного типа показания в объекте MeterReading
        for (String key : letterAndType.keySet()) {
            if (key.equals(type)) {
                String selectedType = letterAndType.get(key);
                meterReading.setMeterType(new MeterType(selectedType));
            }else if(!letterAndType.containsKey(type)){
                ConsoleOut.printLine("Неправильное значение");
                sendCounterReading(meterReading.getUser().getUsername(), userService, auditService);
            }
        }
    }

    private void displayTypes(Map<String, String> letterAndType){
        // Создание карты для хранения соответствия первой буквы и типа показания
        ConsoleOut.print("\t- Тип показания (");
        // Вывод доступных типов показаний и соответствующих первых букв
        for (MeterType m : typesService.findAll()) {
            String firstLetter = String.valueOf(m.getType().charAt(0));
            ConsoleOut.print("" + m.getType() + " - " + firstLetter + " | ");
            letterAndType.put(firstLetter, m.getType());
        }
        ConsoleOut.printLine(")");
    }

    public int isExistsReading(MeterReading meterReading){
        int res = 0;
        // Проверить не отправлял ли пользователь в этом месяце показаний
        for (MeterReading m : findAll()) {
            if (m.getUser().getUsername().equals(meterReading.getUser().getUsername()) &&
                    m.getDate().getMonthValue() == LocalDateTime.now().getMonthValue()
                    && m.getMeterType().getType().equals(meterReading.getMeterType().getType())) {
                ConsoleOut.printLine("Вы в этом месяце уже отправляли показание: " + meterReading.getMeterType() +
                        "\n---\n" + m);
                res = 1;
                break;
            }
        }
        return res;
    }
}
