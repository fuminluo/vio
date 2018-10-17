package priv.rabbit.vio.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import priv.rabbit.vio.service.UploadFileService;

import java.io.File;
import java.io.IOException;

/**
 * 上传文件 业务逻辑
 *
 * @Author administered
 * @Description
 * @Date 2018/10/17 21:50
 **/
@Service
public class UploadFileServiceImpl implements UploadFileService {

    /**
     * @描述 上传文件
     * @参数 [multipartFile]
     * @返回值 java.lang.String
     * @创建人 admin
     * @创建时间 2018/10/17
     */

    @Async
    @Override
    public String pushFile(MultipartFile multipartFile) throws IOException {
        System.out.println("name==" + multipartFile.getOriginalFilename());
        System.out.println("异步线程 名称 ：" + Thread.currentThread().getName());
        File file = new File("D:/file/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        System.out.println("成功");
        return null;
    }
}
