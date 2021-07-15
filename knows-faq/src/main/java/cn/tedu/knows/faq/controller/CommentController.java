package cn.tedu.knows.faq.controller;


import cn.tedu.knows.commons.exception.ServiceException;
import cn.tedu.knows.commons.model.Comment;
import cn.tedu.knows.faq.service.ICommentService;
import cn.tedu.knows.faq.vo.CommentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
@RestController
@RequestMapping("/v2/comments")
@Slf4j
public class CommentController {

    @Resource
    private ICommentService commentService;

    //@PostMapping后面不加()等价于@PostMapping("")
    @PostMapping
    public Comment postComment(
            @Validated CommentVo commentVo,
            BindingResult result,
            @AuthenticationPrincipal UserDetails user){
        log.debug("接收到评论信息:{}",commentVo);
        if(result.hasErrors()){
            String msg=result.getFieldError().getDefaultMessage();
            throw new ServiceException(msg);
        }
        Comment comment=commentService.saveComment(
                commentVo,user.getUsername());
        //千万别忘了返回!!!
        return comment;
    }

    //  /v1/comments/82/delete
    @GetMapping("/{id}/delete")
    public String remove(
            @PathVariable Integer id,
            @AuthenticationPrincipal UserDetails user){
        //执行业务逻辑层方法,使用boolean类型返回值接收
        boolean isDelete=commentService
                .removeCommentById(id,user.getUsername());
        if(isDelete){
            return "删除成功";
        }else{
            return "要删除的评论不存在";
        }
    }

    @PostMapping("/{id}/update")
    public Comment updateComment(
            @PathVariable Integer id,
            @Validated CommentVo commentVo,
            BindingResult result,
            @AuthenticationPrincipal UserDetails user){
        log.debug("修改内容:{}",commentVo);
        if(result.hasErrors()){
            String msg=result.getFieldError().getDefaultMessage();
            throw new ServiceException(msg);
        }
        Comment comment=commentService.updateComment(
                id,commentVo,user.getUsername());
        return comment;
    }
}
