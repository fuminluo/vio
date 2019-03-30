package priv.rabbit.vio.config.annotation;

import java.lang.annotation.*;

/**
 * 加密注解
 * 
 * 加了此注解的接口将进行数据加密操作
 *
 * @author: LIUTAO
 * @Description:
 * @Date: Created in 14:23 2018/6/7
 * @Modified By:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Encrypt {

}
