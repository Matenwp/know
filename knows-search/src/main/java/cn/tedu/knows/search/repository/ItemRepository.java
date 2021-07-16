package cn.tedu.knows.search.repository;

import cn.tedu.knows.search.vo.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ItemRepository extends
        ElasticsearchRepository<Item,Long> {
    //    ElasticsearchRepository<Item,Long>中
    //Item是vo泛型类型
    //Long是vo对象的id类型

    //根据title查询匹配的Item
    Iterable<Item> queryItemsByTitleMatches(String title);

    //查询多个结果返回值为Iterable 必须根据要求的语法定义方法名
    //参数根据需要的条件项传入

    //根据title和品牌进行查询
    List<Item> queryItemsByTitleMatchesAndBrandMatches(
            String title,String brand);
    //根据title查询根据价格升序排序
    List<Item> queryItemsByTitleMatchesOrderByPriceAsc(
            String title);

    //分页查询
    Page<Item> queryItemsByTitleMatchesOrderByPriceAsc(
            String title, Pageable pageable);


}
