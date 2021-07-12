package cn.tedu.knows.sys.controller;


import cn.tedu.knows.commons.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice注解表示当前类是一个对控制器增加额外功能的类
//实际上,这个类可以为控制器增加很多类型的额外功能
//我们的课程中学习的和企业开发经常使用的一样,就是对控制器异常的处理功能
@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    //编写一个异常处理的方法
    @ExceptionHandler
    //这个方法的方法名随意,但是参数决定了这个方法处理什么类型的异常
    //既,当这个方法是处理当控制器中发生ServiceException的方法
    public String handleServiceException(ServiceException e){
        log.error("业务发生了异常",e);
        return e.getMessage();
    }

    @ExceptionHandler
    public String handleException(Exception e){
        log.error("其它异常",e);
        return e.getMessage();
    }


}
