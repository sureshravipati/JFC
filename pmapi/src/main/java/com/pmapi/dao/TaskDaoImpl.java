package com.pmapi.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pmapi.constants.PMConstants;
import com.pmapi.exception.PMException;
import com.pmapi.model.ParentTask;
import com.pmapi.model.Project;
import com.pmapi.model.Task;
import com.pmapi.model.User;
import com.pmapi.to.TaskTO;

@Component
public class TaskDaoImpl implements TaskDao, PMConstants {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private SessionFactory sessionFactory;	

	@Override
	public String saveTask(TaskTO taskTo) throws PMException {	
		Session session = null;
		Transaction tx = null;
		DateFormat formatter=new SimpleDateFormat(YYYY_MM_DD);
		Task task = new Task();
		Project project=new Project();
		ParentTask parenttask=new ParentTask();
		User user=new User(); 
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();			
			if(0!=taskTo.getTaskId()) {
				task.setTaskId(taskTo.getTaskId());
			}	
			task.setTask(taskTo.getTask());
			project.setProjectId(taskTo.getProjectId());
			task.setProject(project);
			parenttask.setParentId(taskTo.getParentId());
			task.setParentTask(parenttask);
			task.setPriority(taskTo.getPriority());
			task.setStartDate(formatter.parse(taskTo.getStartDate()));
			task.setEndDate(formatter.parse(taskTo.getEndDate()));	
			task.setStatus(taskTo.getStatus());
			task=(Task) session.merge(task);
			tx.commit();
			this.updateUser(task.getTaskId(),taskTo.getUserId());
			return "Success";
		} catch(Exception ex) {
			logger.error("Exception occured in saveTask : " + ex);
			throw new PMException(TECH_ERROR_CODE, TECH_ERROR_MESSAGE, STATUS_500);
		} finally {
			session.close();
		}		
	}

	@Override
	public String saveParentTask(TaskTO task) throws PMException {
		Session session = null;
		Transaction tx = null;
		ParentTask parenttask=new ParentTask();
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();			
			if(0!=task.getTaskId()) {
				parenttask.setParentId(task.getTaskId());
			}	
			parenttask.setParentTask(task.getTask());
			session.saveOrUpdate(parenttask);
			tx.commit();
			return "Success";
		} catch(Exception ex) {
			logger.error("Exception occured in saveParentTask : " + ex);
			throw new PMException(TECH_ERROR_CODE, TECH_ERROR_MESSAGE, STATUS_500);
		} finally {
			session.close();
		}	
	}
	private void updateUser(int taskId,int userId)throws PMException {	
		Session session = null;
		Transaction tx = null;
		Set<User> useSet;
		User user=null;
		try {
			session = sessionFactory.openSession();		
			tx = session.beginTransaction();
			Task task= session.get(Task.class, taskId);
			useSet=task.getUser();
    		if(null!=useSet&&!useSet.isEmpty()) {
    			for(User userObj:useSet) {
    				userObj.setTaskId(0);
    				session.merge(userObj);
    			}
    		}
    		user=session.get(User.class, userId);
    		user.setTaskId(task.getTaskId());
			session.merge(user);
			tx.commit();
		} catch(Exception ex) {
			logger.error("Exception occured in updateUser : " + ex);
			throw new PMException(TECH_ERROR_CODE, TECH_ERROR_MESSAGE, STATUS_500);
		} finally {
			session.close();
		}
	}
}
