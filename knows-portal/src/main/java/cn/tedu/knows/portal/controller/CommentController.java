package cn.tedu.knows.portal.controller;


import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.model.Comment;
import cn.tedu.knows.portal.service.ICommentService;
import cn.tedu.knows.portal.vo.CommentVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/v1/comments")
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



}
