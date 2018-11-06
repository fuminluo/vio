package priv.rabbit.vio.config.excel;

import java.lang.annotation.*;

/**
 * @ClassName: ExcelCell * @Description: 实体字段与excel列号关联的注解 * @author albert *
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelCell {
    int col();

    Class<?> Type() default String.class;
}

