package cn.tedu.knows.portal.mapper;

import cn.tedu.knows.portal.model.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
public interface QuestionMapper extends BaseMapper<Question> {

    @Select("SELECT q.* FROM" +
            "    question q" +
            " LEFT JOIN" +
            "    user_question uq" +
            " ON" +
            "    q.id=uq.question_id" +
            " WHERE" +
            "    q.user_id=#{userId} OR uq.user_id=#{userId}" +
            " ORDER BY q.createtime DESC ")
    List<Question> findTeacherQuestions(Integer userId);

    @Update("update question set status=#{status} " +
            "where id=#{questionId}")
    Integer updateStatus(@Param("status")Integer status,
                         @Param("questionId")Integer questionId);




}
