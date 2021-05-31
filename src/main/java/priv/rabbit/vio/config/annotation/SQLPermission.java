package priv.rabbit.vio.config.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SQLPermission {

    /**
     * 表别名
     */
    String alias = "";


    String menuId();
}
