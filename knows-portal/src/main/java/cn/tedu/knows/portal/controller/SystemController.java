package cn.tedu.knows.portal.controller;

import cn.tedu.knows.portal.exception.ServiceException;
import cn.tedu.knows.portal.mapper.UserMapper;
import cn.tedu.knows.portal.service.IUserService;
import cn.tedu.knows.portal.vo.RegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@Slf4j
public class SystemController {

    @Resource
    private IUserService userService;

    @PostMapping("/register")
    public String registerStudent(
            //@Validated表示这个方法运行前
            //Spring Validation框架会对registerVo中的属性进行验证
            // 验证规则就是这个类中编写的规则
            @Validated RegisterVo registerVo,
            //BindingResult就是接收验证结果和错误信息的类型
            BindingResult result) {
        //如果SpringValidation验证有错误
        if (result.hasErrors()) {
            //获得错误信息
            String msg = result.getFieldError().getDefaultMessage();
            return msg;
        }
        log.debug("收到学生注册信息:{}", registerVo);

        userService.registerStudent(registerVo);
        return "注册完成";
    }

    @Value("${knows.resource.path}")
    private File resourcePath;
    @Value("${knows.resource.host}")
    private String resourceHost;
    //http://localhost:8899/2021/07/02/abc.jpg
    //     resourceHost  +/+  path  +/+  name

    //文件上传的方法
    @PostMapping("/upload/file")
    public String uploadFile(MultipartFile imageFile) throws IOException {
        // 根据日期获得一个日期路径
        //  2021/07/02
        String path= DateTimeFormatter.ofPattern("yyyy/MM/dd")
                    .format(LocalDate.now());
        // 实际上传路径:G:/upload/2021/07/02
        File folder=new File(resourcePath,path);
        // 这个路径可能不存在,使用mkdirs方法进行创建
        folder.mkdirs();//方法是mkdirsssssssssssss!!!!
        //开始创建文件名
        //先获得上传文件的原始文件名
        String fileName=imageFile.getOriginalFilename();
        //根据原始文件名截取出文件的扩展名
        // abc.png
        // 0123456
        String ext=fileName.substring(fileName.lastIndexOf("."));
        //再生成一个随机的文件名
        String name= UUID.randomUUID().toString()+ext;
        //将文件位置确定在上面准备的文件夹中
        //G:/upload/2021/07/02 / xxx.png
        File file= new File(folder,name);
        //将文件保存到上面file指定的位置
        imageFile.transferTo(file);
        log.debug("保存的实际路径为:{}",file.getAbsolutePath());
        String url=resourceHost+"/"+path+"/"+name;
        return url;
    }






}
