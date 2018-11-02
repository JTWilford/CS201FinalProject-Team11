import {HttpClient} from "@angular/common/http";

export interface UserInterface {
  firstName: string;
  lastName: string;
  email: string;
  uscID: number;
  level: number;
  levelString: string;
}
