package cn.tedu.knows.sys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    @GetMapping("/demo")
    public String demo(){
        return "sys:Hello World!!!";
    }
}
