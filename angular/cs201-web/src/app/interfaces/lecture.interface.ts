import {Link} from "./link.interface";

export interface Lecture {
  number: number;
  lectureDate: string;
  lectureTopic: string;
  chapters: string;
  lectureSlides: Link[];
  programFiles: Link;
}
