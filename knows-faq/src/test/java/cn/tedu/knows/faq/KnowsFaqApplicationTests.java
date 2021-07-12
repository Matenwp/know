package cn.tedu.knows.faq;

import cn.tedu.knows.commons.model.Tag;
import cn.tedu.knows.faq.mapper.TagMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

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

}
