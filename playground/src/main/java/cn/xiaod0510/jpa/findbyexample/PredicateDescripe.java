package cn.xiaod0510.jpa.findbyexample;

import java.util.*;

/**
 * Created by xiaod0510@gmail.com on 16-11-30 上午10:14.
 */

public class PredicateDescripe<T> {
    private BaseExample.PredicateType type;
    private String field;
    private T value;

    public PredicateDescripe(String f, BaseExample.PredicateType t, T v) {
        this.type = t;
        this.field = f;
        this.value = v;
    }

    public BaseExample.PredicateType getType() {
        return type;
    }

    public void setType(BaseExample.PredicateType type) {
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Boolean toBool() {
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return false;
    }

    public Number toNumber() {
        if (value instanceof Number) {
            return (Number) value;
        }
        return null;
    }

    public Comparable<T> toComparable() {
        if (value instanceof Comparable) {
            return (Comparable) value;
        }
        return null;
    }

    public Integer toInteger() {
        return toNumber().intValue();
    }

    public Long toLong() {
        return toNumber().longValue();
    }

    public Double toDouble() {
        return toNumber().doubleValue();
    }

    public List<T> toList() {
        if (value instanceof List) {
            return (List) value;
        }
        if (value instanceof Object[]) {
            Object[] values = (Object[]) value;
            return (List) Arrays.asList(values);
        }
        return Arrays.asList(value);
    }

    public T[] toArray() {
        if (value instanceof Collection) {
            Collection<T> collection = (Collection<T>) value;
            return (T[]) collection.toArray();
        }
        if (value instanceof Object[]) {
            return (T[]) value;
        }
        return null;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}