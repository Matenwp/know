package cn.tedu.knows.portal.mapper;

import cn.tedu.knows.portal.model.Permission;
import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.vo.UserVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    //根据用户名获得用户信息
    @Select("select * from user where username=#{username}")
    User findUserByUsername(String username);

    //根据用户id查询用户权限
    @Select("SELECT" +
            " p.id , p.name" +
            " FROM " +
            " user u " +
            " LEFT JOIN user_role ur ON u.id=ur.user_id" +
            " LEFT JOIN role r ON r.id=ur.role_id" +
            " LEFT JOIN role_permission rp " +
            " ON r.id=rp.role_id" +
            " LEFT JOIN permission p ON" +
            " p.id=rp.permission_id" +
            " WHERE u.id=#{id}")
    List<Permission> findUserPermissionsById(Integer id);


    //根据用户名查询UserVo信息
    @Select("select id,username,nickname from user" +
            " where username=#{username}")
    UserVo findUserVoByUsername(String username);





}
