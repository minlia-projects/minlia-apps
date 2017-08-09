package com.myland.myapp.demo.service;

import com.myland.framework.base.BaseService;
import com.qianyi.domain.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * 
 * @ClassName: LogServiceImpl
 * @Description: TODO
 * @author zhangbin
 * @date 2015年9月14日 下午4:31:25
 *
 */
@Service("logService")
public class LogServiceImpl extends BaseService<Log> implements LogService {
//	Logger log = LogManager.getLogger(LogServiceImpl.class);
	public Page getLog(Log log, Pageable pageable) {
		Page page= super.selectPageList(log, pageable);
		return page;
	}


	@Override
	@Transactional
	public void saveLog(Log log) {	
		super.insert(log);
	}



	@Override
	public void updateLog(Log log) {
		// TODO Auto-generated method stub
		super.updateById(log);
	}


	@Override
	public void deleteLog(Long id) {
		// TODO Auto-generated method stub
		super.deleteByKey(id);
	}


	@Override
	public void deleteAll(List idList) {
		// TODO Auto-generated method stub
		super.deleteByIdInBatch(idList);
	}


	@Override
	public Log getLog(Long id) {
		// TODO Auto-generated method stub
		return super.selectByKey(id);
	}	
}
