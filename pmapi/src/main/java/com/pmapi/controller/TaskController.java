package com.pmapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmapi.constants.PMConstants;
import com.pmapi.exception.PMException;
import com.pmapi.service.TaskService;
import com.pmapi.to.TaskTO;

@RestController
@RequestMapping("TaskManager")
public class TaskController implements PMConstants {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TaskService taskService;		
	
	@PostMapping(value = "/SubmitTaskDetail")
	public boolean submitTaskDetail(@RequestBody TaskTO taskTO) throws PMException {	
		String status;
		try {
			status = taskService.saveTask(taskTO);
			if("Success".contentEquals(status)) return true;
		} catch(PMException ex) {
			logger.error("Exception occured in SubmitProjectDetail : "+ ex);
			throw new PMException(ex.getErrorCode(), ex.getErrorMessage(), ex.getStatus());
		}
		return false;
	}
	
	@GetMapping(value = "/GetParentTaskList")
	public List<TaskTO> getParentTaskList() throws PMException {	
		List<TaskTO> list=null;
		try {
			list= taskService.getParentTasks();
		} catch(PMException ex) {
			logger.error("Exception occured in getParentTaskList : "+ ex);
			throw new PMException(ex.getErrorCode(), ex.getErrorMessage(), ex.getStatus());
		}
		return list;
	}
	
	@GetMapping(value="/GetTaskDetailList")
	public List<TaskTO> getTaskDetailList() throws PMException {		
		List<TaskTO> list=null;
		try {
			list= taskService.getAllTasks();
		} catch(PMException ex) {
			logger.error("Exception occured in getParentTaskList : "+ ex);
			throw new PMException(ex.getErrorCode(), ex.getErrorMessage(), ex.getStatus());
		}
		return list;	
	}
}