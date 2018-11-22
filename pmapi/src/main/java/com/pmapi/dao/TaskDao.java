package com.pmapi.dao;

import java.util.List;

import com.pmapi.exception.PMException;
import com.pmapi.to.TaskTO;

public interface TaskDao {

	public String saveTask(TaskTO task) throws PMException;
	
	public String saveParentTask(TaskTO task) throws PMException;

	public List<TaskTO> getParentTasks() throws PMException;

	public List<TaskTO> getAllTasks()throws PMException;

}
