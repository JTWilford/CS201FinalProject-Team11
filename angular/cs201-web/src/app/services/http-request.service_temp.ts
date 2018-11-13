import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {catchError, map} from "rxjs/operators";

export class HttpRequestService {

  private backendUrl = "http://localhost:8080";
  //Inject an HttpClient via the constructor
  constructor (private http: HttpClient) {}

  //--------------------------------------------------------------------------------------------------------------------
  //  Data/Table System Requests
  //--------------------------------------------------------------------------------------------------------------------
  getAssignmentData(): Observable<any>{
    return this.getData("assignment");
  }

  getLectureData() {

  }

  getLabData() {

  }

  getExamData() {

  }

  private getData(type: string): Observable<any> {
    return this.http.get(this.backendUrl + "/Data", {params: {type:type}});
  }

  //--------------------------------------------------------------------------------------------------------------------
  //  Account System Requests
  //--------------------------------------------------------------------------------------------------------------------
  loginAccount(email: string, password: string): Observable<any>{
    return this.http.get(this.backendUrl + "Accounts");
  }
  //Should return all the data from the Accounts servlet, if the user's level is high enough
  searchAccount() {

  }
  createAccount(email: string, password: string, firstName: string, lastName: string, uscID: number, accountLevel: string) {

  }
  updateAccount() {

  }

  //--------------------------------------------------------------------------------------------------------------------
  //  Calendar System Requests
  //--------------------------------------------------------------------------------------------------------------------
  getCalendar() {

  }

  //--------------------------------------------------------------------------------------------------------------------
  //  Attendance System Requests
  //--------------------------------------------------------------------------------------------------------------------
  getAttendance() {

  }
  postAttendance() {

  }
}
