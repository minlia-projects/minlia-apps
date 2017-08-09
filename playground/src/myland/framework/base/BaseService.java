package com.myland.framework.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.util.Assert;

import com.myland.framework.exption.ServiceException;
import com.myland.framework.utils.MyBeanUtils;
import com.myland.framework.utils.SqlId;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * 通用Service工具类
 * @ClassName: BaseService
 * @Description: TODO
 * @author zhangbin
 * @date 2015年10月9日 下午5:13:41
 *
 * @param <T>
 */
public abstract class BaseService<T extends Serializable>{
	
	@Resource
	private SqlSessionTemplate sqlSessionTemplate ;
	
	public static final String SQLNAME_SEPARATOR = "Mapper.";
	
	private String sqlNamespace = getDefaultSqlNamespace(); 

	public String getSqlNamespace() {  
        return sqlNamespace;  
    }
	
	public void setSqlNamespace(String sqlNamespace) {  
        this.sqlNamespace = sqlNamespace;  
    }
	
	/** 
     * 将SqlMapping命名空间与给定的SqlMapping名组合在一起。 
     * @param sqlName SqlMapping名  
     * @return 组合了SqlMapping命名空间后的完整SqlMapping名  
     */  
	protected String getSqlName(String sqlName) {  
        return sqlNamespace + SQLNAME_SEPARATOR + sqlName;  
    }

	
	
	public SqlSessionTemplate getSqlSessionTemplate() {
		return sqlSessionTemplate;
	}

	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	/**
	 * Mapper命名空间
	 * @Title: getDefaultSqlNamespace
	 * @Description: TODO
	 * @return
	 */
	protected String getDefaultSqlNamespace() {  
        Class<?> genericClass = MyBeanUtils.getGenericClass(this.getClass());  
        return genericClass == null ? null : genericClass.getName();  
    }
	/**
	 * 根据条件查询单条记录
	 * @Title: selectOne
	 * @Description: TODO
	 * @param query
	 * @return
	 */
	public <V extends T> V selectOne(T query) {
		Assert.notNull(query);  
        try {  
            Map<String, Object> params = MyBeanUtils.toMap(query);  
            return sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT), params);  
        } catch (Exception e) {  
            throw new ServiceException(String.format("查询一条记录出错！语句：%s", getSqlName(SqlId.SQL_SELECT)), e);  
        }
	}
	/**
	 * 根据主键查询记录
	 * @Title: selectByKey
	 * @Description: TODO
	 * @param id
	 * @return
	 */
	public <V extends T> V selectByKey(Long id) {
		Assert.notNull(id);  
        try {  
            return sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT_ONE), id);  
        } catch (Exception e) {  
            throw new ServiceException(String.format("查询一条记录出错！语句：%s", getSqlName(SqlId.SQL_SELECT_ONE)), e);  
        }
	}
	/**
	 * 根据条件查询多条记录
	 * @Title: selectList
	 * @Description: TODO
	 * @param query
	 * @return
	 */
	public <V extends T> List<V> selectList(T query) {  
        try {  
            Map<String, Object> params = MyBeanUtils.toMap(query);  
            return sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT), params);  
        } catch (Exception e) {  
            throw new ServiceException(String.format("查询对象列表出错！语句：%s", getSqlName(SqlId.SQL_SELECT)), e);  
        }  
    }
	/**
	 * 查询所有记录
	 * @Title: selectAll
	 * @Description: TODO
	 * @return
	 */
	public <V extends T> List<V> selectAll() {  
        try {  
            return sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT));  
        } catch (Exception e) {  
            throw new ServiceException(String.format("查询所有对象列表出错！语句：%s", getSqlName(SqlId.SQL_SELECT)), e);  
        }  
    }
	/**
	 * 根据条件查询记录返回Map
	 * @Title: selectMap
	 * @Description: TODO
	 * @param query
	 * @param mapKey
	 * @return
	 */
	public <K, V extends T> Map<K, V> selectMap(T query, String mapKey) {  
        Assert.notNull(mapKey, "[mapKey] - must not be null!");  
        try {  
            Map<String, Object> params = MyBeanUtils.toMap(query);  
            return sqlSessionTemplate.selectMap(getSqlName(SqlId.SQL_SELECT), params, mapKey);  
        } catch (Exception e) {  
            throw new ServiceException(String.format("查询对象Map时出错！语句：%s", getSqlName(SqlId.SQL_SELECT)), e);  
        }  
    }
	
	/** 
     * 设置分页 
     * @param pageInfo 分页信息 
     * @return SQL分页参数对象 
     */
	protected RowBounds getRowBounds(Pageable pageable) {  
        RowBounds bounds = RowBounds.DEFAULT;  
        if (null != pageable) {  
            bounds = new RowBounds(pageable.getOffset(), pageable.getPageSize());  
        }  
        return bounds;  
    }
	/** 
     * 获取分页查询参数 
     * @param query 查询对象 
     * @param pageable 分页对象 
     * @return Map 查询参数 
     */
	protected Map<String, Object> getParams(T query, Pageable pageable) {  
        Map<String, Object> params = MyBeanUtils.toMap(query, getRowBounds(pageable));  
        if (pageable != null && pageable.getSort() != null) {  
            String sorting = pageable.getSort().toString();  
            params.put("sorting", sorting.replace(":", ""));  
        }  
        return params;  
    }
	/**
	 * 分页查询
	 * @Title: selectList
	 * @Description: TODO
	 * @param query
	 * @param pageable
	 * @return
	 */
	public <V extends T> List<V> selectList(T query, Pageable pageable) {  
        try {  
        	List<V> contentList= sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT), getParams(query, pageable));  
        	return contentList;
        } catch (Exception e) {  
            throw new ServiceException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SqlId.SQL_SELECT)), e);  
        }  
    }
	/**
	 * 分页查询
	 * @Title: selectPageList
	 * @Description: TODO
	 * @param query
	 * @param pageable
	 * @return
	 */
	public <V extends T> Page<V> selectPageList(T query, Pageable pageable) {  
        try {  
            List<V> contentList = sqlSessionTemplate.selectList(getSqlName(SqlId.SQL_SELECT),  
                    getParams(query, pageable));  
            return new PageImpl<V>(contentList, pageable, this.selectCount(query));  
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SqlId.SQL_SELECT)), e);  
        }  
    }
	
	public <K, V extends T> Map<K, V> selectMap(T query, String mapKey, Pageable pageable) {  
        try {  
            return sqlSessionTemplate.selectMap(getSqlName(SqlId.SQL_SELECT), getParams(query, pageable), mapKey);  
        } catch (Exception e) {  
            throw new ServiceException(String.format("根据分页对象查询列表出错！语句:%s", getSqlName(SqlId.SQL_SELECT)), e);  
        }  
    }
	
	public Long selectCount() {  
        try {  
            return sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT_COUNT));  
        } catch (Exception e) {  
            throw new ServiceException(String.format("查询对象总数出错！语句：%s", getSqlName(SqlId.SQL_SELECT_COUNT)), e);  
        }  
    }
	
	public Long selectCount(T query) {  
        try {  
            Map<String, Object> params = MyBeanUtils.toMap(query);  
            return sqlSessionTemplate.selectOne(getSqlName(SqlId.SQL_SELECT_COUNT), params);  
        } catch (Exception e) {  
            throw new ServiceException(String.format("查询对象总数出错！语句：%s", getSqlName(SqlId.SQL_SELECT_COUNT)), e);  
        }  
    }
	
	public int insert(T entity) {  
        Assert.notNull(entity);  
        try {  
//            if (StringUtils.isBlank(entity.getId()))  
//                entity.setId(generateId());  
            return sqlSessionTemplate.insert(getSqlName(SqlId.SQL_INSERT), entity);  
        } catch (Exception e) {  
            throw new ServiceException(String.format("添加对象出错！语句：%s", getSqlName(SqlId.SQL_INSERT)), e);  
        }  
    }
	
	public int delete(T query) {  
        Assert.notNull(query);  
        try {  
            Map<String, Object> params = MyBeanUtils.toMap(query);  
            return sqlSessionTemplate.delete(getSqlName(SqlId.SQL_DELETE), params);  
        } catch (Exception e) {  
            throw new ServiceException(String.format("删除对象出错！语句：%s", getSqlName(SqlId.SQL_DELETE)), e);  
        }  
    }
	
	public int deleteByKey(Long id) {  
        Assert.notNull(id);  
        try {  
            return sqlSessionTemplate.delete(getSqlName(SqlId.SQL_DELETE_BY_ID), id);  
        } catch (Exception e) {  
            throw new ServiceException(String.format("根据ID删除对象出错！语句：%s", getSqlName(SqlId.SQL_DELETE_BY_ID)), e);  
        }  
    } 
	
	public int deleteAll() {  
        try {  
            return sqlSessionTemplate.delete(getSqlName(SqlId.SQL_DELETE));  
        } catch (Exception e) {  
            throw new ServiceException(String.format("删除所有对象出错！语句：%s", getSqlName(SqlId.SQL_DELETE)), e);  
        }  
    }
	
	public int updateById(T entity) {  
        Assert.notNull(entity);  
        try {  
            return sqlSessionTemplate.update(getSqlName(SqlId.SQL_UPDATE_BY_ID), entity);  
        } catch (Exception e) {  
            throw new ServiceException(String.format("根据ID更新对象出错！语句：%s", getSqlName(SqlId.SQL_UPDATE_BY_ID)), e);  
        }  
    }
	
	public int updateByIdSelective(T entity) {  
        Assert.notNull(entity);  
        try {  
            return sqlSessionTemplate.update(getSqlName(SqlId.SQL_UPDATE_BY_ID_SELECTIVE), entity);  
        } catch (Exception e) {  
            throw new ServiceException(String.format("根据ID更新对象某些属性出错！语句：%s", getSqlName(SqlId.SQL_UPDATE_BY_ID_SELECTIVE)),  
                    e);  
        }  
    } 
	
	public void deleteByIdInBatch(List<Long> idList) {  
        if (idList == null || idList.isEmpty())  
            return;  
        for (Long id : idList) {  
            this.deleteByKey(id);  
        }  
    }
	
	public void updateInBatch(List<T> entityList) {  
        if (entityList == null || entityList.isEmpty())  
            return;  
        for (T entity : entityList) {  
            this.updateByIdSelective(entity);  
        }  
    }
	
	public void insertInBatch(List<T> entityList) {  
        if (entityList == null || entityList.isEmpty())  
            return;  
        for (T entity : entityList) {  
            this.insert(entity);  
        }  
    } 
}
