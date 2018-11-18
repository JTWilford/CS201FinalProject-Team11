import {HttpRequestService} from "./http-request.service";
import {UserInterface} from "../interfaces/user.interface";
import {BehaviorSubject, Subject} from "rxjs";
import {WebsocketService} from "./websocket.service";
import {Injectable} from "@angular/core";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  //The current user's information. Until the user logs in, should be null
  private currentUser: UserInterface = {
    firstName: "",
    lastName: "",
    email: "",
    uscID: 0,
    level: 0,
    authorized: false
  };
  private hasLoggedIn: BehaviorSubject<boolean> = new BehaviorSubject(false);
  public $loggedIn = this.hasLoggedIn.asObservable();
  private socket: Subject<Message>;

  //Inject an HttpRequestService via the constructor
  constructor (private httpRequestService: HttpRequestService,
               private webSocketService: WebsocketService) {
    this.currentUser = null;
  }

  public login(email: string, password: string) {
    this.socket = <Subject<Message>>this.webSocketService.connect().pipe(
      map((response: MessageEvent) => {
        // console.log(response);
        let data = JSON.parse(response.data);
        let message = {
          error: "",
          data: []
        };
        if(data.hasOwnProperty("error")) {
          message.error = data["error"];
        }
        if(data.hasOwnProperty("data")) {
          message.data = data["data"];
        }
        return <Message>message;
      })
    );
    this.socket.subscribe((response) => {
      console.log("[AuthenticationService] Received Response: ");
      console.log(response);
      if(response.error !== "") {
        console.log("[AuthorizationService] " + response.error);
        alert(response.error);
        this.hasLoggedIn.next(false);
      }
      else {
        if(response.data.hasOwnProperty("authorized")) {    //Login info was sent
          this.currentUser.authorized = response.data["authorized"];
          this.currentUser.firstName = response.data["firstName"];
          this.currentUser.lastName = response.data["lastName"];
          this.currentUser.email = response.data["email"];
          this.currentUser.uscID = response.data["uscID"];
          this.currentUser.level = response.data["level"];
          //Tell observers that the user is now logged in
          this.hasLoggedIn.next(true);
        }
      }
    });
    setTimeout(() => {
      // this.webSocketService.login("JWilford@usc.edu", "pass");
      this.webSocketService.login(email, password);
    }, 1000);
  }

  public logout() {
    //TODO: Disconnect from the web socket
    //Tell observers that the users is no longer logged in
    this.hasLoggedIn.next(false);
    //Reset the current user's data
    this.currentUser = {
      firstName: "",
      lastName: "",
      email: "",
      uscID: 0,
      level: 0,
      authorized: false
    }
  }
}

export interface Message {
  error: string,
  data: object
}
