package cn.xiaod0510.jpa.findbyexample;

import cn.xiaod0510.jpa.findbyexample.fill.FillCondition;
import cn.xiaod0510.jpa.findbyexample.fill.FillNotNull;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static cn.xiaod0510.jpa.findbyexample.BaseExample.OperatorType.and;
import static cn.xiaod0510.jpa.findbyexample.BaseExample.OperatorType.or;

/**
 * Created by xiaod0510@gmail.com on 16-11-22 下午12:47.
 */
public abstract class BaseExample<T extends BaseExample, M>
        implements Specification<M> {
    public enum OperatorType {
        and, or
    }

    public enum PredicateType {
        eq, gt, lt, gtEq, ltEq, in, like, between, isNull,
        notEq, notLike, isNotNull, notIn;
    }

    protected BaseExample prev;
    protected OperatorType operatorType;
    private List<PredicateDescripe> operators = new ArrayList<PredicateDescripe>();

    public T add(String f, PredicateType t, Object v) {
        this.operators.add(new PredicateDescripe(f, t, v));
        return (T) this;
    }

    public T fill(Object pojo) {
        return fill(pojo, FillNotNull.class);
    }

    public T fill(Object pojo, Class<? extends FillCondition> fill) {
        try {
            return fill(pojo, fill.newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public T fill(Object pojo, FillCondition fill) {
        fill.fill(this, pojo);
        return (T) this;
    }

    /****/
    protected T newInstance(OperatorType type) {
        try {
            T inst = (T) this.getClass().newInstance();
            inst.prev = this;
            inst.operatorType = type;
            return inst;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * and expr
     *
     * @return
     */
    public T and() {
        return newInstance(and);
    }

    /**
     * or expr
     *
     * @return
     */
    public T or() {
        return newInstance(or);
    }

    /**
     * return prev expr
     *
     * @return
     */
    public T end() {
        return (T) (this.prev == null ? this : this.prev);
    }

    public Predicate toPredicate(Root<M> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        //find the first
        List<BaseExample> condictions = new LinkedList<BaseExample>();

        BaseExample pointer = this;
        while (pointer != null) {
            condictions.add(0, pointer);
            pointer = pointer.prev;
        }
        List<Predicate> withWhere = new ArrayList<Predicate>();
        //start with first
        for (int i = 0; i < condictions.size(); i++) {
            pointer = condictions.get(i);
            Predicate[] predicates = pointer.conditionToPredicate(root, cb);
            if (predicates == null || predicates.length == 0) continue;
            switch (pointer.operatorType) {
                case and:
                    withWhere.add(cb.and(predicates));
                    break;
                case or:
                    withWhere.add(cb.or(predicates));
                    break;
            }
        }
        return query.where(withWhere.toArray(new Predicate[]{})).getRestriction();
    }

    private Predicate[] conditionToPredicate(Root<M> root, CriteriaBuilder cb) {

        List<Predicate> result = new ArrayList<Predicate>();
        for (int i = 0; i != this.operators.size(); i++) {
            PredicateDescripe predicateDescripe = this.operators.get(i);
            Expression expr = root.get(predicateDescripe.getField());
            if (expr == null) continue;
            switch (predicateDescripe.getType()) {
                case isNotNull:
                    result.add(cb.isNotNull(expr));
                    break;
                case eq:
                    result.add(cb.equal(expr, predicateDescripe.getValue()));
                    break;
                case gt:
                    result.add(cb.greaterThan(expr, predicateDescripe.toComparable()));
                    break;
                case lt:
                    result.add(cb.lessThan(expr, predicateDescripe.toComparable()));
                    break;
                case gtEq:
                    result.add(cb.greaterThanOrEqualTo(expr, predicateDescripe.toComparable()));
                    break;
                case ltEq:
                    result.add(cb.lessThanOrEqualTo(expr, predicateDescripe.toComparable()));
                    break;
                case like:
                    result.add(cb.like(expr, predicateDescripe.toString()));
                    break;
                case between:
                    List<Comparable> params = predicateDescripe.toList();
                    result.add(cb.between(expr, params.get(0), params.get(1)));
                    break;
                case in:
                    result.add(cb.in(expr).value(predicateDescripe.toList()));
                    break;
                case isNull:
                    result.add(cb.isNull(expr));
                    break;
                case notEq:
                    result.add(cb.notEqual(expr, predicateDescripe.getValue()));
                    break;
                case notLike:
                    result.add(cb.notLike(expr, (String) predicateDescripe.getValue()));
                    break;
                case notIn:
                    result.add(cb.in(expr).value(predicateDescripe.toList()).not());

            }
        }
        return result.toArray(new Predicate[]{});
    }

    @Override
    public String toString() {
        BaseExample pointer = this;
        StringBuffer result = new StringBuffer();
        while (pointer != null) {
            StringBuffer oneExprStr = new StringBuffer();
            for (int i = 0; i < pointer.operators.size(); i++) {
                PredicateDescripe descripe = (PredicateDescripe) pointer.operators.get(i);
                if (descripe.getField() == null) continue;

                oneExprStr.append(pointer.operatorType).append(" ");
                oneExprStr.append(descripe.getField()).append(" ");
                oneExprStr.append(descripe.getType()).append(" ");
                if (descripe.getValue() != null) {
                    oneExprStr.append(descripe.getValue()).append(" ");
                }
            }
            result.insert(0, oneExprStr.toString());
            pointer = pointer.prev;
        }
        return result.toString();
    }

    /**
     * attribute describe in BaseExample
     *
     * @param <C>
     */
    public class Attr<C> extends AttrDescribe<C, T> {
        public Attr(String fieldName, T t) {
            super(fieldName, t);
        }

        public Attr(String fieldName) {
            this.name = fieldName;
            this.condiction = (T) BaseExample.this;
        }
    }
}
