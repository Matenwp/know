package cn.tedu.kafka.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class DemoAspect {
    //步骤1,明确我们要增强功能的方法是哪一个
    //定义切入点既指定要增强代码的切面位置
    @Pointcut("execution(" +
       "public * cn.tedu.kafka.controller.DemoController.test(..))")
    //下面的方法仅仅是需要它的方法名做切入点名称,
    // 这个方法没有任何意义,不需要编写代码
    public void pointCut(){}

    //步骤2:
    //添加一个增强功能,在切入点方法运行之前运行代码
    @Before("pointCut()")
    public void before(JoinPoint joinPoint){
        System.out.println("before在控制器方法运行前运行!");
        //获得方法信息
        Signature method=joinPoint.getSignature();
        //输出方法信息
        System.out.println("前置增强:"+method);
    }
    @After("pointCut()")
    public void after(JoinPoint joinPoint){
        Signature method=joinPoint.getSignature();
        System.out.println("后置增强:"+method);
    }
    //异常增强,在方法发生异常时才运行
    //如果方法运行正常,没有运行过程
    @AfterThrowing("pointCut()")
    public void throwing(JoinPoint joinPoint){
        Signature method=joinPoint.getSignature();
        System.out.println("异常增强"+method);
    }
    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint)
            throws Throwable {
        //方法运行前
        System.out.println("环绕增强前置");
        //通过下面代码放行程序运行到目标方法
        Object re=joinPoint.proceed();
        //方法运行后
        System.out.println("环绕增强后置");
        return re;
    }
}
