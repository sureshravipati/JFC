import { Component, NgModule, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';


//var vURL = "http://localhost:8050/";
 var vURL = "http://localhost:10326/";

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
};


@Injectable({
  providedIn: 'root'
})

export class CommonServiceService {

  constructor(public http: HttpClient) { }

  // Common Code
  getParentTask() {
    return this.http.get(vURL + "api/TaskManager/GetParentTaskList");
  }

  // Code for Project screen

  getProjectDetails() {
    return this.http.get(vURL + "api/Project/GetProjectDetails");
  }

  getManagerDetails() {
    return this.http.get(vURL + "api/Project/GetManagerDetails");
  }

  getProjectName() {
    return this.http.get(vURL + "api/Project/GetProjectName");
  }

  submitProject(project) {
    return this.http.post(vURL + "api/Project/SubmitProjectDetail", project, httpOptions);
  }

  SuspendProject(project) {
    return this.http.post(vURL + "api/Project/SuspendProjectDetail", project, httpOptions);
  }
  // Code for Task screen

  getTaskManager() {
    return this.http.get(vURL + "api/TaskManager/GetTaskDetailList");
  }

  submitTask(task) {
    return this.http.post(vURL + "api/TaskManager/SubmitTaskDetail", task, httpOptions);
  }

  updateEndTask(task) {
    return this.http.post(vURL + "api/TaskManager/UpdateEndTask", task);
  }

  // Code for User screen 


  getUserDetails() {
    return this.http.get(vURL + "api/User/GetUserDetails");
  }

  submitUser(user) {
    return this.http.post(vURL + "api/User/SubmitUserDetail", user, httpOptions);
  }

  deleteUser(user) {
    return this.http.post(vURL + "api/User/DeleteUserDetail", user, httpOptions);
  }


};
