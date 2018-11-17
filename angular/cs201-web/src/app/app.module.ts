import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SidebarComponent } from './component/sidebar/sidebar.component';
import { TopbarComponent } from './component/topbar/topbar.component';
import { HomeComponent } from './component/home/home.component';
import { LecturesLabsComponent } from './component/lectures-labs/lectures-labs.component';
import { AssignmentsComponent } from './component/assignments/assignments.component';
import { PreviousExamsComponent } from './component/previous-exams/previous-exams.component';
import { AttendanceComponent } from './component/attendance/attendance.component';
import { CourseRosterComponent } from './component/course-roster/course-roster.component';
import { PageNotFoundComponent } from './component/page-not-found/page-not-found.component';
import { ProfileComponent } from './component/profile/profile.component';
import { CalendarComponent } from './component/calendar/calendar.component';



import { HttpClientModule } from '@angular/common/http';
import {AuthenticationService} from "./services/authentication.service";
import {DataService} from "./services/data.service";

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
    CalendarComponent,
    ProfileComponent,
    TopbarComponent,
    HomeComponent,
    LecturesLabsComponent,
    AssignmentsComponent,
    PreviousExamsComponent,
    AttendanceComponent,
    CourseRosterComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
