import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';
import { FormsModule } from '@angular/forms';

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
import { CommonModule } from '@angular/common';
import { CalendarModule, DateAdapter } from 'angular-calendar';






import { HttpClientModule } from '@angular/common/http';
import { SignUpComponent } from './component/sign-up/sign-up.component';

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
    PageNotFoundComponent,
    SignUpComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    CommonModule,
    FormsModule,
    CalendarModule.forRoot({
      provide: DateAdapter,
      useFactory: adapterFactory
    })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
