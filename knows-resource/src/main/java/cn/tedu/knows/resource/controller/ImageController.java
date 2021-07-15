package cn.tedu.knows.resource.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/upload")
@CrossOrigin
public class ImageController {
    // http://localhost:9000/image/upload
    @Value("${knows.resource.path}")
    private File resourcePath;
    @Value("${knows.resource.host}")
    private String resourceHost;

    //文件上传的方法
    @PostMapping("")
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
