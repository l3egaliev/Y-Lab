package kg.rakhim.classes.aspects;

import kg.rakhim.classes.context.ApplicationContext;
import kg.rakhim.classes.context.UserContext;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.service.AuditService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

@Aspect
public class AuditAspect {
    private final AuditService auditService;

    public AuditAspect() {
        this.auditService = (AuditService) ApplicationContext.getContext("auditsService");
    }

    @Pointcut("within(@kg.rakhim.classes.annotations.Auditable *) && execution(* * (..))")
    public void annotatedByAuditable() {}

    @Around("annotatedByAuditable()")
    public Object auditCall(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().getName();
        String userName = getCurrentUserName();
        String action = getAction(method);
        System.out.println("Вызван метод: " + method);
        if (userName.equals("null") || action.equals("null")){
            System.out.println("аудит не сохраняется");
        }else {
            auditService.save(new Audit(userName, action));
            System.out.println("Аудит успешно сохранен");
        }
        System.out.println(Arrays.toString(joinPoint.getArgs()));

        // Выполняем метод
        return joinPoint.proceed();
    }

    private String getCurrentUserName() {
        if (UserContext.getCurrentUser() == null){
            return "null";
        }
        return UserContext.getCurrentUser().getUsername();
    }
    private String getAction(String method){
        if (UserContext.getCurrentUser() == null){
            return "null";
        }
        return UserContext.getCurrentUser().getAction().get(method);
    }
}

