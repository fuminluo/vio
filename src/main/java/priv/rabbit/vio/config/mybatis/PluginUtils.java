package priv.rabbit.vio.config.mybatis;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Proxy;
import java.util.Properties;

public class PluginUtils {
    public static final String DELEGATE_BOUNDSQL_SQL = "delegate.boundSql.sql";

    private PluginUtils() {
    }

    public static <T> T realTarget(Object target) {
        if (Proxy.isProxyClass(target.getClass())) {
            MetaObject metaObject = SystemMetaObject.forObject(target);
            return realTarget(metaObject.getValue("h.target"));
        } else {
            return (T) target;
        }
    }

    public static String getProperty(Properties properties, String key) {
        String value = properties.getProperty(key);
        return StringUtils.isBlank(value) ? null : value;
    }
}
