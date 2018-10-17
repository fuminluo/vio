package priv.rabbit.vio.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 上传文件接口
 *
 * @Author administered
 * @Description
 * @Date 2018/10/17 21:47
 **/
public interface UploadFileService {

    String pushFile(MultipartFile multipartFile) throws IOException;

    String syncTest() throws IOException;
}
