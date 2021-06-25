package cn.tedu.knows.portal;

import cn.tedu.knows.portal.mapper.TagMapper;
import cn.tedu.knows.portal.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class KnowsPortalApplicationTests {

    @Autowired(required = false)
    TagMapper tagMapper;

    @Test
    void testPlus(){
        /*Tag tag=new Tag();
        tag.setId(21);
        tag.setName("MybatisPlus");
        tag.setCreateby("admin");
        tag.setCreatetime("2021-6-25 10:00:00");
        int num=tagMapper.insert(tag);
        System.out.println("num:"+num);
        System.out.println("新增完毕");*/

        //Tag tag=tagMapper.selectById(21);
        //System.out.println(tag);

        List<Tag> tags=tagMapper.selectList(null);
        for(Tag t : tags){
            System.out.println(t);
        }

        /*int num=tagMapper.deleteById(21);
        System.out.println(num);*/





    }




    @Test
    void contextLoads() {
        Tag tag=new Tag();
        tag.setId(1);
        tag.setName("java基础");
        System.out.println("id:"+tag.getId());
        System.out.println("name:"+tag.getName());
        System.out.println(tag);
        tag.record();
    }

}
