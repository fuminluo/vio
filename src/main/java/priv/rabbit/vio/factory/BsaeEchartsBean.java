package priv.rabbit.vio.factory;

import java.util.Map;

public abstract class BsaeEchartsBean<T> {


    protected Map<String, Object> parameters;

    protected abstract void setParameters(Map<String, Object> parameters);

    protected abstract T initEchartsData();
}

