package cn.tedu.knows.portal;

import cn.tedu.knows.portal.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KnowsPortalApplicationTests {

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
