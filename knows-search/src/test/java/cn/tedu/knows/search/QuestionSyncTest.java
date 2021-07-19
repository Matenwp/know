package cn.tedu.knows.search;

import cn.tedu.knows.search.repository.QuestionRepository;
import cn.tedu.knows.search.service.IQuestionService;
import cn.tedu.knows.search.vo.QuestionVo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.RegEx;
import javax.annotation.Resource;

@SpringBootTest
public class QuestionSyncTest {
    @Resource
    IQuestionService questionService;
    @Test
    void run(){
        questionService.sync();

    }
    @Resource
    QuestionRepository questionRepository;
    //下面可以在写一个测试,查询所有Es中的数据
    //questionRepository.findAll()可以查询出Es中指定索引的所有数据
    @Test
    void  showAll(){
        Iterable<QuestionVo> qs=questionRepository.findAll();
        for(QuestionVo q:qs){
            System.out.println(q);
        }
    }

}
