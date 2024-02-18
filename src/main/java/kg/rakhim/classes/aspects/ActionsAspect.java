package kg.rakhim.classes.aspects;

import kg.rakhim.classes.context.UserContext;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.service.AuditService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ActionsAspect {
    private final AuditService auditService;
    public ActionsAspect(AuditService auditService) {
        this.auditService = auditService;
    }
    @AfterReturning(pointcut = "within(@kg.rakhim.classes.annotations.AuditableAction *) && execution(* * (..))")
    public void auditActionsCall(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getName();
        String userName = getCurrentUserName();
        String action = getAction(method);
        System.out.println("Вызван метод: " + method);
        if (userName.isEmpty() || action.isEmpty()){
            System.out.println("аудит не сохраняется");
        }else {
            auditService.save(new Audit(userName, action));
            System.out.println("Аудит успешно сохранен");
        }
        System.out.println(Arrays.toString(joinPoint.getArgs()));
    }

    private String getCurrentUserName() {
        if (UserContext.getCurrentUser() == null || UserContext.getCurrentUser().getUsername() == null){
            return "";
        }
        return UserContext.getCurrentUser().getUsername();
    }
    private String getAction(String method){
        if (UserContext.getCurrentUser() == null || UserContext.getCurrentUser().getAction() == null
                || !UserContext.getCurrentUser().getAction().containsKey(method)){
            return "";
        }
        return UserContext.getCurrentUser().getAction().get(method);
    }
}
