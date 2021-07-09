package cn.tedu.knows.commons.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
@TableName("notice")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 消息类型，0-》评论问题的回答，1-》回答某人的提问，2-》评论某人的提问
     */
    @TableField("type")
    private Integer type;

    /**
     * 问题id
     */
    @TableField("question_id")
    private Integer questionId;

    /**
     * 创建时间
     */
    @TableField("createtime")
    private LocalDateTime createtime;

    /**
     * 收到消息的用户id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 回复者id
     */
    @TableField("reply_user_id")
    private Integer replyUserId;

    /**
     * 消息是否已查看，0-》否，1-》是
     */
    @TableField("read_status")
    private Integer readStatus;


}
