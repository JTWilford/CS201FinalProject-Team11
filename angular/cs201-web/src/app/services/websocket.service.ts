import { Injectable } from '@angular/core';
import {Observable, Observer, Subject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  constructor() { }

  private subject: Subject<MessageEvent>;

  public connect(url = "ws://localhost:8080/session"): Subject<MessageEvent> {
    if(!this.subject) {
      this.subject = this.create(url);
      console.log("[WebSocketService] Successfully Connected: " + url);
    }
    return this.subject;
  }

  private create(url): Subject<MessageEvent> {
    let ws = new WebSocket(url);

    let observable = Observable.create(
      (obs: Observer<MessageEvent>) => {
        ws.onmessage = obs.next.bind(obs);
        ws.onerror = obs.error.bind(obs);
        ws.onclose = obs.complete.bind(obs);
        return ws.close.bind(obs);
      }
    )

    let observer = {
      next: (data: string) => {
        if(ws.readyState === WebSocket.OPEN) {
          //ws.send(JSON.stringify(data));
          ws.send(data);
        }
      }
    }

    return Subject.create(observer, observable);
  }
}
