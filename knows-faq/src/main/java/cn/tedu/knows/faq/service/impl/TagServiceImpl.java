package cn.tedu.knows.faq.service.impl;


import cn.tedu.knows.commons.model.Tag;
import cn.tedu.knows.faq.mapper.TagMapper;
import cn.tedu.knows.faq.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    //声明一个成员变量充当缓存
    //CopyOnWriteArrayList是一个线程安全的List集合
    private List<Tag> tags=new CopyOnWriteArrayList<>();

    private Map<String,Tag> tagMap=new ConcurrentHashMap<>();

    @Override
    public List<Tag> getTags() {
        //  1     2     3
        if(tags.isEmpty()){
            synchronized (tags) {
                if(tags.isEmpty()) {
                    tags.addAll(list());
                    //循环遍历所有标签
                    for(Tag t: tags){
                        //以标签名称为key,标签对象为value保存在tagMap中
                        tagMap.put(t.getName(),t);
                    }
                }
            }
        }
        return tags;
    }

    @Override
    public Map<String, Tag> getTagMap() {
        if(tagMap.isEmpty()){
            getTags();
        }
        //别忘了返回tagMap
        return tagMap;
    }



    /*@Autowired
    private TagMapper tagMapper;
    @Override
    public List<Tag> getTags() {
        List<Tag> tags=tagMapper.selectList(null);
        return tags;
    }*/
}
