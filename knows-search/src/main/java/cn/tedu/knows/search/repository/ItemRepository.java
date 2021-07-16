package cn.tedu.knows.search.repository;

import cn.tedu.knows.search.vo.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ItemRepository extends
        ElasticsearchRepository<Item,Long> {
    //    ElasticsearchRepository<Item,Long>中
    //Item是vo泛型类型
    //Long是vo对象的id类型
}
