package priv.rabbit.vio;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 将 spring 默认的文件上传处理类取消自动配置，这一步很重要，没有这一步，当multipartResolver重新指向了我们定义好
 * 的新的文件上传处理类后，前台传回的 file 文件在后台获取会是空，加上这句话就好了，推测不加这句话，spring 依然
 * 会先走默认的文件处理流程并修改request对象，再执行我们定义的文件处理类。
 * exclude表示自动配置时不包括Multipart配置
 */
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@SpringBootApplication
@MapperScan(basePackages = "priv.**.mapper")
@EnableRedisHttpSession
@EnableFeignClients
public class VioApplication {

    public static void main(String[] args) {
        SpringApplication.run(VioApplication.class, args);
    }
}
