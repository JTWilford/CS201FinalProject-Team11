import { Injectable } from '@angular/core';
import {Subject} from "rxjs";
import {WebsocketService} from "./websocket.service";
import {environment} from "../../environments/environment";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  public messages: Subject<string>;

  constructor(private wsService: WebsocketService) {
    this.messages = <Subject<string>>wsService
      .connect("ws://localhost:8080" +environment.websocketURL)
      .pipe(
        map((response: MessageEvent): string => {
          return(response.data)
        })
      );
  }
}
