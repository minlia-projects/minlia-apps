package com.myland.framework.mybatis.dialect;

import com.myland.framework.Interceptor.PageableUtil;

public abstract class Dialect {
	public static enum Type {  
        MYSQL, ORACLE
    }  
  
    public abstract String getLimitString(String querySqlString, PageableUtil pageparam, String sort);
}
