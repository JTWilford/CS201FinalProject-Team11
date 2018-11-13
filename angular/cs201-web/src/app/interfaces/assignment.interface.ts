import {Link} from "./link.interface";

export interface Assignment {
	number: number;               //The assignment's number (for ordering)
	title: string;                //The assignment's title
	gradePercent: number;         //The assignment's grade percentage
	assignedDate: string;         //The assignment's assigned date
	dueDate: string;              //The assignment's due date
	pdfLink: Link;                //The link to the assignment details
	solutionLink: Link;           //The link to the assignment's solution (will only be set if the assignment's due date has passed)
	additionalLinks: Link[];      //Any additional file links
}
