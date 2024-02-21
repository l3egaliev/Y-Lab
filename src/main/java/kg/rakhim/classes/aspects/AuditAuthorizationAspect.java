package kg.rakhim.classes.aspects;

import kg.rakhim.classes.context.UserContext;
import kg.rakhim.classes.models.Audit;
import kg.rakhim.classes.service.AuditService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditAuthorizationAspect {

    private final AuditService auditService;
    private final UserContext userContext;

    @Autowired
    public AuditAuthorizationAspect(AuditService auditService, UserContext userContext) {
        this.auditService = auditService;
        this.userContext = userContext;
    }

    @Pointcut("@within(kg.rakhim.classes.annotations.AuditableForAuth) && execution(* *(..))")
    public void annotatedByAuditableForAuth() {}

    @AfterReturning("annotatedByAuditableForAuth()")
    public void auditCall(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getName();
        String userName = getCurrentUserName();
        String action = getAction(method);

        System.out.println("Вызван метод: " + method);

        if (userName.isEmpty() || action.isEmpty()) {
            System.out.println("аудит не сохраняется");
        } else {
            auditService.save(new Audit(userName, action));
            System.out.println("Аудит успешно сохранен");
        }
    }

    private String getCurrentUserName() {
        if (userContext.getCurrentUser() == null || userContext.getCurrentUser().getUsername() == null) {
            return "";
        }
        return userContext.getCurrentUser().getUsername();
    }

    private String getAction(String method) {
        if (userContext.getCurrentUser() == null || userContext.getCurrentUser().getAction() == null
                || !userContext.getCurrentUser().getAction().containsKey(method)) {
            return "";
        }
        return userContext.getCurrentUser().getAction().get(method);
    }
}
