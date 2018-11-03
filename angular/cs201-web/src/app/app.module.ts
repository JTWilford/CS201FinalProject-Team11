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

@NgModule({
  declarations: [
    AppComponent,
    SidebarComponent,
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
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
