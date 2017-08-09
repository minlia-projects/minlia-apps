package com.myland.myapp.demo.action;

import com.qianyi.domain.Log;
import com.myland.myapp.demo.service.LogService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Map;
@Controller
@RequestMapping("/api/v1/demo")
public class DemoController{
	@Resource
	private LogService logService;
	
	@RequestMapping("/list")
	public String demoTest(HttpServletRequest request,HttpServletResponse response) {
		
		String page=request.getParameter("page");
		
		int pagenum=0;
		if(StringUtils.isNotBlank(page)){
			pagenum=Integer.parseInt(page);
		}
		int pagesize=2;
		
		
		
		Sort sort=new Sort(Sort.Direction.DESC, "crt_time");
		
		Pageable pageable=new PageRequest(pagenum, pagesize, sort);
		Log log=new Log();
		
		Page dataPage=logService.getLog(log,pageable);
		request.setAttribute("dataPage", dataPage);
		return "demo/list";
	}
	
	@RequestMapping("/open")
	public String open(HttpServletRequest request,HttpServletResponse response){
		if(StringUtils.isNotBlank(request.getParameter("id"))){
			Log log=logService.getLog(Long.parseLong(request.getParameter("id")));
			request.setAttribute("obj", log);
		}
		return "demo/edit";
	}
	
	@RequestMapping("save")
	public String save(HttpServletRequest request,HttpServletResponse response) throws IllegalAccessException, InvocationTargetException{
		
		Map map=request.getParameterMap();
		Log log=new Log();
		log.setCrtTime(new Date());
		BeanUtils.populate(log, map);
		logService.saveLog(log);
		return "redirect:/demo/list.do";
	}
	@RequestMapping("update")
	public String update(HttpServletRequest request,HttpServletResponse response) throws IllegalAccessException, InvocationTargetException{
		Map map=request.getParameterMap();
		Log log=new Log();
		log.setCrtTime(new Date());
		BeanUtils.populate(log, map);
		logService.updateLog(log);
		
		return "redirect:/demo/list.do";
	}
	
	@RequestMapping("delete")
	public String delete(HttpServletRequest request,HttpServletResponse response) {
		if(StringUtils.isNotBlank(request.getParameter("id"))){
			logService.deleteLog(Long.parseLong(request.getParameter("id")));
		}
		
		return "redirect:/demo/list.do";
	}
}
