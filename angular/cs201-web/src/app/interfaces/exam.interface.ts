import {Link} from "./link.interface";

export interface Exam {
  date: string;
  type: string;
  pdfLink: Link;
  solutionLinks: Link[];
}
