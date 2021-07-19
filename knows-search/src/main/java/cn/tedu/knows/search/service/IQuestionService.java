package cn.tedu.knows.search.service;

public interface IQuestionService {

    //同步数据库中question表和ES中数据的方法
    public void sync();

}
