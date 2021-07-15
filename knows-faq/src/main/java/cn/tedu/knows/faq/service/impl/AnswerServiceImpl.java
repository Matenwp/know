package cn.tedu.knows.faq.service.impl;


import cn.tedu.knows.commons.exception.ServiceException;
import cn.tedu.knows.commons.model.Answer;
import cn.tedu.knows.commons.model.Question;
import cn.tedu.knows.commons.model.User;
import cn.tedu.knows.faq.mapper.AnswerMapper;
import cn.tedu.knows.faq.mapper.QuestionMapper;
import cn.tedu.knows.faq.service.IAnswerService;
import cn.tedu.knows.faq.vo.AnswerVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {

    @Resource
    private AnswerMapper answerMapper;

    @Resource
    private RibbonClient ribbonClient;

    @Override
    @Transactional
    public Answer saveAnswer(AnswerVo answerVo, String username) {
        User user=ribbonClient.getUser(username);
        Answer answer=new Answer()
                .setContent(answerVo.getContent())
                .setQuestId(answerVo.getQuestionId())
                .setUserNickName(user.getNickname())
                .setUserId(user.getId())
                .setLikeCount(0)
                .setAcceptStatus(0)
                .setCreatetime(LocalDateTime.now());
        int num=answerMapper.insert(answer);
        if(num!=1){
            throw new ServiceException("服务器忙");
        }
        //千万别忘了返回!!!
        return answer;
    }

    @Override
    public List<Answer> getAnswersByQuestionId(Integer questionId) {
        List<Answer> answers=answerMapper
                .findAnswersWithCommentsByQuestionId(questionId);
        //千万忘了返回!!!
        return answers;
    }

    @Resource
    private QuestionMapper questionMapper;

    @Override
    @Transactional
    public boolean accept(Integer answerId) {
        Answer answer=answerMapper.selectById(answerId);
        if(answer==null || answer.getAcceptStatus()==1){
            return false;
        }
        //修改answer表的采纳状态
        int num=answerMapper
                .updateAcceptStatus(1,answerId);
        if(num!=1){
            throw new ServiceException("服务器忙");
        }
        //修改question表的状态
        num=questionMapper
                .updateStatus(Question.SOLVED,answer.getQuestId());
        if(num!=1){
            throw new ServiceException("服务器忙");
        }
        //正常运行返回true!!!!
        return true;
    }
}
