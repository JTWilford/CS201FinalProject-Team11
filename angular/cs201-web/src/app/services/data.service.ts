import { Injectable } from '@angular/core';
import {HttpRequestService} from "./http-request.service";
import {catchError, finalize, map} from "rxjs/operators";
import {of} from "rxjs";
import {Assignment} from "../interfaces/assignment.interface";

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
        if(response.hasOwnProperty("error") && response["error"] != "") {
          console.log("[DataService] There was an error with the request")
          console.log(response["error"]);
          resolve([]);    //Send back an empty array
        }
        let assignments: Assignment[] = response["data"];
        console.log("[DataService] Assignments Found:");
        console.log(assignments);
        resolve(assignments);   //Send back the received data
      });
    });

    return promise;
  }
}
