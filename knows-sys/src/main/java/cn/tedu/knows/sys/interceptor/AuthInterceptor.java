package cn.tedu.knows.sys.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private RestTemplate restTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取客户端发送到服务器的Token
        //从request中获得客户端发送来的名为accessToken的参数
        String token=request.getParameter("accessToken");
        //将上面的token作为参数,发送ribbon请求到授权服务器
        //获得用户详情信息
        String url=
          "http://auth-service/auth/oauth/check_token?token={1}";
        Map<String,Object> map=restTemplate.getForObject(
                url , Map.class , token);
        System.out.println(map.get("user_name"));
        System.out.println(map.get("authorities")+","
                        +map.get("authorities").getClass().getName());


        return false;
    }
}
