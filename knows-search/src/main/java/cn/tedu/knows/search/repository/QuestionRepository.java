package cn.tedu.knows.search.repository;

import cn.tedu.knows.search.vo.QuestionVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface QuestionRepository
            extends ElasticsearchRepository<QuestionVo,Integer> {
    @Query("{\n" +
            "    \"bool\": {\n" +
            "      \"must\": [{\n" +
            "        \"bool\": {\n" +
            "          \"should\": [{\"match\": {\"title\": \"?0\"}}, {\"match\": {\"content\": \"?1\"}}]\n" +
            "        }\n" +
            "      }, {\n" +
            "        \"bool\": {\n" +
            "          \"should\": [{\"term\": {\"publicStatus\": 1}}, {\"term\": {\"userId\": ?2}}]\n" +
            "        }\n" +
            "      }]\n" +
            "    }\n" +
            "  }")
    Page<QuestionVo> queryAllByParams(String title, String content,
                     Integer userId, Pageable pageable);
}
