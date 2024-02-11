package kg.rakhim.classes.service.actions;

import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.models.User;
import kg.rakhim.classes.service.MeterReadingService;
import kg.rakhim.classes.service.MeterTypesService;
import kg.rakhim.classes.service.UserService;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

public class ReadingSender {
    private final UserService userService;
    private final MeterReadingService meterReadingService;
    private final MeterTypesService typesService;

    public ReadingSender(UserService userService, MeterReadingService meterReadingService, MeterTypesService typesService) {
        this.userService = userService;
        this.meterReadingService = meterReadingService;
        this.typesService = typesService;
    }

    /**
     * Отправляет показания счетчика от указанного пользователя.
     *
     * @param meterReading   Новая подача показания
     */
    public Map<Boolean, JSONObject> sendCounterReading(MeterReading meterReading) {
        JSONObject message = new JSONObject();
        boolean key;
        boolean userNotPresent = isEmptyUser(meterReading);
           if(userNotPresent) {
               message.put("message:", "Пользователь с именем " + meterReading.getUser().getUsername() + " не найден");
               key = false;
           }else {
               boolean isExists = isExistsReading(meterReading);
               if (!isExists) {
                   if (!isNotExistsType(meterReading.getMeterType().getType())) {
                       meterReadingService.save(meterReading);
                       message.put("message:", "Показание успешно отправлено");
                       key = true;
                   }else{
                       message.put("message:",meterReading.getMeterType().getType()+ "Такой тип показания не принимается");
                       key = false;
                   }
               }else {
                   message.put("message:", "Вы в этом месяце уже отправляли показание: " + meterReading.getMeterType());
                   key = false;
               }
           }
           return Map.of(key, message);
    }

    private boolean isEmptyUser(MeterReading meterReading) {
        String username = meterReading.getUser().getUsername();
        Optional<User> userOptional = userService.findByUsername(username);
        return userOptional.isEmpty();
    }
//    /**
//     * Считывает и выбирает тип показания счетчика.
//     *
//     * @param meterReading  объект MeterReading, для которого необходимо выбрать тип показания
//     * @param userService   сервис для работы с пользователями
//     * @param auditService  сервис для аудита действий пользователей
//     * @param scanner       сканер для считывания ввода пользователя
//     */
//    public void scanTypeOfMeterReading(MeterReading meterReading, UserService userService,
//                                       AuditService auditService, Scanner scanner) {
//        Map<String, String> letterAndType = new HashMap<>();
//        displayTypes(letterAndType);
//        // Получение выбора от пользователя
//        String type = scanner.next().toLowerCase();
//
//        // Установка выбранного типа показания в объекте MeterReading
//        for (String key : letterAndType.keySet()) {
//            if (key.equals(type)) {
//                String selectedType = letterAndType.get(key);
//                meterReading.setMeterType(new MeterType(selectedType));
//            }else if(!letterAndType.containsKey(type)){
//                ConsoleOut.printLine("Неправильное значение");
//                sendCounterReading(meterReading.getUser().getUsername(), userService, auditService, scanner);
//            }
//        }
//    }
//
//    /**
//     * Отображает доступные типы показаний.
//     *
//     * @param letterAndType  карта соответствия первой буквы и типа показания
//     */
//    private void displayTypes(Map<String, String> letterAndType){
//        // Создание карты для хранения соответствия первой буквы и типа показания
//        ConsoleOut.print("\t- Тип показания (");
//        // Вывод доступных типов показаний и соответствующих первых букв
//        for (MeterType m : typesService.findAll()) {
//            String firstLetter = String.valueOf(m.getType().charAt(0));
//            ConsoleOut.print("" + m.getType() + " - " + firstLetter + " | ");
//            letterAndType.put(firstLetter, m.getType());
//        }
//        ConsoleOut.printLine(")");
//    }
    private boolean isNotExistsType(String type){
        return typesService.findByType(type).isEmpty();
    }
    /**
     * Проверяет, существуют ли уже показания счетчика для данного пользователя в текущем месяце и типом счетчика.
     *
     * @param meterReading  объект MeterReading, для которого необходимо проверить существование показаний
     * @return              1, если показания уже существуют, иначе 0
     */
    private boolean isExistsReading(MeterReading meterReading){
        boolean res = false;
        // Проверить не отправлял ли пользователь в этом месяце показаний
        for (MeterReading m : meterReadingService.findAll()) {
            if (m.getUser().getUsername().equals(meterReading.getUser().getUsername()) &&
                    m.getDate().getMonthValue() == LocalDateTime.now().getMonthValue()
                    && m.getMeterType().getType().equals(meterReading.getMeterType().getType())) {
                res = true;
                break;
            }
        }
        return res;
    }
}
