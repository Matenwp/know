package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.model.Question;
import cn.tedu.knows.portal.mapper.QuestionMapper;
import cn.tedu.knows.portal.model.Tag;
import cn.tedu.knows.portal.model.User;
import cn.tedu.knows.portal.service.IQuestionService;
import cn.tedu.knows.portal.service.ITagService;
import cn.tedu.knows.portal.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
@Service
@Slf4j
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public PageInfo<Question> getMyQuestions(
            String username,Integer pageNum,Integer pageSize) {
        //1.通过用户名查询用户信息
        User user=userMapper.findUserByUsername(username);
        //2.根据当前登录用户的id查询问题
        QueryWrapper<Question> query=new QueryWrapper<>();
        query.eq("user_id",user.getId());
        query.eq("delete_status",0);
        query.orderByDesc("createtime");
        //查询执行之前使用PageHelper对象进行分页设置
        //第一个参数是页码,第二个参数是每页最大条数
        PageHelper.startPage(pageNum,pageSize);
        List<Question> list=questionMapper.selectList(query);
        //3.将查询到的当前问题的所有标签获得
        for(Question question : list){
            List<Tag> tags=tagName2Tags(question.getTagNames());
            question.setTags(tags);
        }
        //4.返回查询到的问题
        log.debug("当前用户问题数量:{}", list.size());
        //千万别忘了返回!!!
        return new PageInfo<>(list);
    }

    @Autowired
    private ITagService tagService;
    //编写一个方法,根据tag_names的值获得一个对应的List<Tag>集合
    private List<Tag> tagName2Tags(String tagNames){
        //tagNames :  "Java基础,Java SE,面试题"
        String[] names=tagNames.split(",");
        // names:   {"Java基础","Java SE","面试题"}
        //准备包含所有标签的Map
        Map<String,Tag> tagMap=tagService.getTagMap();
        //声明List<Tag> 用于接收标签名称对应的标签对象
        List<Tag> tags=new ArrayList<>();
        //遍历数组,将数组元素对应的标签对象添加到tags中
        for(String name:names){
            tags.add(tagMap.get(name));
        }
        //别忘了返回
        return tags;

    }






}
