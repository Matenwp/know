package cn.tedu.knows.portal.mapper;

import cn.tedu.knows.portal.model.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

//BaseMapper就是MyBatisPlus提供的父接口,其中有对<>中指定的实体类
//的基本增删改查方法
public interface TagMapper extends BaseMapper<Tag> {


}
