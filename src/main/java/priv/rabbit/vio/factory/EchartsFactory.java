package priv.rabbit.vio.factory;

import java.util.Map;

public abstract class EchartsFactory {

    public abstract AbstractBarSimple createBarSimple(Map<String, Object> parameters, Class<?> cls);

    public abstract AbstractLineSimple createLineSimple(Map<String, Object> parameters, Class<?> cls);

}
