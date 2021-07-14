package cn.tedu.knows.sys.interceptor;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
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
        // 将map中的authorities转换为List
        List<String> list=(List<String>)map.get("authorities");
        //将上面的list中的内容保存到String[] auth中
        String[] auth=list.toArray(new String[0]);
        //构建用户详情对象
        UserDetails userDetails= User.builder()
                .username(map.get("user_name").toString())
                .password("")
                .authorities(auth)
                .build();
        //将UserDetails对象保存到一个可以和Spring-Security交互的对象中
        PreAuthenticatedAuthenticationToken authenticationToken=
                new PreAuthenticatedAuthenticationToken(
                        userDetails,userDetails.getPassword(),
                        AuthorityUtils.createAuthorityList(auth));
        //将本次解析的用户详情和当前请求关联
        //关联之后才能在后面的控制器中获得用户详情
        authenticationToken.setDetails(
                new WebAuthenticationDetails(request));
        //将当前用户详情保存到Spring-Security上下文
        SecurityContextHolder.getContext()
                .setAuthentication(authenticationToken);
        //千万别忘了返回true!!!!
        return true;
    }
}
