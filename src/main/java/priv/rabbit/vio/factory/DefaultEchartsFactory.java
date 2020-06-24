package priv.rabbit.vio.factory;

import org.springframework.beans.BeansException;
import priv.rabbit.vio.utils.SpringUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DefaultEchartsFactory extends EchartsFactory {

    /**
     * 饿汉单例模式
     * 在类加载时就完成了初始化，所以类加载较慢，但获取对象的速度快
     */
    private static DefaultEchartsFactory defaultEchartsFactory = new DefaultEchartsFactory();

    /**
     * 静态，不用同步（类加载时已初始化，不会有多线程的问题）
     */
    public static DefaultEchartsFactory getInstance() {
        return defaultEchartsFactory;
    }

    @Override
    public AbstractBarSimple createBarSimple(Map<String, Object> parameters, Class<?> cls) {
        return (AbstractBarSimple) getBeanInstance(cls, parameters);
    }

    @Override
    public AbstractLineSimple createLineSimple(Map<String, Object> parameters, Class<?> cls) {
        return (AbstractLineSimple) getBeanInstance(cls, parameters);
    }


    @SuppressWarnings("unchecked")
    public static <T> T getBeanInstance(Class<T> cls, Map<String, Object> parameters) throws BeansException {
        try {
            /**
             * 如果使用反射newInstance()之后<br/>
             * cls实现类springIOC容器失效<br/>
             * 所以从SpringIOC容器中获取该实例
             */
            Object obj = SpringUtil.getObject(cls);
            Method method = cls.getDeclaredMethod("setParameters", new Class[]{Map.class});
            method.setAccessible(true);
            method.invoke(obj, parameters);
            return (T) obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
