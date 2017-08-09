package com.myland.framework.mybatis.dialect;

import com.myland.framework.Interceptor.PageableUtil;

/**
 * 
 * @ClassName: OracleDialect
 * @Description: TODO
 * @author zhangbin
 * @date 2015年10月9日 下午5:12:46
 *
 */
public class OracleDialect extends Dialect {

	public String getLimitString(String sql, PageableUtil pageparam, String sort) {
		
		if(pageparam==null){
			 return sql;
		}
		sql = sql.trim();
		boolean isForUpdate = false;
		if (sql.toLowerCase().endsWith(" for update")) {
			sql = sql.substring(0, sql.length() - 11);
			isForUpdate = true;
		}

		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		
		pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		
		pagingSelect.append(sql);
		pagingSelect.append(" ").append(sort).append(" ");
		
		pagingSelect.append(" ) row_ ) where rownum_ > "+pageparam.getOffset()+" and rownum_ <= "+(pageparam.getOffset() + pageparam.getLimit()));

		if (isForUpdate) {
			pagingSelect.append(" for update");
		}
		
		return pagingSelect.toString();
	}
}
