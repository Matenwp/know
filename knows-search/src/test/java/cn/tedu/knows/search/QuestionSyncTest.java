package cn.tedu.knows.search;

import cn.tedu.knows.search.repository.QuestionRepository;
import cn.tedu.knows.search.service.IQuestionService;
import cn.tedu.knows.search.vo.QuestionVo;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
    //测试模糊查询
    @Test
    void query(){
        Page<QuestionVo> page=questionRepository
             .queryAllByParams("方法问题","方法问题",11,
                     PageRequest.of(0,10));
        for(QuestionVo q:page){
            System.out.println(q);
        }
    }
    @Test
    void searchTest(){
        PageInfo<QuestionVo> qs=questionService
                .search("java","st2",
                        1,8);
        for(QuestionVo q:qs.getList()){
            System.out.println(q);
        }
    }


}
