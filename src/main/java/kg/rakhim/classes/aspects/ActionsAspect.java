package kg.rakhim.classes.aspects;

import kg.rakhim.classes.context.ApplicationContext;
import kg.rakhim.classes.context.UserContext;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.service.AuditService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

@Aspect
public class ActionsAspect {
    private final AuditService auditService;
    public ActionsAspect() {
        this.auditService = (AuditService) ApplicationContext.getContext("auditsService");
    }

    @Pointcut("within(@kg.rakhim.classes.annotations.AuditableAction *) && execution(* * (..))")
    public void annotatedByAuditableAction() {}

    @AfterReturning("annotatedByAuditableAction()")
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
