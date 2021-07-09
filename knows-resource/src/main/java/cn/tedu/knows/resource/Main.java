package cn.tedu.knows.resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Main {
    @Value("${var}")
    private String var;

    public void show(){
        System.out.println(var);
    }

}
