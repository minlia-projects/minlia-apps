package com.myland.framework.Interceptor;
/**
 * sql分页工具类对象
 * @ClassName: PageParam
 * @Description: TODO
 * @author zhangbin
 * @date 2015年10月9日 下午5:12:58
 *
 */
public class PageableUtil {
	private int offset=0;
	private int limit=0;
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
}
