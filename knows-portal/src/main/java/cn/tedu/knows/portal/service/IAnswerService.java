package cn.tedu.knows.portal.service;

import cn.tedu.knows.portal.model.Answer;
import cn.tedu.knows.portal.vo.AnswerVo;
import com.baomidou.mybatisplus.extension.service.IService;
import sun.security.x509.AVA;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-06-25
 */
public interface IAnswerService extends IService<Answer> {

    // 新增回答的方法
    // 这个新增方法返回Answer,因为返回值要直接显示在页面上
    Answer saveAnswer(AnswerVo answerVo,String username);

}
