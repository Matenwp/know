package cn.tedu.knows.faq.service.impl;


import cn.tedu.knows.commons.exception.ServiceException;
import cn.tedu.knows.commons.model.Comment;
import cn.tedu.knows.commons.model.User;
import cn.tedu.knows.faq.mapper.CommentMapper;
import cn.tedu.knows.faq.service.ICommentService;
import cn.tedu.knows.faq.vo.CommentVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private RibbonClient ribbonClient;

    @Override
    @Transactional
    public Comment saveComment(CommentVo commentVo, String username) {
        User user=ribbonClient.getUser(username);
        Comment comment=new Comment()
                .setUserId(user.getId())
                .setUserNickName(user.getNickname())
                .setAnswerId(commentVo.getAnswerId())
                .setContent(commentVo.getContent())
                .setCreatetime(LocalDateTime.now());
        int num=commentMapper.insert(comment);
        if(num!=1){
            throw new ServiceException("服务器忙");
        }
        // 别忘了返回
        return comment;
    }

    @Override
    public boolean removeCommentById(Integer id, String username) {
        // 根据用户名查询用户信息
        User user=ribbonClient.getUser(username);
        //判断是不是讲师
        if(user.getType()==1){
            int num=commentMapper.deleteById(id);
            return num==1;
        }
        //获得当前要删除的评论的详细信息
        Comment comment=commentMapper.selectById(id);
        //判断当前登录用户是不是评论的发布者
        if(user.getId()==comment.getUserId()){
            int num=commentMapper.deleteById(id);
            return num == 1;
        }
        //上面两个if都不进,表示当前用户既不是讲师也不是评论的发布者
        throw new ServiceException("您不能删除这个评论!");
    }

    @Override
    @Transactional
    public Comment updateComment(Integer commentId,
                   CommentVo commentVo, String username) {
        User user=ribbonClient.getUser(username);
        Comment comment=commentMapper.selectById(commentId);
        if(user.getType()==1||user.getId()==comment.getUserId()){
            int num=commentMapper.updateCommentContentById(
                    commentVo.getContent(),commentId);
            comment.setContent(commentVo.getContent());
            if(num!=1){
                throw new ServiceException("服务器忙");
            }
            return comment;
        }
        throw new ServiceException("您不能修改这个评论");
    }
}
