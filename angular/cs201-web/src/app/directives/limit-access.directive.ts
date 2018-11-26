import {Directive, Input, OnChanges, OnDestroy, OnInit, TemplateRef, ViewContainerRef} from '@angular/core';
import {AuthenticationService} from "../services/authentication.service";
import {Subscription} from "rxjs";

@Directive({
  selector: '[appLimitAccess]'
})
export class LimitAccessDirective {

  constructor(private templateRef: TemplateRef<any>,
              private viewContainer: ViewContainerRef,
              private authenticationService: AuthenticationService) {
  }

  @Input() set appLimitAccess(access: string) {
    let required = 4;
    let absolute = false;
    if(access.charAt(0) == '-') {
      console.log("[Limit Access] Absolute requirement found");
      access = access.substr(1);
      absolute = true;
    }
    switch(access) {
      case "student":
        required = 3;
        break;
      case "ta/cp":
        required = 2;
        break;
      case "professor":
        required = 1;
        break;
      default:
        required = 4;
        break;
    }
    console.log("[LimitAccess] Required: " + access + " : " + required);
    if (access) {
      if(absolute) {
        this.authenticationService.userLevel$.subscribe(((level) => {
          if (level === required) {
            console.log("[LimitAccess] User is (absolutely) authorized!");
            this.viewContainer.createEmbeddedView(this.templateRef);
          }
          else {
            console.log("[LimitAccess] User is not (absolutely) authorized");
            this.viewContainer.clear();
          }
        }));
      }
      else {
        this.authenticationService.userLevel$.subscribe((level) => {
          else if (level <= required) {
            console.log("[LimitAccess] User is authorized!");
            this.viewContainer.createEmbeddedView(this.templateRef);
          }
          else {
            console.log("[LimitAccess] User is not authorized");
            this.viewContainer.clear();
          }
        });
      }
    } else {
      console.log('[LimitAccess] Required privileges not set.');
      this.viewContainer.clear();
    }
  }
}
