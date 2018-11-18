import { Component, OnInit } from '@angular/core';
import { Exam } from "../../interfaces/exam.interface";
import {DataService} from "../../services/data.service";

@Component({
  selector: 'app-previous-exams',
  templateUrl: './previous-exams.component.html',
  styleUrls: ['./previous-exams.component.css']
})
export class PreviousExamsComponent implements OnInit {

  constructor(private dataService: DataService) { }

  hasLoaded = false;
  exams: Exam[];

  ngOnInit() {
    this.hasLoaded = false;
    this.dataService.getExams().then((response) => {
      console.log("[AssignmentComponent] Received Data");
      this.exams = response;
      console.log(this.exams);
      this.hasLoaded = true;
    })
  }

}
