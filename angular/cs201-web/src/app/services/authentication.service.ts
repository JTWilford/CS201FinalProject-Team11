import {HttpRequestService} from "./http-request.service";
import {UserInterface} from "../interfaces/user.interface";
import {BehaviorSubject} from "rxjs";

export class AuthenticationService {
  //The current user's information. Until the user logs in, should be null
  private currentUser: UserInterface;
  private hasLoggedIn: BehaviorSubject<boolean> = new BehaviorSubject(false);
  public $loggedIn = this.hasLoggedIn.asObservable();
  //Inject an HttpRequestService via the constructor
  constructor (private httpRequestService: HttpRequestService) {
    this.currentUser = null;
  }

  public login(username: string, password: string) {
    //TODO: Connect to backend with a web socket and subscribe to it
    //Tell observers that the user isn't logged in
    this.hasLoggedIn.next(false);
    //Make a request to the backend to login
    this.httpRequestService.loginAccount(username, password).subscribe((data) => {
      //Set the user's information based on the response
      this.currentUser.uscID = data["uscID"];
      this.currentUser.firstName = data["fname"];
      this.currentUser.lastName = data["lname"];
      this.currentUser.email = data["email"];
      this.currentUser.level = data["level"];

      switch (this.currentUser.level) {
        case 1:
          this.currentUser.levelString = "Professor";
          break;
        case 2:
          this.currentUser.levelString = "TA/CP";
          break;
        case 3:
          this.currentUser.levelString = "Student";
          break;
        default:
          this.currentUser.levelString = "Invalid Account Configuration";
          this.currentUser.level = 4;
          break;
      }
      //Now that the user has bee set, tell observers that the user has been logged in
      this.hasLoggedIn.next(true);
    });
  }

  public logout() {
    //TODO: Disconnect from the web socket
    //Tell observers that the users is no longer logged in
    this.hasLoggedIn.next(false);
    //Reset the current user's data
    this.currentUser = new class implements UserInterface {
      email: string;
      firstName: string;
      lastName: string;
      level: number;
      levelString: string;
      uscID: number;
    };
  }
}
