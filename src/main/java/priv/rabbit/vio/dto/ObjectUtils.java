package priv.rabbit.vio.dto;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Author administered
 * @Description
 * @Date 2020/4/14 1:28
 **/
public class ObjectUtils {
    private ObjectUtils() {}
    //    获取类属性
    public static Object getObject(Object wrapObject,String attribute) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
        String methodName = "get" + StringUtils.initcap(attribute);    //调用字符串大小写处理方法获得"getter方法名"字符串
        Field field = wrapObject.getClass().getDeclaredField(attribute);    //通过反射取得指定类中指定成员变量
        if(field == null) {    //如果成员变量中没有找到属性值，可能是父类中的属性
            field = wrapObject.getClass().getField(attribute);
        }
        if(field == null) {    //都不存在，确认为Null
            return null;
        }
        Method method = wrapObject.getClass().getMethod(methodName);    //通过反射获取指定类中的指定方法
        return method.invoke(wrapObject);    //调用无参方法
    }
    //    设置成员变量
    public static void setObjectValue(Object wrapObject,String attribute,String value) throws NoSuchFieldException, SecurityException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Field field = wrapObject.getClass().getDeclaredField(attribute);    //field = dept变量
        if(field == null) {    //类中没有找到属性值，可能是父类中的属性
            field = wrapObject.getClass().getField(attribute);
        }
        if(field == null) {    //都不存在，确认为Null
            return ;
        }
        String methodName = "set" + StringUtils.initcap(attribute);
        Method method = wrapObject.getClass().getMethod(methodName,field.getType());    //method = 方法(参数类型)
        method.invoke(wrapObject, value);    //调用有参方法
    }

}
