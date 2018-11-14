import { Component, OnInit } from '@angular/core';
import {DataService} from "../../services/data.service";
import {Assignment} from "../../interfaces/assignment.interface";

@Component({
  selector: 'app-assignments',
  templateUrl: './assignments.component.html',
  styleUrls: ['./assignments.component.css']
})
export class AssignmentsComponent implements OnInit {

  constructor(private dataService: DataService) { }

  hasLoaded = false;
  assignments: Assignment[];

  ngOnInit() {
    this.hasLoaded = false;
    this.dataService.getAssignments().then((response) => {
      console.log("[AssignmentComponent] Recieved Data");
      this.assignments = response;
      console.log(this.assignments);
      this.hasLoaded = true;
    })
  }

}
