package cn.tedu.knows.faq.service;


import cn.tedu.knows.commons.model.Answer;
import cn.tedu.knows.faq.vo.AnswerVo;
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
public interface IAnswerService extends IService<Answer> {

    // 新增回答的方法
    // 这个新增方法返回Answer,因为返回值要直接显示在页面上
    Answer saveAnswer(AnswerVo answerVo, String username);

    // 按问题id查询回答的方法
    List<Answer> getAnswersByQuestionId(Integer questionId);

    // 采纳答案的方法
    // 如果想做的严谨一些,可以传入当前登录用户的username
    // 来判断是不是问题的提问者在采纳答案
    boolean accept(Integer answerId);


}
