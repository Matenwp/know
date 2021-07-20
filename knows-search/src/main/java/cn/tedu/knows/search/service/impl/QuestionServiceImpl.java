package cn.tedu.knows.search.service.impl;

import cn.tedu.knows.commons.model.Tag;
import cn.tedu.knows.commons.model.User;
import cn.tedu.knows.search.repository.QuestionRepository;
import cn.tedu.knows.search.service.IQuestionService;
import cn.tedu.knows.search.utils.Pages;
import cn.tedu.knows.search.vo.QuestionVo;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

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

    @Override
    public PageInfo<QuestionVo> search(String key, String username, Integer pageNum, Integer pageSize) {
        // 验证一下分页信息不能为空
        if (pageNum==null)
            pageNum=1;
        if (pageSize==null)
            pageNum=8;
        // 查询用户信息User对象
        String url="http://sys-service/v1/auth/user?username={1}";
        User user=restTemplate.getForObject(
                url,User.class,username);
        // 准备分页信息对象Pageable(带排序的)
        Pageable pageable= PageRequest.of(pageNum-1,pageSize,
                Sort.Direction.DESC,"createtime");
        // 执行按条件查询ES获得分页结果
        Page<QuestionVo> page=questionRepository
                .queryAllByParams(key,key,user.getId(),pageable);
        for(QuestionVo q:page){
            List<Tag> tags=tagName2Tags(q.getTagNames());
            q.setTags(tags);
        }
        // 转换成PageInfo类型并返回
        return Pages.pageInfo(page);
    }

    @Override
    public void saveQuestion(QuestionVo questionVo) {
        //新增questionVo到ES
        questionRepository.save(questionVo);
    }

    private Map<String,Tag> getTagMap(){
        String url="http://faq-service/v2/tags";
        Tag[] tags=restTemplate.getForObject(url,Tag[].class);
        Map<String,Tag> tagMap=new HashMap<>();
        for(Tag t: tags){
            tagMap.put(t.getName(),t);
        }
        return tagMap;
    }

    private List<Tag> tagName2Tags(String tagNames){
        String[] names=tagNames.split(",");
        Map<String,Tag> tagMap=getTagMap();
        List<Tag> tags=new ArrayList<>();
        for(String name:names){
            tags.add(tagMap.get(name));
        }
        return tags;
    }
}
