import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../services/authentication.service";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  email: string;
  pass: string;

  loggedIn$ = false;

  constructor(public authenticationService: AuthenticationService) { }

  ngOnInit() {
    this.authenticationService.loggedIn$.subscribe((loggedIn) => {
      this.loggedIn$ = loggedIn;
    });
  }

  loginUser() {
    console.log("Logging in: " + this.email + "  " + this.pass);
    this.authenticationService.login(this.email, this.pass);

  }
  logout() {
    this.authenticationService.logout();
  }
}
