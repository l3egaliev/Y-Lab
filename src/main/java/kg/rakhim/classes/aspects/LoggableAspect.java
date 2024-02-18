package kg.rakhim.classes.aspects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Aspect
@Component
public class LoggableAspect {
    @Around("within(@kg.rakhim.classes.annotations.Loggable *) && execution(* * (..))")
    public Object logging(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        Object[] args = joinPoint.getArgs();
        Instant startTime = Instant.now();
        System.out.println("Method " + className + "." + methodName + " called with arguments: " + Arrays.toString(args));
        System.out.println("Method " + className + "." + methodName + " returned: " + joinPoint.proceed());
        Instant endTime = Instant.now();
        Duration duration = Duration.between(startTime, endTime);
        System.out.println("Method "+className+"."+methodName+" executed in: "+duration.toMillis()+"ms");
        return joinPoint.proceed();
    }
}