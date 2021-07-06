package cn.tedu.knows.portal.service;

import cn.tedu.knows.portal.model.Comment;
import cn.tedu.knows.portal.vo.CommentVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
public interface ICommentService extends IService<Comment> {

    //新增评论的方法
    Comment saveComment(CommentVo commentVo,String username);
}
