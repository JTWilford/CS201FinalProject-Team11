import { Component, OnInit } from '@angular/core';
import {HttpRequestService} from "../../services/http-request.service";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication.service";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  email: string;
  password: string;
  firstName: string;
  lastName: string;
  gitHub: string;
  uscID: number;

  constructor(private httpRequestService: HttpRequestService,
              private router: Router,
              private authenticationService: AuthenticationService) { }

  ngOnInit() {
  }

  createAccount() {
    this.httpRequestService.createAccount(this.email, this.password, this.firstName, this.lastName, this.uscID, this.gitHub)
      .subscribe((response) => {
        console.log("[Sign-up] Response!");
        console.log(response);
        if(response.hasOwnProperty("error") && response["error"] !== "") {
          alert("There was an error when creating the account: " + response["error"]);
        }
        else {
          alert("Account created successfully!");
          //Log the user in, then route to the home page
          this.authenticationService.login(this.email, this.password);
          this.authenticationService.loggedIn$.subscribe((loggedin) => {
            this.router.navigate(["/home"]);
          })
        }
      })
  }

}
