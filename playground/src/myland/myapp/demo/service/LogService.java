package com.myland.myapp.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.qianyi.domain.Log;

public interface LogService {
	public Page getLog(Log log,Pageable pageable);
	public void saveLog(Log log);
	public void updateLog(Log log);
	public void deleteLog(Long id);
	public void deleteAll(List list);
	public Log getLog(Long id);
}
