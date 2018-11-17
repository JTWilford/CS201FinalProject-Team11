import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomeComponent} from "./component/home/home.component";
import {AssignmentsComponent} from "./component/assignments/assignments.component";
import {AttendanceComponent} from "./component/attendance/attendance.component";
import {CourseRosterComponent} from "./component/course-roster/course-roster.component";
import {LecturesLabsComponent} from "./component/lectures-labs/lectures-labs.component";
import {PreviousExamsComponent} from "./component/previous-exams/previous-exams.component";
import {PageNotFoundComponent} from "./component/page-not-found/page-not-found.component";
import {ProfileComponent} from "./component/profile/profile.component";


const routes: Routes = [
  {path: 'assignments', component: AssignmentsComponent},
  {path: 'attendance', component: AttendanceComponent},
  {path: 'course-roster', component: CourseRosterComponent},
  {path: 'home', component: HomeComponent},
  {path: 'lectures-labs', component: LecturesLabsComponent},
  {path: 'previous-exams', component: PreviousExamsComponent},
  {path: 'profile', component: ProfileComponent},


  {path: '', redirectTo:'/home', pathMatch: 'full'},
  {path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
