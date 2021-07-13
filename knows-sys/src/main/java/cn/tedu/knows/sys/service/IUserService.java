package cn.tedu.knows.sys.service;


import cn.tedu.knows.commons.model.Permission;
import cn.tedu.knows.commons.model.Role;
import cn.tedu.knows.commons.model.User;
import cn.tedu.knows.sys.vo.RegisterVo;
import cn.tedu.knows.sys.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
//Ctrl+Alt+B 从接口自动跳转掉实现类
public interface IUserService extends IService<User> {

    //注册一个学生的方法
    void registerStudent(RegisterVo registerVo);

    //查询所有讲师List集合的方法
    List<User> getTeachers();

    //查询所有讲师Map集合的方法
    Map<String,User> getTeacherMap();

    //查询用户信息面板数据
    UserVo getCurrentUserVo(String username);

    //根据用户名查询用户信息
    User getUserByUserName(String username);

    //根据用户id查询权限
    List<Permission> getUserPermissionsById(Integer id);
    //根据用户id查询角色
    List<Role> getUserRolesById(Integer id);

}
