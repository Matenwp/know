package cn.tedu.knows.portal.service;

import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
