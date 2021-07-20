package cn.tedu.knows.search.service;

import cn.tedu.knows.search.vo.QuestionVo;
import com.github.pagehelper.PageInfo;

public interface IQuestionService {

    //同步数据库中question表和ES中数据的方法
    public void sync();

    //返回按关键字模糊查询ES的分页结果
    PageInfo<QuestionVo> search(String key,String username,
                                Integer pageNum,Integer pageSize);

    //编写新增一个问题对象到ES的业务逻辑层方法
    void saveQuestion(QuestionVo questionVo);


}
