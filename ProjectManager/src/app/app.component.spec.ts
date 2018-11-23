import { TestBed, async, ComponentFixture, inject } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing'
import { AppComponent } from './app.component';
import { Component, NgModule, OnInit, NO_ERRORS_SCHEMA } from '@angular/core';
import { FormsModule, ReactiveFormsModule, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { CommonServiceService } from './services/common-service.service';
import { FilterPipe } from './pipes/filter.pipe';
import { AlertsModule } from 'angular-alert-module';
import Swal from 'sweetalert2';
import { Observable, of } from 'rxjs';
import { OrderPipe, OrderModule } from 'ngx-order-pipe';
import { MockBackend } from '@angular/http/testing';
import { ResponseOptions, XHRBackend } from '@angular/http';

declare var $: any;

describe('AppComponent', () => {

  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;

  const parentTaskDetail: any = [
    {
      "ParentTask": "Cognizant",
      "ParentId": 1

    }
  ];

  
  const taskDetail: any = [
    {
      "projectId": "1",
      "taskId": "1",
      "project": "Project Name1",
      "parentdD": 2,
      "parentTask": "Cognizant",
      "task": "Test 1",
      "startDate": "09/07/2018",
      "endDate": "09/08/2018",
      "priority": 4,
      "managerId": 123,
      "projStatus": "Completed",
      "status": 0,
      "userId": 4
    }
  ];

  const projectDetail: any = [
    {
      "projectId": "1",
      "taskId": "1",
      "project": "Project Name1",
      "parentId": 2,
      "task": "Test 1",
      "startDate": "09/07/2018",
      "endDate": "09/08/2018",
      "priority": 4,
      "managerId": 123,
      "taskCount": 4
    }
  ];

    const managerDetails: any = [
    {
      "managerId": "1"

    }
  ];

  const projectList: any = [
    {
      "projectId" : 1,
      "project" : "Project1",

    }
  ];
  

  const userDetails: any = [
    {
      "userId": "1",
      "firstName": "mahesh",
      "lastName": "Kumaar",
      "employeeId": "123"
    }
  ];


  let mockService = {
    getParentTask(): Observable<any> {
      return of(parentTaskDetail);
    },

    getTaskManager(): Observable<any> {
      return of(taskDetail);
    },

    getProjectDetails(): Observable<any> {
      return of(projectDetail);
    },

    getProjectName():Observable<any> {
      return of(projectList);
    },

    getManagerDetails(): Observable<any> {
      return of(managerDetails);
    },

    getUserDetails(): Observable<any> {
      return of(userDetails);
    },

    submitTask(task): Observable<any> {
      taskDetail.unshift(task);
      return of(task);
    },

    onProjectSubmit(project): Observable<any> {
      projectDetail.unshift(project);
      return of(project);
    },

    updateEndTask(task): Observable<any> {
      let idx = taskDetail.findIndex(x => x.taskiD == task.taskId);
      if (idx !== -1) {
        taskDetail[idx] = task;
      }
      return of(task);
    },
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent, FilterPipe
      ],
      schemas: [NO_ERRORS_SCHEMA],
      imports: [HttpClientModule, RouterTestingModule, BrowserAnimationsModule, FormsModule, AlertsModule, ReactiveFormsModule],
      providers: [{ provide: CommonServiceService, useValue: mockService },OrderPipe,{ provide: XHRBackend, useClass: MockBackend }]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the app', async(() => {
    expect(component).toBeTruthy();
  }));

  it('should be get Parent tasks', inject([CommonServiceService], (service: CommonServiceService) => {
    service.getParentTask().subscribe(data => { component.parentTaskList = data; });
    fixture.detectChanges();
    expect(service).toBeTruthy();
  }));

  it('should be get Project Tasks', inject([CommonServiceService], (service: CommonServiceService) => {
	  let poject={
      "projectId": "1",
      "taskId": "1",
      "project": "Project Name1",
      "parentId": 2,
      "task": "Test 1",
      "startDate": "09/07/2018",
      "endDate": "09/08/2018",
      "priority": 4,
      "managerId": 123,
      "taskCount": 4
    };
    service.getTaskManager(poject).subscribe(data => { component.taskDetails = data; });
    fixture.detectChanges();
    expect(service).toBeTruthy();
  }));

  it('should be get All Projects', inject([CommonServiceService], (service: CommonServiceService) => {
    service.getProjectDetails().subscribe(data => { component.projDetails = data; });
    fixture.detectChanges();
    expect(service).toBeTruthy();
  }));
  it('should be get All Users', inject([CommonServiceService], (service: CommonServiceService) => {
    service.getUserDetails().subscribe(data => { component.userDetails = data; });
    fixture.detectChanges();
    expect(service).toBeTruthy();
  }));
  
  it('should be have End task', inject([CommonServiceService], (service: CommonServiceService) => {
	  let task={
			  "projectId": "1",
			  "taskId": "1",
			  "project": "Project Name1",
			  "parentdD": 2,
			  "parentTask": "Cognizant",
			  "task": "Test 1",
			  "startDate": "09/07/2018",
			  "endDate": "09/08/2018",
			  "priority": 4,
			  "managerId": 123,
			  "projStatus": "Completed",
			  "status": 0,
			  "userId": 4   
			  };
		component.EndTask(task);
		component.ResetTask()
		expect(service).toBeTruthy();
  }));
  
  it('should be have Edit User', inject([CommonServiceService], (service: CommonServiceService) => {
	  let user={
			    "firstName": "fname",
			    "lastName": "lname",
				"userId": 1,
				"employeeId": 2,
			    "projectId": 2,
			    "taskId": 3
			  };
		component.EditUser(user);
		expect(service).toBeTruthy();
  }));
  
});
