package aspect;


import aspectj.UnauthorizedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by mallika on 3/2/15.
 */

@Aspect
public class Authorize {
    @Before("execution(public* aspectj.FileService.readFile(..))")
    public Object doAccessCheck() throws Throwable {
//    public Object doAccessCheck(ProceedingJoinPoint pjp) throws Throwable {
//        if (1==1) { throw new UnauthorizedException(); }
        //Object retVal = pjp.proceed();
       // return retVal;
        System.err.println("------> Before access check!!!");
        return new Object();
    }

    @Around("execution(public* aspectj.FileService.readFile(..))")
    public Object doAccessCheck(ProceedingJoinPoint pjp) throws Throwable {
//        if (1==1) { throw new UnauthorizedException(); }
        System.err.println("------> Around access check!!! (BEFORE)");
        Object retVal = pjp.proceed();
        System.err.println("------> Around access check!!! (AFTER)");
        return retVal;
    }
}
