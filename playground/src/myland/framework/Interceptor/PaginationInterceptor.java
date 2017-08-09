package com.myland.framework.Interceptor;


import com.myland.framework.mybatis.dialect.Dialect;
import com.myland.framework.mybatis.dialect.MySql5Dialect;
import com.myland.framework.mybatis.dialect.OracleDialect;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.Configuration;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

/**
 * mybatis分页拦截器
 * @ClassName: PaginationInterceptor
 * @Description: TODO
 * @author zhangbin
 * @date 2015年10月9日 下午5:11:30
 *
 */
@Intercepts({ @Signature(method = "prepare", type = StatementHandler.class, args = { Connection.class }) })
public class PaginationInterceptor implements Interceptor {
	private final static Log log = LogFactory.getLog(PaginationInterceptor.class);

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

        ParameterHandler parameterHandler = statementHandler.getParameterHandler();
        Object parameterObject = parameterHandler.getParameterObject();
        
        //分页工具类
        PageableUtil pageparam=null;
        
        if(parameterObject instanceof Map){
        	//参数对象
        	Map  paramMapObject = (Map)parameterObject ;
	
        	if(paramMapObject.get("limit")!=null&&paramMapObject.get("offset")!=null){
        		
        		pageparam =new PageableUtil();
        		Integer tmp=(Integer)paramMapObject.get("limit");
        		int limit=tmp.intValue();

        		Integer tmp2=(Integer)paramMapObject.get("offset");
        		int offset=tmp2.intValue();
        		
        		pageparam.setLimit(limit);
        		pageparam.setOffset(offset);
        	}
        	
        	String sort=" ";
        	
        	if(paramMapObject.get("sorting")!=null){
        		sort = "order by  "+(String)paramMapObject.get("sorting");
        	}
        			

            MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(),new DefaultReflectorFactory());
            Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
            Dialect.Type databaseType = null;

            try {
                databaseType = Dialect.Type.valueOf(configuration.getVariables().getProperty("dialect").toUpperCase());
            } catch (Exception e) {
                throw new Exception("Generate SQL: Obtain DatabaseType Failed!");
            }

            Dialect dialect = null;
            switch (databaseType) {
                case MYSQL:
                    dialect = new MySql5Dialect();
                    break;
                case ORACLE:
                    dialect = new OracleDialect();
                    break;
            }

            String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
            
            originalSql=dialect.getLimitString(originalSql, pageparam,sort);
            
            metaStatementHandler.setValue("delegate.boundSql.sql", originalSql);

            if (log.isDebugEnabled()) {
                BoundSql boundSql = statementHandler.getBoundSql();
                log.debug("Generate SQL : " + boundSql.getSql());
            }

            return invocation.proceed();
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
