package priv.rabbit.vio.config.uploadprogress;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;

/**
 * 配置 multipartResolver
 *
 * @author LuoFuMin
 * @data 2018/8/6
 */
@Configuration
public class MultipartResolverConfig {
    /**
     * 将 multipartResolver 指向我们刚刚创建好的继承 ommonsMultipartResolver 类的自定义文件上传处理类
     */
    @Bean(name = "multipartResolver")
    public MultipartResolver multipartResolver() {
        CustomMultipartResolver customMultipartResolver = new CustomMultipartResolver();
        return customMultipartResolver;
    }
}
