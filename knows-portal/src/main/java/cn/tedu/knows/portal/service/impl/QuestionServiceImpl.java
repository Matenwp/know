package cn.tedu.knows.portal.service.impl;

import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.mapper.QuestionTagMapper;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.mapper.UserQuestionMapper;
import cn.tedu.knows.portal.model.*;
import cn.tedu.knows.portal.mapper.QuestionMapper;
import cn.tedu.knows.portal.service.IQuestionService;
import cn.tedu.knows.portal.service.ITagService;
import cn.tedu.knows.portal.service.IUserService;
import cn.tedu.knows.portal.vo.QuestionVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    @Autowired
    private QuestionTagMapper questionTagMapper;
    @Autowired
    private UserQuestionMapper userQuestionMapper;
    @Autowired
    private IUserService userService;

    @Override
    //@Transactional标记上业务逻辑层的方法上
    //SpringBoot封装的功能,将下面方法定义为一个事务
    //这个方法中的所有对数据库的操作要么都执行,要么都回滚
    @Transactional
    public void saveQuestion(QuestionVo questionVo, String username) {
        //1.根据用户名查询用户信息
        User user=userMapper.findUserByUsername(username);
        //2.根据学生选中的标签构建tag_names列的值
        StringBuilder builder=new StringBuilder();
        //{"java基础","javaSE","面试题"}
        for(String tagName : questionVo.getTagNames()){
            builder.append(tagName).append(",");
        }
        String tagNames=builder
                .deleteCharAt(builder.length()-1).toString();
        //"java基础,javaSE,面试题,"
        //3.实例化Question对象并赋值
        Question question=new Question()
                .setTitle(questionVo.getTitle())
                .setContent(questionVo.getContent())
                .setUserNickName(user.getNickname())
                .setUserId(user.getId())
                .setCreatetime(LocalDateTime.now())
                .setStatus(0)
                .setPageViews(0)
                .setPublicStatus(0)
                .setDeleteStatus(0)
                .setTagNames(tagNames);
        //4.执行新增Question
        int num=questionMapper.insert(question);
        if(num!=1){
            throw new ServiceException("服务器忙!");
        }
        //5.新增Question和tag的关系
        //获得包含所有标签的Map
        Map<String,Tag> tagMap=tagService.getTagMap();
        for(String tagName : questionVo.getTagNames()){
            Tag t=tagMap.get(tagName);
            QuestionTag questionTag=new QuestionTag()
                    .setQuestionId(question.getId())
                    .setTagId(t.getId());
            num=questionTagMapper.insert(questionTag);
            if(num!=1){
                throw new ServiceException("服务器忙");
            }
            log.debug("新增了问题和标签的关系:{}",questionTag);
        }

        //6.新增User(讲师)和Question的关系
        //获得包含所有讲师的Map
        Map<String,User> teacherMap=userService.getTeacherMap();
        for(String nickname : questionVo.getTeacherNicknames()){
            User teacher=teacherMap.get(nickname);
            UserQuestion userQuestion=new UserQuestion()
                    .setQuestionId(question.getId())
                    //        ↓↓↓↓↓↓↓↓ 不是user,是teacher
                    .setUserId(teacher.getId())
                    .setCreatetime(LocalDateTime.now());
            num=userQuestionMapper.insert(userQuestion);
            if(num!=1){
                throw new ServiceException("服务器忙");
            }
            log.debug("新增了讲师和问题的关系:{}",userQuestion);
        }
    }

    @Override
    public Integer countQuestionsByUserId(Integer userId) {
        QueryWrapper<Question> query=new QueryWrapper<>();
        query.eq("user_id",userId);
        query.eq("delete_status",0);
        Integer count=questionMapper.selectCount(query);
        return count;
    }

    @Override
    public PageInfo<Question> getTeacherQuestions(String username, Integer pageNum, Integer pageSize) {
        User user=userMapper.findUserByUsername(username);
        //设置分页
        PageHelper.startPage(pageNum,pageSize);
        List<Question> questions=
                questionMapper.findTeacherQuestions(user.getId());
        //将查询出的问题包含的标签赋值
        for(Question question: questions){
            List<Tag> tags=tagName2Tags(question.getTagNames());
            question.setTags(tags);
        }
        //别忘了返回!!!!
        return new PageInfo<>(questions);
    }


}
