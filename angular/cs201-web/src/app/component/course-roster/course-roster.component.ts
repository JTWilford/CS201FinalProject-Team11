import { Component, OnInit } from '@angular/core';
import {HttpRequestService} from "../../services/http-request.service";
import {UserInterface} from "../../interfaces/user.interface";

@Component({
  selector: 'app-course-roster',
  templateUrl: './course-roster.component.html',
  styleUrls: ['./course-roster.component.css']
})
export class CourseRosterComponent implements OnInit {

  private queryString = "";
  private hasData = false;
  private hasLoaded = false;
  private results: UserInterface[];
  constructor(private httpRequestService: HttpRequestService) { }

  ngOnInit() {
    this.searchAccounts()
  }

  searchAccounts() {
    this.hasLoaded = false;
    this.httpRequestService.searchAccount(this.queryString).subscribe((response) => {
      if(response.hasOwnProperty("error")) {
        if(response["error"] !== "") {
          console.log("Search was unsuccessful: " + response["error"]);
          alert(response["error"]);
        }
        else if(response.hasOwnProperty("data")) {
          console.log(response["data"]);
          this.results = response["data"];
          console.log("results: ");
          console.log(this.results);
          if(this.results.length !== 0) {
            this.hasData = true;
          }
          else {
            this.hasData = false;
          }
          this.hasLoaded = true;
        }
      }
    })
  }

}
