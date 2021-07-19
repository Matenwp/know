package cn.tedu.knows.search.service.impl;

import cn.tedu.knows.search.repository.QuestionRepository;
import cn.tedu.knows.search.service.IQuestionService;
import cn.tedu.knows.search.vo.QuestionVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Arrays;

@Service
@Slf4j
public class QuestionServiceImpl implements IQuestionService {

    @Resource
    private QuestionRepository questionRepository;
    @Resource
    private RestTemplate restTemplate;
    @Override
    public void sync() {
        String url=
          "http://faq-service/v2/questions/page/count?pageSize={1}";
        //调用Faq模块的方法获得总页数
        Integer pageSize=8;
        Integer totalPage=restTemplate
                .getForObject(url,Integer.class,pageSize);
        //根据总页数进行循环
        for(int i=1;i<=totalPage;i++){
            url= "http://faq-service/v2/questions/" +
                    "page?pageNum={1}&pageSize={2}";
            //分页查询出question表中的数据
            QuestionVo[] qs=restTemplate.getForObject(
                    url,QuestionVo[].class,i,pageSize);
            //将本页数据新增到Es
            questionRepository.saveAll(Arrays.asList(qs));
            log.debug("新增完成了第{}页",i);
        }
        //所有页的数据都新增到Es中了


    }
}
