package cn.tedu.knows.portal;

import cn.tedu.knows.portal.mapper.ClassroomMapper;
import cn.tedu.knows.portal.model.Classroom;
import cn.tedu.knows.portal.service.IUserService;
import cn.tedu.knows.portal.service.impl.UserServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClassroomTest {


    @Autowired
    ClassroomMapper classroomMapper;
    @Test
    void query(){
        //创建一个QueryWrapper的对象
        QueryWrapper<Classroom> query=new QueryWrapper<>();
        //QueryWrapper是另外一种设置查询条件的方式
        query.eq("invite_code","JSD2002-525416");
        //运行查询,查询结果最多只能一行的查询,使用selectOne
        Classroom classroom=classroomMapper.selectOne(query);
        System.out.println(classroom);

        //gt(great than)
        //lt(less  than)
        //ge(great equals)
        //le(less  equals)
        //ne(not   equals)

    }



}
