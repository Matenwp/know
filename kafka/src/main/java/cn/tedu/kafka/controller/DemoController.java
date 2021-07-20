package cn.tedu.kafka.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    //localhost:8082/demo
    @GetMapping("/demo")
    public String test(String msg){
        System.out.println("demo运行");
        String s=null;
        s.length();
        return "hello world!!!";
    }
}
