package kg.rakhim.classes.service.actions.users;

import kg.rakhim.classes.models.MeterReading;
import kg.rakhim.classes.models.MeterType;
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
               message.put("message", "Пользователь с именем " + meterReading.getUser().getUsername() + " не найден");
               key = false;
           }else {
               boolean isExists = isExistsReading(meterReading);
               MeterType type = meterReading.getMeterType();
               if(typesService.getTypeId(type) == null){
                   message.put("message", type.getType()+" - Такой тип показания не существует");
                   key = false;
               }else if (!isExists) {
                   meterReadingService.save(meterReading);
                   message.put("message", "Показание успешно отправлено");
                   key = true;
               } else {
                   message.put("message", "Вы в этом месяце уже отправляли показание: " + meterReading.getMeterType());
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
    /**
     * Проверяет, существуют ли уже показания счетчика для данного пользователя в текущем месяце и типом счетчика.
     *
     * @param meterReading  объект MeterReading, для которого необходимо проверить существование показаний
     * @return              true, если показания уже существуют, иначе false
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
