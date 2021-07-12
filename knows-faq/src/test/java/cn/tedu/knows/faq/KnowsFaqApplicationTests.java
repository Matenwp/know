package cn.tedu.knows.faq;

import cn.tedu.knows.commons.model.Tag;
import cn.tedu.knows.commons.model.User;
import cn.tedu.knows.faq.mapper.TagMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class KnowsFaqApplicationTests {

    //要想操作redis要添加下面的依赖
    //RedisTemplate<[key类型],[value类型]>
    //一般情况下key类型都是string
    // value类型具体问题具体分析
    @Resource
    RedisTemplate<String, List<Tag>> redisTemplate;
    @Resource
    TagMapper tagMapper;
    @Test
    void contextLoads() {
        //全查所有标签
        List<Tag> tags=tagMapper.selectList(null);
        redisTemplate.opsForValue().set("alltag",tags);
        //向redis中保存一个数据
        /*redisTemplate.opsForValue().set("myname","张三丰");*/
        System.out.println("ok");
    }

    @Test
    void getName(){
        List<Tag> tags=redisTemplate.opsForValue().get("alltag");
        for(Tag t: tags){
            System.out.println(t);
        }
        /*String string=redisTemplate.opsForValue().get("myname");
        System.out.println(string);*/
    }


    @Resource
    RestTemplate restTemplate;

    @Test
    void testRibbon(){
        //定义访问控制器的url
        //sys-service:是要调用的微服务的注册名称
        // /v1/auth/demo:是要访问资源的路径
        String url="http://sys-service/v1/auth/demo";
        //返回值根据请求的方法的返回值而定
        //getForObject方法第一个参数是上面定的url
        //                第二个参数就是返回值类型的反射对象
        String msg=restTemplate.getForObject(url,String.class);
        System.out.println(msg);

    }

    @Test
    void getUser(){
        //注意参数的传递
        //需要参数时使用?进行分割username对应sys控制器参数的名称
        //{1}表示本次请求的第一个参数
        String url="http://sys-service/v1/auth/user?username={1}";
        // 从第3个参数开始向{1}中的占位符中赋值(框架规定,无法解释)
        User user=restTemplate.getForObject(
                url,User.class,"st2");
        System.out.println(user);

    }


}
