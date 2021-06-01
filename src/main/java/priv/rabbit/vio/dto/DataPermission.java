package priv.rabbit.vio.dto;


import priv.rabbit.vio.config.mybatis.MybatisAuthorityInterceptor;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;


public class DataPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    private String alias;

    private String whereSql;

    /**
     * 是否启用分页插件
     */
    private boolean enPagePlug;

    private AtomicInteger atomicInteger;

    public DataPermission(String whereSql) {
        this.whereSql = whereSql;
        MybatisAuthorityInterceptor.LOCAL_DATA_PERMISSION.set(this);
    }

    public DataPermission(String alias, String whereSql) {
        this.alias = alias;
        this.whereSql = whereSql;
        MybatisAuthorityInterceptor.LOCAL_DATA_PERMISSION.set(this);
    }

    public DataPermission(String whereSql, boolean enPagePlug) {
        this.whereSql = whereSql;
        this.enPagePlug = enPagePlug;
        this.atomicInteger = new AtomicInteger(0);
        MybatisAuthorityInterceptor.LOCAL_DATA_PERMISSION.set(this);
    }

    public String getWhereSql() {
        return whereSql;
    }

    public void setWhereSql(String whereSql) {
        this.whereSql = whereSql;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean getEnPagePlug() {
        return enPagePlug;
    }

    public void setEnPagePlug(boolean enPagePlug) {
        this.enPagePlug = enPagePlug;
    }

    public AtomicInteger getAtomicInteger() {
        return atomicInteger;
    }

    public void setAtomicInteger(AtomicInteger atomicInteger) {
        this.atomicInteger = atomicInteger;
    }
}
