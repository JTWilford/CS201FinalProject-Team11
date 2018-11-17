import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from "./services/authentication.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'cs201-web';

  constructor(private authorizationService: AuthenticationService) {

  }

  ngOnInit() {
    this.loginUser("jwilford@usc.edu", "password");
  }

  loadOnClick() {
    console.log("not much");
  }

  loginUser(email: string, password: string) {
    this.authorizationService.login(email, password);
  }
}
