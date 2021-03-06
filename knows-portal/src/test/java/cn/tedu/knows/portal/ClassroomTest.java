package cn.tedu.knows.portal;

import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.mapper.ClassroomMapper;
import cn.tedu.knows.portal.mapper.QuestionMapper;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.Classroom;
import cn.tedu.knows.portal.model.Question;
import cn.tedu.knows.portal.model.Role;
import cn.tedu.knows.portal.service.IQuestionService;
import cn.tedu.knows.portal.service.IUserService;
import cn.tedu.knows.portal.service.impl.UserServiceImpl;
import cn.tedu.knows.portal.vo.RegisterVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

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

    @Autowired
    IUserService userService;
    @Test
    void addStu(){
        RegisterVo registerVo=new RegisterVo()
                .setPhone("13833148996")
                .setNickname("马晕")
                .setPassword("123123")
                .setInviteCode("JSD2002-55416");
        try {
            userService.registerStudent(registerVo);
        }catch (ServiceException e){
            System.out.println(e.getMessage());
        }
        System.out.println("完成");

    }

    @Resource
    IQuestionService questionService;
    @Test
    public void count(){
        int count=questionService.countQuestionsByUserId(11);
        System.out.println(count);
    }

    @Resource
    UserMapper userMapper;
    @Test
    void role(){
        List<Role> roles=userMapper.findUserRolesById(3);
        for(Role r:roles){
            System.out.println(r);
        }
    }


    @Resource
    QuestionMapper questionMapper;
    @Test
    public void teacherList(){
        List<Question> list=questionMapper
                .findTeacherQuestions(3);
        list.forEach(question -> System.out.println(question));
    }




}
