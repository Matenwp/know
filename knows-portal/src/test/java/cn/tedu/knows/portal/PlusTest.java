package cn.tedu.knows.portal;

import cn.tedu.knows.portal.mapper.TagMapper;
import cn.tedu.knows.portal.model.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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


}
