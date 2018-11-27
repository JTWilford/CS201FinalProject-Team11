import { Component, OnInit } from '@angular/core';
import {DataService} from "../../services/data.service";
import {Lecture} from "../../interfaces/lecture.interface";
import {Lab} from "../../interfaces/lab.interface";



@Component({
  selector: 'app-lectures-labs',
  templateUrl: './lectures-labs.component.html',
  styleUrls: ['./lectures-labs.component.css']
})
export class LecturesLabsComponent implements OnInit {

  constructor(private dataService: DataService) { }

  hasLoadedLectures = false;
  hasLoadedLabs = false;
  labs: Lab[];
  lectures: Lecture[];

  ngOnInit() {
    this.hasLoadedLectures = false;
    this.hasLoadedLabs = false;
    this.dataService.getLectures().then((response) => {
      console.log("[LectureComponent] Received Data");
      this.lectures = response;
      console.log(this.lectures);
      this.hasLoadedLectures = true;
    });
    this.dataService.getLabs().then((response) => {
      console.log("[LabComponent] Received Data");
      this.labs = response;
      console.log(this.labs);
      this.hasLoadedLabs = true;
    })
  }

}
