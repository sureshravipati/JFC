package com.pmapi.service;

import java.util.List;

import com.pmapi.exception.PMException;
import com.pmapi.to.TaskTO;

public interface TaskService {
	
	public String saveTask(TaskTO task) throws PMException;

	public List<TaskTO> getParentTasks() throws PMException;

	public List<TaskTO> getAllTasks() throws PMException;
	
}
