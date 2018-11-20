import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
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
    return this.getData("lecture");
  }

  getLabData() {
    return this.getData("lab");
  }

  getExamData() {
    return this.getData("exam");
  }

  private getData(type: string): Observable<any> {
    return this.http.get(`${this.backendUrl}/Data`, {params: {FileType:type}});
  }

  //--------------------------------------------------------------------------------------------------------------------
  //  Account System Requests
  //--------------------------------------------------------------------------------------------------------------------
  loginAccount(email: string, password: string): Observable<any>{
    return this.http.get(this.backendUrl + "/Accounts");
  }
  //Should return all the data from the Accounts servlet, if the user's level is high enough
  searchAccount() {

  }
  createAccount(email: string, password: string, firstName: string, lastName: string, uscID: number, gitHub: string): Observable<any> {
    console.log("Creating new account");
    let params = new HttpParams();
    params.set("email", email);
    params.set("password", password);
    params.set("firstName", firstName);
    params.set("lastName", lastName);
    params.set("uscID", uscID + "");
    params.set("gitHub", gitHub);
    params.set("accountLevel", "Student");
    return this.http.post(`${this.backendUrl}/Accounts`, "", {params: params});
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
  postAttendance(latitude: string, longitude: string) {
    return this.http.post(this.backendUrl + "/Attendance", latitude + "," + longitude);
  }
}
