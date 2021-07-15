package cn.tedu.knows.sys.service.impl;


import cn.tedu.knows.commons.exception.ServiceException;
import cn.tedu.knows.commons.model.*;
import cn.tedu.knows.sys.mapper.ClassroomMapper;
import cn.tedu.knows.sys.mapper.UserMapper;
import cn.tedu.knows.sys.mapper.UserRoleMapper;
import cn.tedu.knows.sys.service.IUserService;
import cn.tedu.knows.sys.vo.RegisterVo;
import cn.tedu.knows.sys.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
//Ctrl+Alt+B 从接口自动跳转掉实现类
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ClassroomMapper classroomMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    //Spring容器中没有保存PasswordEncoder,我们自己实例化
    private PasswordEncoder encoder=new BCryptPasswordEncoder();

    @Override
    @Transactional
    public void registerStudent(RegisterVo registerVo) {

        //1.验证邀请码正确性
        QueryWrapper<Classroom> query=new QueryWrapper<>();
        query.eq("invite_code",registerVo.getInviteCode());
        Classroom classroom=classroomMapper.selectOne(query);
        if(classroom==null){
            throw new ServiceException("邀请码不正确!");
        }
        //2.验证手机号(用户名)可用
        User user=userMapper.findUserByUsername(
                                    registerVo.getPhone());
        if(user!=null){
            throw new ServiceException("手机号已经被注册过!");
        }
        //3.实例化User对象
        User u=new User();
        //4.密码加密
        String pwd="{bcrypt}"+encoder.encode(
                                    registerVo.getPassword());
        //5.User对象赋值
        u.setUsername(registerVo.getPhone())
                .setNickname(registerVo.getNickname())
                .setPassword(pwd)
                .setClassroomId(classroom.getId())
                .setCreatetime(LocalDateTime.now())
                .setEnabled(1)
                .setLocked(0)
                .setType(0);
        //6.执行新增
        int num=userMapper.insert(u);
        if(num!=1){
            throw new ServiceException("服务器忙,请稍后再试");
        }
        //7.新增用户和角色关系表的数据
        UserRole userRole=new UserRole();
        userRole.setRoleId(2);
        userRole.setUserId(u.getId());
        num=userRoleMapper.insert(userRole);
        if(num!=1){
            throw new ServiceException("服务器忙,请稍后再试");
        }
    }

    //声明计时器
    private Timer timer=new Timer();

    {
        //这个区域叫初始化代码块,每次实例化当前类型对象时
        //会在构造方法运行之前运行其中的代码
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (teachers){
                    synchronized (teacherMap){
                        teachers.clear();
                        teacherMap.clear();
                    }
                }
                System.out.println("缓存已清空");
            }
        },30*60*1000,30*60*1000);
    }



    private List<User> teachers=new CopyOnWriteArrayList<>();
    private Map<String,User> teacherMap=new ConcurrentHashMap<>();

    @Override
    public List<User> getTeachers() {
        if(teachers.isEmpty()) {
            synchronized (teachers) {
                if(teachers.isEmpty()) {
                    QueryWrapper<User> query = new QueryWrapper<>();
                    query.eq("type", 1);
                    teachers = userMapper.selectList(query);
                    for(User user:teachers){
                        teacherMap.put(user.getNickname(),user);
                    }
                }
            }
        }
        //别忘了返回!!!
        return teachers;
    }

    @Override
    public Map<String, User> getTeacherMap() {
        if(teacherMap.isEmpty()){
            getTeachers();
        }
        return teacherMap;
    }


    @Autowired
    private RestTemplate restTemplate;
    @Override
    public UserVo getCurrentUserVo(String username) {
        //获得当前用户的基本信息
        UserVo userVo=userMapper.findUserVoByUsername(username);
        //按用户id查询这个用户的提问数
        String url=
            "http://faq-service/v2/questions/count?userId={1}";
        Integer num=restTemplate.getForObject(
                url,Integer.class,userVo.getId());
        //将提问数赋值给userVo对象
        userVo.setQuestions(num);
        //下面自己做收藏数的代码
        //别忘了返回userVo!!!
        return userVo;
    }

    @Override
    public User getUserByUserName(String username) {
        return userMapper.findUserByUsername(username);
    }

    @Override
    public List<Permission> getUserPermissionsById(Integer id) {
        return userMapper.findUserPermissionsById(id);
    }
    @Override
    public List<Role> getUserRolesById(Integer id) {
        return userMapper.findUserRolesById(id);
    }
}
