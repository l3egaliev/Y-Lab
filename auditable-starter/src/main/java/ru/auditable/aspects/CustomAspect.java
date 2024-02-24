package ru.auditable.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.auditable.data.UserContext;
import ru.auditable.model.Audit;
import ru.auditable.service.AuditService;

@Aspect
@Component
public class CustomAspect {
    private final AuditService auditService;
    private final UserContext context;

    public CustomAspect(AuditService auditService, UserContext context) {
        this.auditService = auditService;
        this.context = context;
    }

    @Around("@within(ru.auditable.annotations.EnableXXX))")
    public Object enable(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().getName();
        String userName = getCurrentUserName();
        String action = getAction(method);
        System.out.println("Вызван метод: " + method);
        if (userName.isEmpty() || action.isEmpty()){
            System.out.println("Аудит не сохраняется");
        }else {
            auditService.save(new Audit(userName, action));
            System.out.println("Аудит успешно сохранен");
            System.out.println(action);
        }
        return joinPoint.proceed();
    }

    private String getCurrentUserName() {
        if (context.getCurrentUser() == null || context.getCurrentUser().getUsername() == null){
            return "";
        }
        return context.getCurrentUser().getUsername();
    }
    private String getAction(String method){
        if (context.getCurrentUser() == null || context.getCurrentUser().getActions() == null
                || !context.getCurrentUser().getActions().containsKey(method)){
            return "";
        }
        return context.getCurrentUser().getActions().get(method);
    }
}
