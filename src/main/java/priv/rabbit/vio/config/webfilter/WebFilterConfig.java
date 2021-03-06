package priv.rabbit.vio.config.webfilter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 该类的作用是可以为ws.html提供便捷的地址映射,只需要在地址栏里面输入localhost:8080/ws,就会找到ws.html
 */
@Configuration
public class WebFilterConfig implements WebMvcConfigurer {

    /**
     * 注册 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //指定拦截器类
        registry.addInterceptor(new VioHandlerInterceptor());
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/ws").setViewName("/ws");
    }
}
