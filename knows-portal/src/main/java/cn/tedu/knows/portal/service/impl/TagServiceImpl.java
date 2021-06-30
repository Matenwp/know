package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.model.Tag;
import cn.tedu.knows.portal.mapper.TagMapper;
import cn.tedu.knows.portal.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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

    //声明一个成员变量充当缓存
    //CopyOnWriteArrayList是一个线程安全的List集合
    private List<Tag> tags=new CopyOnWriteArrayList<>();

    @Override
    public List<Tag> getTags() {
        //  1     2     3
        if(tags.isEmpty()){
            synchronized (tags) {
                if(tags.isEmpty()) {
                    tags.addAll(list());
                }
            }
        }
        return tags;
    }



    /*@Autowired
    private TagMapper tagMapper;
    @Override
    public List<Tag> getTags() {
        List<Tag> tags=tagMapper.selectList(null);
        return tags;
    }*/
}
