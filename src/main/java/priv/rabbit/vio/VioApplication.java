package priv.rabbit.vio;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import priv.rabbit.vio.factory.*;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Properties;

/**
 * 将 spring 默认的文件上传处理类取消自动配置，这一步很重要，没有这一步，当multipartResolver重新指向了我们定义好
 * 的新的文件上传处理类后，前台传回的 file 文件在后台获取会是空，加上这句话就好了，推测不加这句话，spring 依然
 * 会先走默认的文件处理流程并修改request对象，再执行我们定义的文件处理类。
 * exclude表示自动配置时不包括Multipart配置
 */

@SpringBootApplication
@MapperScan(basePackages = "priv.**.mapper")
//@EnableRedisHttpSession
@EnableFeignClients
@EnableAsync
//@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@EnableCaching
public class VioApplication {

    //配置mybatis的分页插件pageHelper
    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        properties.setProperty("dialect", "mysql");    //配置mysql数据库的方言
        pageHelper.setProperties(properties);
        return pageHelper;
    }


    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    @Bean
    public CorsFilter corsFilter() {
        System.out.println("=============== 设置跨域过滤器 ================");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    public static void main(String[] args) {
        SpringApplication.run(VioApplication.class, args);
    }
}
