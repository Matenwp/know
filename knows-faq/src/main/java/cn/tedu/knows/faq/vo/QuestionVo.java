package cn.tedu.knows.faq.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class QuestionVo implements Serializable {

    @NotBlank(message = "标题不能为空")
    @Pattern(regexp = "^.{3,50}$",message = "标题要求3~50个字符")
    private String title;

    @NotEmpty(message = "至少选择一个标签")
    private String[] tagNames={};

    @NotEmpty(message = "至少选择一个讲师")
    private String[] teacherNicknames={};

    @NotBlank(message ="问题内容不能为空")
    private String content;


}
