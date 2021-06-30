package cn.tedu.knows.portal;

import cn.tedu.knows.portal.mapper.TagMapper;
import cn.tedu.knows.portal.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class PlusTest {
    @Autowired(required = false)
    TagMapper tagMapper;
    @Test
    void getTag(){
        List<Tag> tags=tagMapper.selectList(null);
        for(Tag t:tags){
            System.out.println(t);
        }
    }
    @Test
    void add(){
        /*Tag tag=new Tag()
                .setName("杂项")
                .setCreateby("admin")
                .setCreatetime(LocalDateTime.now());
        int num=tagMapper.insert(tag);
        //num表示本次数据库操作影响数据库的行数
        System.out.println(num);*/

        tagMapper.deleteById(22);

    }

    @Test
    void abc(){
        System.out.println(System.currentTimeMillis()/1000/60/60/24/365);
    }

}
