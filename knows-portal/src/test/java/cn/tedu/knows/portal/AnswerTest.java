package cn.tedu.knows.portal;

import cn.tedu.knows.portal.mapper.AnswerMapper;
import cn.tedu.knows.portal.model.Answer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class AnswerTest {
    @Resource
    AnswerMapper answerMapper;
    @Test
    void run(){
        List<Answer> answers=answerMapper
                .findAnswersWithCommentsByQuestionId(153);
        for(Answer a: answers){
            System.out.println(a);
        }
    }
}
