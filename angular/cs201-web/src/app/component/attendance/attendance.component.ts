import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-attendance',
  templateUrl: './attendance.component.html',
  styleUrls: ['./attendance.component.css']
})
export class AttendanceComponent implements OnInit {

  shouldShow: boolean = false;

  constructor() { }

  ngOnInit() {
  }

  toggleShow() {
    this.shouldShow = !this.shouldShow;
  }

}
