package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.model.Tag;
import cn.tedu.knows.portal.mapper.TagMapper;
import cn.tedu.knows.portal.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Override
    public List<Tag> getTags() {
        //this.list()就是在调用父类中已经提供的全查所有当前Tag对象的方法
        //List<Tag> tags=this.list();
        //list()方法是当前类父类ServiceImpl中提供的全查方法!!!
        return list();
    }



    /*@Autowired
    private TagMapper tagMapper;
    @Override
    public List<Tag> getTags() {
        List<Tag> tags=tagMapper.selectList(null);
        return tags;
    }*/
}
