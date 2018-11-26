import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../services/authentication.service";
import {HttpRequestService} from "../../services/http-request.service";

@Component({
  selector: 'app-attendance',
  templateUrl: './attendance.component.html',
  styleUrls: ['./attendance.component.css']
})
export class AttendanceComponent implements OnInit {

  shouldShow: boolean = false;
  private hasGeolocation = false;

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
                                                this.authenticationService.getID())
          .subscribe((response) => {
            console.log("Here's the response: ");
            console.log(response);
            if(response.hasOwnProperty("error")) {
              if(response["error"] !== "") {
                alert("Check-in was unsuccessful: \n" + response["error"]);
              }
            }
            else {
              alert("Check-in was successful!");
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
  }

}
