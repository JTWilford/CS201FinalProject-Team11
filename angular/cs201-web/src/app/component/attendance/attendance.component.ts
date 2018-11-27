import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../services/authentication.service";
import {HttpRequestService} from "../../services/http-request.service";
import {Attendance} from "../../interfaces/attendance.interface";

@Component({
  selector: 'app-attendance',
  templateUrl: './attendance.component.html',
  styleUrls: ['./attendance.component.css']
})
export class AttendanceComponent implements OnInit {

  shouldShow: boolean = false;
  private hasGeolocation = false;
  private classSelection = 1;
  private entries: Attendance[];

  constructor(private authenticationService: AuthenticationService,
              private httpRequestService: HttpRequestService) { }

  ngOnInit() {
    if("geolocation" in navigator) {
      console.log("User has geolocation enabled!");
      this.hasGeolocation = true;
    }
    else {
      console.log("User doesn't have geolocation enabled");
      this.hasGeolocation = false;
    }
  }

  getLocation() {
    if (this.hasGeolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        console.log("latitude: " + position.coords.latitude);
        console.log("longitude: " + position.coords.longitude);

        this.httpRequestService.postAttendance(position.coords.latitude, position.coords.longitude,
                                                this.authenticationService.getID(), this.classSelection)
          .subscribe((response) => {
            console.log("Here's the response: ");
            console.log(response);
            if(response.hasOwnProperty("error")) {
              if(response["error"] !== "") {
                alert("Check-in was unsuccessful: \n" + response["error"]);
              }
              else {
                alert("Check-in was successful!");
                this.getAttendance();
              }
            }
            else {
              alert("There was an unexpected response");
            }
        });
      });
    }
    else {
      console.log("Cannot get location");
      alert("Geolocation is disabled on your device");
    }
  }

  toggleShow() {
    this.shouldShow = !this.shouldShow;
    if(this.shouldShow) {
      this.getAttendance();
    }
  }

  getAttendance() {
    this.httpRequestService.getAttendance(this.authenticationService.getID()).subscribe((response) => {
      if(response.hasOwnProperty("error")) {
        if(response["error"] !== "") {
          alert("There was an error retrieving attendance: " + response["error"]);
        }
        else {
          this.entries = response["data"];
          console.log(this.entries);
        }
      }
    });
  }

  getPeriod(id: number) {
    switch (id) {
      case 1:
        return "T/TH 8:00 A.M.";
        break;
      case 2:
        return "T/TH 9:30 A.M.";
        break;
      case 3:
        return "T/TH 11:00 A.M.";
        break;
      default:
        return "Invalid class";
        break;
    }
  }

}
