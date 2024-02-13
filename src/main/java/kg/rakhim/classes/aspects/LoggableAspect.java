package kg.rakhim.classes.aspects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Aspect
public class LoggableAspect {
    @Pointcut("within(@kg.rakhim.classes.annotations.Loggable *) && execution(* * (..))")
    public void annotatedByLoggable() {}

    @Around("annotatedByLoggable()")
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