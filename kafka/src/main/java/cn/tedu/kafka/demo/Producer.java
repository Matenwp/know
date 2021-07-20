package cn.tedu.kafka.demo;

import cn.tedu.kafka.vo.Message;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class Producer {

    //从Spring容器中获得由添加依赖注入的控制操作kafka的对象
    //KafkaTemplate<[topic的名称],[发送的信息]>
    @Resource
    private KafkaTemplate<String,String> kafkaTemplate;

    //发送信息需要将java对象转换为json格式
    //可以是实例化google提供的gson对象来操作
    private Gson gson=new Gson();

    int num=1;
    //开始设置定时计划,每隔8秒运行一个下面的方法
    //从启动SpringBoot项目开始每隔指定时间自动运行
    @Scheduled(fixedRate = 8000) //8000毫秒
    public  void  sendMessage(){
        Message message=new Message()
                .setContent("恭喜你中了200万!")
                .setTime(System.currentTimeMillis())
                .setId(num++);
        //将message转换为json格式的String
        String json=gson.toJson(message);
        //发送信息到Kafka的代码
        //send([话题名称],[发送的信息])
        kafkaTemplate.send("demoTopic",json);
        log.debug("发送信息成功:{}",json);
    }
}
