package com.pmapi.service;

import com.pmapi.exception.PMException;
import com.pmapi.to.TaskTO;

public interface TaskService {
	
	public String saveTask(TaskTO task) throws PMException;
}
