import { Injectable } from '@angular/core';
import {HttpRequestService} from "./http-request.service";
import {catchError, finalize, map} from "rxjs/operators";
import {of} from "rxjs";
import {Assignment} from "../interfaces/assignment.interface";
import {Lecture} from "../interfaces/lecture.interface";
import {Lab} from "../interfaces/lab.interface";
import {Exam} from "../interfaces/exam.interface";

@Injectable({
  providedIn: 'root'
})
export class DataService {
  constructor(private httpRequestService: HttpRequestService) { }

  getAssignments(): Promise<Assignment[]> {
    let promise = new Promise<Assignment[]>((resolve, reject) => {
      this.httpRequestService.getAssignmentData().pipe(
        catchError((response) => of(response["error"])),
        finalize(() => {})
      ).subscribe((response) => {
        console.log("[DataService] Assignment Request Response:");
        console.log(response);
        if(response.hasOwnProperty("error") && response["error"] !== "") {
          console.log("[DataService] There was an error with the request");
          console.log(response["error"]);
          resolve([]);    //Send back an empty array
        }
        let assignments: Assignment[] = response["data"];
        console.log("[DataService] Assignments Found:");
        console.log(assignments);

        //Sort assignments by assignment number
        assignments.sort((a,b) =>{
          if(a.number > b.number)
            return 1;
          if(a.number < b.number)
            return -1
          return 0;
        });

        resolve(assignments);   //Send back the received, sorted data
      });
    });

    return promise;
  }

  getLectures(): Promise<Lecture[]> {
    let promise = new Promise<Lecture[]>((resolve, reject) => {
      this.httpRequestService.getLectureData().pipe(
        catchError((response) => of(response["error"])),
        finalize(() => {})
      ).subscribe((response) => {
        console.log("[DataService] Lecture Request Response:");
        console.log(response);
        if(response.hasOwnProperty("error") && response["error"] !== "") {
          console.log("[DataService] There was an error with the request");
          console.log(response["error"]);
          resolve([]);    //Send back an empty array
        }
        let lectures: Lecture[] = response["data"];
        console.log("[DataService] Lectures Found:");
        console.log(lectures);
        resolve(lectures);
      });
    });
    return promise;
  }

  getLabs(): Promise<Lab[]> {
    let promise = new Promise<Lab[]>((resolve, reject) => {
      this.httpRequestService.getLabData().pipe(
        catchError((response) => of(response["error"])),
        finalize(() => {})
      ).subscribe((response) => {
        console.log("[DataService] Lab Request Response:");
        console.log(response);
        if(response.hasOwnProperty("error") && response["error"] !== "") {
          console.log("[DataService] There was an error with the request");
          console.log(response["error"]);
          resolve([]);
        }
        let labs: Lab[] = response["data"];
        console.log("[DataService] Labs Found:");
        console.log(labs);
        resolve(labs);
      });
    });
    return promise;
  }

  getExams(): Promise<Exam[]> {
    let promise = new Promise<Exam[]>((resolve, reject) => {
      this.httpRequestService.getExamData().pipe(
        catchError((response) => of(response["error"])),
        finalize(() => {})
      ).subscribe((response) => {
        console.log("[DataService] Exam Request Response:");
        console.log(response);
        if(response.hasOwnProperty("error") && response["error"] !== "") {
          console.log("[DataService] There was an error with the request");
          console.log(response["error"]);
          resolve([]);
        }
        let exams: Exam[] = response["data"];
        console.log("[DataService] Exams Found:");
        console.log(exams);
        resolve(exams);
      });
    });
    return promise;
  }
}
