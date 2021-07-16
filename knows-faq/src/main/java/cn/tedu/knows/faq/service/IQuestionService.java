package cn.tedu.knows.faq.service;


import cn.tedu.knows.commons.model.Question;
import cn.tedu.knows.faq.vo.QuestionVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
public interface IQuestionService extends IService<Question> {

    // 查询当前登录学生的问题
    PageInfo<Question> getMyQuestions(
            String username,Integer pageNum,Integer pageSize);

    //新增问题的业务逻辑层方法
    void saveQuestion(QuestionVo questionVo, String username);

    // 根据用户id查询当前用户问题数的方法
    Integer countQuestionsByUserId(Integer userId);

    // 查询登录讲师的任务列表
    PageInfo<Question> getTeacherQuestions(
            String username,Integer pageNum,Integer pageSize);


    // 根据问题id查询问题详情
    Question getQuestionById(Integer id);


    // 用于和ES数据库同步数据的业务逻辑层方法
    PageInfo<Question> getQuestions(
            Integer pageNum,Integer pageSize);



}
