package cn.tedu.knows.search.vo;

import cn.tedu.knows.commons.model.Tag;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Document(indexName = "knows")
public class QuestionVo implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final Integer POSTED=0;  //已提交\未回复
    public static final Integer SOLVING=1; //未采纳\已回复
    public static final Integer SOLVED=2;  //已采纳\已解决

    @Id
    private Integer id;

    /**
     * 问题的标题
     */
    @Field(type = FieldType.Text,analyzer = "ik_smart",
                    searchAnalyzer = "ik_smart")
    private String title;
    /**
     * 提问内容
     */
    @Field(type = FieldType.Text,analyzer = "ik_smart",
            searchAnalyzer = "ik_smart")
    private String content;

    /**
     * 提问者用户名
     */
    @Field(type = FieldType.Keyword)
    private String userNickName;

    /**
     * 提问者id
     */
    @Field(type = FieldType.Integer)
    private Integer userId;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Date,
            format = DateFormat.basic_date_time)
    private LocalDateTime createtime;

    /**
     * 状态，0-》未回答，1-》待解决，2-》已解决
     */
    @Field(type = FieldType.Integer)
    private Integer status;

    /**
     * 浏览量
     */
    @Field(type = FieldType.Integer)
    private Integer pageViews;

    /**
     * 该问题是否公开，所有学生都可见，0-》否，1-》是
     */
    @Field(type = FieldType.Integer)
    private Integer publicStatus;

    @Field(type = FieldType.Date,format = DateFormat.basic_date)
    private LocalDate modifytime;

    @Field(type = FieldType.Integer)
    private Integer deleteStatus;

    @Field(type = FieldType.Keyword)
    private String tagNames;

    // 保存当前问题的所有标签
    //@Transient表示当前属性在ES中没有对应的列
    @Transient
    private List<Tag> tags;


}
