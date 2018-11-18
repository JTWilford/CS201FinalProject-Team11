import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable, Observer, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  constructor() { }

  private subject: Subject<MessageEvent>;
  private ws: WebSocket;

  private hasConnected: BehaviorSubject<boolean> = new BehaviorSubject(false)
  public $connected: Observable<boolean> = this.hasConnected.asObservable();

  public connect(url = "ws://localhost:8080/session"): Subject<MessageEvent> {
    if (!this.subject) {
      this.subject = this.create(url);
      console.log("Successfully connected: " + url);
      this.hasConnected.next(true);
    }
    return this.subject;
  }

  private create(url): Subject<MessageEvent> {
    this.ws = new WebSocket(url);

    let observable = Observable.create(
      (obs: Observer<MessageEvent>) => {
        this.ws.onmessage = obs.next.bind(obs);
        this.ws.onerror = obs.error.bind(obs);
        this.ws.onclose = obs.complete.bind(obs);
        return this.ws.close.bind(this.ws);
      });
    let observer = {
      next: (data: Object) => {
        if (this.ws.readyState === WebSocket.OPEN) {
          this.ws.send(JSON.stringify(data));
        }
        else {
          console.log("[WebSocketService] the socket isn't available");
        }
      }
    };

    return Subject.create(observer, observable);
  }

  public login(email: string, password: string) {
    if(this.ws.readyState === WebSocket.OPEN) {
      let loginInfo = {
        email: email,
        password: password
      };
      this.ws.send(JSON.stringify(loginInfo));
    }
  }
}
