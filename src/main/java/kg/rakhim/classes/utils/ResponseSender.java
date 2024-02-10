package kg.rakhim.classes.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class ResponseSender {
    public static boolean validation(Object obj, HttpServletResponse response, ObjectMapper mapper) throws IOException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(obj);
        JSONObject errorMessage = new JSONObject();
        if (!violations.isEmpty()) {
            // Обработка ошибок валидации
            for (ConstraintViolation<Object> violation : violations) {
                String propertyPath = String.valueOf(violation.getPropertyPath());
                String message = violation.getMessage();
                errorMessage.put(propertyPath, message);
            }
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            mapper.writeValue(response.getWriter(), errorMessage.toString());
            return false;
        } else {
            return true;
        }
    }

    public static void sendRegisterMessage(Map<Boolean, JSONObject> result, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        if (result.containsKey(true)){
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(result.get(true).toString());
        }else{
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write(result.get(false).toString());
        }
    }
}
