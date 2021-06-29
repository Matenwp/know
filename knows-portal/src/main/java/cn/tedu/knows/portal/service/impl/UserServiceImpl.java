package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.mapper.ClassroomMapper;
import cn.tedu.knows.portal.mapper.UserRoleMapper;
import cn.tedu.knows.portal.model.Classroom;
import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.UserRole;
import cn.tedu.knows.portal.service.IUserService;
import cn.tedu.knows.portal.vo.RegisterVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
}
