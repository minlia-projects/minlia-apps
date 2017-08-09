package com.myland.framework.mybatis.dialect;

import com.myland.framework.Interceptor.PageableUtil;
/**
 * 
 * @ClassName: MySql5Dialect
 * @Description: TODO
 * @author zhangbin
 * @date 2015年10月9日 下午5:12:39
 *
 */
public class MySql5Dialect extends Dialect{
	public String getLimitString(String querySqlString, PageableUtil pageparam, String sort) {
		if(pageparam==null){
			 return querySqlString;
		}
        return querySqlString +" "+sort+" " + " limit " + pageparam.getOffset() + " ," + pageparam.getLimit();  
    }
  
    public boolean supportsLimit() {  
        return true;  
    }
}
