import {Link} from "./link.interface";

export interface Lab {
  number: number;
  labDate: string;
  labTopics: string;
  pdfLink: Link;
  additionalFiles: Link[];
}
