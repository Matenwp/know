package cn.tedu.knows.search.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Accessors(chain = true)
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
//表示对应ES文档的一个对象
//indexName = "items"对应索引名
// 对这个索引进行操作时,如果索引不存在,会自动创建
@Document(indexName = "items")
public class Item {
    @Id
    private Long id;
    @Field( type= FieldType.Text,
            analyzer = "ik_smart",
            searchAnalyzer = "ik_smart")
    private String title;  //商品标题
    @Field(type = FieldType.Keyword)
    private String category;//分类
    @Field(type = FieldType.Keyword)
    private String brand;//品牌
    @Field(type = FieldType.Double)
    private Double price;//价格
    @Field(type = FieldType.Keyword,index = false)
    private String images;//图片路径
}
