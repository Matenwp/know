package cn.tedu.knows.portal.service;


import cn.tedu.knows.portal.model.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
public interface ITagService extends IService<Tag> {

    List<Tag> getTags();

}
