package cn.tedu.knows.faq.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeAspect {
    //定义切入点
    //service包下所有类的所有方法
    @Pointcut("execution(* cn.tedu.knows.faq.service..*.*(..))")
    public void timePoint(){}

    @Around("timePoint()")
    public Object time(ProceedingJoinPoint joinPoint) throws Throwable {
        //记录方法运行开始的时间
        long start=System.currentTimeMillis();
        //方法运行
        Object o=joinPoint.proceed();
        //记录方法运行结束的时间
        long end=System.currentTimeMillis();
        //计算时间差并输出
        String methodName=joinPoint.getSignature().getName();
        System.out.println(methodName+"方法用时:"+(end-start));
        return o;
    }


}
