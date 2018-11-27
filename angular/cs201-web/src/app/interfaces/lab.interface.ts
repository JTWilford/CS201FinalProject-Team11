import {Link} from "./link.interface";

export interface Lab {
  number: number;
  labDate: string;
  title: string;
  pdfLink: Link;
  solutionLink: Link;
  additionalFiles: Link[];
}
