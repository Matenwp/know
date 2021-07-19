package cn.tedu.knows.search.repository;

import cn.tedu.knows.search.vo.QuestionVo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface QuestionRepository
            extends ElasticsearchRepository<QuestionVo,Integer> {

}
