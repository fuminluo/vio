package priv.rabbit.vio.config.mybatis;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import priv.rabbit.vio.entity.User;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.*;

/**
 * @Author administered
 * @Description
 * @Date 2019/11/6 22:11
 **/
@Intercepts(@Signature(method = "handleResultSets", type = ResultSetHandler.class, args = {Statement.class}))
public class MybatisInterceptor implements Interceptor {

    static Class cls = User.class;
    static Field phone;
    static Field token;
    static Field nickname;


    {
        try {
            phone = cls.getDeclaredField("phone");
            phone.setAccessible(true);

            token = cls.getDeclaredField("token");
            token.setAccessible(true);

            nickname = cls.getDeclaredField("nickname");
            nickname.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        {
            System.out.println(" getMethod : " + invocation.getMethod().getName());
            Object object = invocation.proceed();
            if (null != object && object instanceof Collection) {
                Collection collection = (Collection) object;
                Iterator iterator = collection.iterator();
                long start = System.currentTimeMillis();
                while (iterator.hasNext()) {
                    Object obj = iterator.next();
                    if (obj.getClass().getClassLoader() == null) {
                        break;
                    }
                    Field userIdField = obj.getClass().getDeclaredField("userId");
                    userIdField.setAccessible(true);
                    Long userId = (Long) userIdField.get(obj);
                    phone.set(obj, System.currentTimeMillis() + "");
                    token.set(obj, "110");
                    nickname.set(obj, UUID.randomUUID().toString());
                }
                System.out.println(" 拦截器耗时 : " + (System.currentTimeMillis() - start));
            }
            return object;
        }

    }

    @Override
    public Object plugin(Object target) {
        System.out.println("生成代理对象 ： " + target.getClass().getSimpleName() + "--" + (target instanceof ResultSetHandler));
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}



