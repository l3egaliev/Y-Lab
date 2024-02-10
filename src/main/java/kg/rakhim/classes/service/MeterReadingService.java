package kg.rakhim.classes.service;

import kg.rakhim.classes.dao.MeterReadingDAO;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.models.MeterType;
import kg.rakhim.classes.out.ConsoleOut;
import kg.rakhim.classes.repository.MeterReadingRepository;

import java.time.LocalDateTime;
import java.util.*;

import static kg.rakhim.classes.in.ConsoleIn.commandList;

/**
 * Сервис для работы с показаниями счетчиков.
 */
public class MeterReadingService implements MeterReadingRepository {
    private final MeterReadingDAO dao;
    private final MeterTypesService typesService;

    /**
     * Создает экземпляр класса MeterReadingService с указанным DAO и сервисом типов счетчиков.
     *
     * @param dao           DAO для работы с данными о показаниях счетчиков
     * @param typesService  сервис для работы с типами счетчиков
     */
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
     * Отправляет показания счетчика от указанного пользователя.
     *
     * @param username      имя пользователя, подающего показания
     * @param userService   сервис для работы с пользователями
     * @param auditService  сервис для аудита действий пользователей
     * @param scanner       сканер для считывания ввода пользователя
     */
    public void sendCounterReading(String username, UserService userService, AuditService auditService, Scanner scanner) {
        MeterReading meterReading = new MeterReading();
        meterReading.setUser(userService.findByUsername(username));
        ConsoleOut.printLine("Для подачи показаний вводите следующие данные: ");
        scanTypeOfMeterReading(meterReading, userService, auditService, scanner);
        ConsoleOut.printLine("\t- Значение (формат: 4 цифр, пример - 1000)");
        int value = Integer.parseInt(scanner.next());
        if(Integer.toString(value).length()<4){
            ConsoleOut.printLine("Неправильно! Не меньше 4 цифр");
            sendCounterReading(username, userService, auditService, scanner);
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
     * Считывает и выбирает тип показания счетчика.
     *
     * @param meterReading  объект MeterReading, для которого необходимо выбрать тип показания
     * @param userService   сервис для работы с пользователями
     * @param auditService  сервис для аудита действий пользователей
     * @param scanner       сканер для считывания ввода пользователя
     */
    public void scanTypeOfMeterReading(MeterReading meterReading, UserService userService,
                                       AuditService auditService, Scanner scanner) {
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
                sendCounterReading(meterReading.getUser().getUsername(), userService, auditService, scanner);
            }
        }
    }

    /**
     * Отображает доступные типы показаний.
     *
     * @param letterAndType  карта соответствия первой буквы и типа показания
     */
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

    /**
     * Проверяет, существуют ли уже показания счетчика для данного пользователя в текущем месяце и типом счетчика.
     *
     * @param meterReading  объект MeterReading, для которого необходимо проверить существование показаний
     * @return              1, если показания уже существуют, иначе 0
     */
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