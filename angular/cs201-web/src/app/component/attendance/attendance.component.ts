import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-attendance',
  templateUrl: './attendance.component.html',
  styleUrls: ['./attendance.component.css']
})
export class AttendanceComponent implements OnInit {

  shouldShow: boolean = false;
  private hasGeolocation = false;

  constructor() { }

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
      });
    }
    else {
      console.log("Cannot get location");
    }
  }

  toggleShow() {
    this.shouldShow = !this.shouldShow;
  }

}
