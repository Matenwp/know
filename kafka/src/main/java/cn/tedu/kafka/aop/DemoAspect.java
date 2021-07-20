package cn.tedu.kafka.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
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

    //添加一个增强功能,在切入点方法运行之前运行代码
    @Before("pointCut()")
    public void before(){
        System.out.println("before在控制器方法运行前运行!");
    }

}
