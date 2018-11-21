package com.pmapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmapi.dao.TaskDao;
import com.pmapi.exception.PMException;
import com.pmapi.to.TaskTO;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskDao taskDao;

	@Override
	public String saveTask(TaskTO task) throws PMException {
		if(task.isParent()) {			
			return taskDao.saveParentTask(task);
		}else {
			return taskDao.saveTask(task);
		}
	}	

}
