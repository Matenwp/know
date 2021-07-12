package cn.tedu.knows.faq.service;


import cn.tedu.knows.commons.model.Tag;
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
public interface ITagService extends IService<Tag> {

    //获得所有标签的List
    List<Tag> getTags();

    //获得所有标签的Map,key是标签名称,value是标签对象
    Map<String,Tag> getTagMap();
}
