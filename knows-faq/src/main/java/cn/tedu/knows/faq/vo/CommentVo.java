package cn.tedu.knows.faq.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class CommentVo implements Serializable {

    @NotNull(message = "回答id不能为空")
    private Integer answerId;
    @NotBlank(message = "评论内容不能为空")
    private String content;

}
