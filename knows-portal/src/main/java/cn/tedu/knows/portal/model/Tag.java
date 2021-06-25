package cn.tedu.knows.portal.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Data
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Slf4j
public class Tag {

    private Integer id;
    private String name;
    private String createby;
    private String createtime;

    public void record(){

        log.debug("这是Lombok@Slf4j注解的log对象输出的日志信息");

    }

}
