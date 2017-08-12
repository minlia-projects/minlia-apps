package cn.xiaod0510.jpa.findbyexample.fill;

import cn.xiaod0510.jpa.findbyexample.BaseExample;

/**
 * Created by xiaod0510@gmail.com on 16-12-15 下午12:43.
 */
public interface FillCondition<T> {
    void fill(BaseExample baseExample, T t);
}
