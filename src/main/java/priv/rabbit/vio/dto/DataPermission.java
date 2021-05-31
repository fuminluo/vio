package priv.rabbit.vio.dto;


import priv.rabbit.vio.config.mybatis.MybatisAuthorityInterceptor;

import java.io.Serializable;


public class DataPermission implements Serializable {

    private String alias;

    private String whereSql;

    public DataPermission(String whereSql) {
        this.whereSql = whereSql;
        MybatisAuthorityInterceptor.LOCAL_DATA_PERMISSION.set(this);
    }

    public DataPermission(String alias, String whereSql) {
        this.alias = alias;
        this.whereSql = whereSql;
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
}
