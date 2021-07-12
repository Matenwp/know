package cn.tedu.knows.faq.service.impl;


import cn.tedu.knows.commons.model.Tag;
import cn.tedu.knows.faq.mapper.TagMapper;
import cn.tedu.knows.faq.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
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

    @Resource
    private RedisTemplate<String,List<Tag>> redisTemplate;
    @Override
    public List<Tag> getTags() {
        List<Tag> tags=redisTemplate.opsForValue().get("tags");
        if(tags==null||tags.isEmpty()){
            System.out.println("连接数据库新增Redis中内容");
            tags=list();//list()方法是当前类父类提供的,就是全查所有标签
            //将查询到的信息保存到redis中
            redisTemplate.opsForValue().set("tags",tags);
        }
        return tags;
    }

    @Override
    public Map<String, Tag> getTagMap() {
        Map<String,Tag> map=new HashMap<>();
        //getTags()就是上面返回所有标签的方法
        // 所以这个循环就是遍历所有标签
        for(Tag t: getTags()){
            map.put(t.getName(),t);
        }
        //别忘了返回Map
        return map;
    }
}
