package cn.tedu.knows.faq.service.impl;


import cn.tedu.knows.commons.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RibbonClient {
    @Autowired
    private RestTemplate restTemplate;

    public User getUser(String username){
        String url="http://sys-service/v1/auth/user?username={1}";
        User user=restTemplate.getForObject(
                url,User.class,username);
        return user;
    }
}
