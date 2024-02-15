package kg.rakhim.classes.service.actions.admins;

import kg.rakhim.classes.annotations.AuditableAction;
import kg.rakhim.classes.context.UserContext;
import kg.rakhim.classes.context.UserDetails;
import kg.rakhim.classes.service.MeterTypesService;
import kg.rakhim.classes.service.UserService;
import org.json.JSONObject;

import java.util.Map;
@AuditableAction
public class EditorReadings {
    private final UserService userService;
    private final MeterTypesService typesService;
    public EditorReadings(UserService userService, MeterTypesService typesService) {
        this.userService = userService;
        this.typesService = typesService;
    }

    /**
     * Добавление нового тип счетчика
     *
     * @param username    имя пользователя, чьи показания нужно просмотреть
     */
    public Map<Boolean, JSONObject> setNewType(String username, String newType){
        JSONObject message = new JSONObject();
        boolean status;
        if (userService.isAdmin(username)){
            if(typesService.saveType(newType)){
                message.put("message", "Новый тип успешно добавлен");
                status = true;
                UserDetails userDetails = UserContext.getCurrentUser();
                userDetails.setAction(Map.of("setNewType", "Добавление нового тип счетчика"));
            }else if(typesService.isExistsType(newType)){
                message.put("message", "Такой тип уже существует");
                status = false;
            }else{
                message.put("message", "Ошибка, Попробуйте еще раз");
                status = false;
            }
        }else{
            message.put("message", "У вас нет прав доступа");
            status = false;
        }
        return Map.of(status, message);
    }
}
