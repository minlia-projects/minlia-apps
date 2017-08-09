package com.myland.framework.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * controller操作日志
 * @ClassName: OperLogInterceptor
 * @Description: TODO
 * @author zhangbin
 * @date 2015年10月10日 上午11:37:30
 *
 */
@Aspect
@Slf4j
public class OperLogInterceptor{
	//@Before("execution(* com.inspur.ac.ecgate.*Action.*(..))")
	public void logAll(JoinPoint joinPoint)throws Throwable
	{
		Object obj[] = joinPoint.getArgs();
		  for(Object o :obj){
		  }           
    }
	
	//@After("execution(* com.inspur.ac.ecgate.*.*(..))")
    public void after()
	{
      //  System.out.println("after");
    }
	
	//方法执行的前后调用  
	@Around("execution(*  com.myland..*.*Controller.*(..))||execution(*  com.myland..*.*Controller.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable
    {

		
		//操作目标类型
		String targetName = point.getTarget().getClass().getName();
	
		
		//操作方法
		String methodName = point.getSignature().getName();
		
		//获取request对象
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder .getRequestAttributes()).getRequest();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
		Calendar ca = Calendar.getInstance();
		
		//操作时间
		String operDate = df.format(ca.getTime());
		
		//访问IP
		String ip=getIpAddress(request);;

		//参数转换普通Map
		Map<String,String> map=this.getParameterMap(request);
		
		if(map!=null&&!map.isEmpty()){
			String params="";
			for(String key:map.keySet()){
				params=key+":"+map.get(key)+params;
			}
		}
		
		log.info("IP:"+ip+"--"+"targetName:"+targetName+"--"+"methodName:"+methodName+"--"+"operDate:"+operDate);
		
		
		Object object=null;
		try {
			object = point.proceed();
        } catch (Exception e) {
           throw e;
        }  	

        return object;
    }
      
    //方法运行出现异常时调用  
    //@AfterThrowing(pointcut = "execution(*  com.inspur.ac.ecgate.*.*(..))",throwing = "ex")
    public void afterThrowing(Exception ex){

    }
    
    // 获取方法的中文备注____用于记录用户的操作日志描述 
    public static String getMthodRemark(ProceedingJoinPoint joinPoint)  
            throws Exception 
    {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
  
        Class targetClass = Class.forName(targetName);
        Method[] method = targetClass.getMethods();
        String methode = "";
        for (Method m : method)
        {
            if (m.getName().equals(methodName)) 
            {
                Class[] tmpCs = m.getParameterTypes();
                if (tmpCs.length == arguments.length)
                {

                    break;
                }
            }
        }
        return methode;  
    }  
    /**
     * 
     * @param request
     * @return
     */
    public String getIpAddress(HttpServletRequest request){
    	String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
    	  ip = request.getHeader("http_client_ip");  
    	}  
    	if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
    	  ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
    	}  
    	if (ip != null && ip.indexOf(",") != -1) {  
		  ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();  
		} 
        if("0:0:0:0:0:0:0:1".equals(ip))
        {
        	ip="127.0.0.1";
        }
        return ip;
    }
    
    /**
     * request参数MAP转换普通map
     * @param request
     * @return
     */
    public static Map getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if(null == valueObj){
                value = "";
            }else if(valueObj instanceof String[]){
                String[] values = (String[])valueObj;
                for(int i=0;i<values.length;i++){
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length()-1);
            }else{
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }
}