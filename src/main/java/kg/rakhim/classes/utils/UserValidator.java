package kg.rakhim.classes.utils;


import kg.rakhim.classes.models.User;
import kg.rakhim.classes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(User.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userService.findByUsername(user.getUsername()).isPresent()){
            errors.rejectValue("username", "", "Такой пользователь уже существует");
        }
    }
}
