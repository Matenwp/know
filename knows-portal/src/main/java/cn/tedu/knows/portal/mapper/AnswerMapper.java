package cn.tedu.knows.portal.mapper;

import cn.tedu.knows.portal.model.Answer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
@Repository
public interface AnswerMapper extends BaseMapper<Answer> {

    // 按问题id查询包含所有评论的回答list
    // 和xml文件对应的方法不用写任何注解
    List<Answer> findAnswersWithCommentsByQuestionId(Integer id);

}
