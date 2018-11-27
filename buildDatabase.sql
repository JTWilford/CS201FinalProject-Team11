DROP DATABASE IF EXISTS Data;
CREATE DATABASE Data;

USE Data;


CREATE TABLE Account (
  uscID BIGINT(11) PRIMARY KEY UNIQUE NOT NULL, # reference accounts on the site
  firstName VARCHAR(30) NOT NULL, # user first name
  lastName VARCHAR(50) NOT NULL, # user last name
  email VARCHAR(50) UNIQUE NOT NULL, # user email address
  password VARCHAR(50) NOT NULL, # user account password
  githubAccount VARCHAR(50) UNIQUE, # link to user github account page
  accountLevel INT(5) NOT NULL
  # accountLevel: student (3), TA/CP (2), professor (1), anything else (assume student account)
);

CREATE TABLE Links (
  linkID INT(11) PRIMARY KEY UNIQUE, # reference single-link entries in other tables
  display VARCHAR(150) NOT NULL, # text displayed in the browser for the link
  href VARCHAR(150) NOT NULL # destination of the link (starting with "/" specify relative paths)
);

CREATE TABLE Assignments (
  assignmentID INT(11) PRIMARY KEY AUTO_INCREMENT, # reference assignment entries
  number INT(6) UNIQUE NOT NULL, # the assignment number
  title VARCHAR(50) NOT NULL, # the assignment title
  gradePercent FLOAT(5,2) NOT NULL, # final grade percent
  assignedDate VARCHAR(12) NOT NULL, # (MM/DD/YYYY) the given date of assignment
  dueDate VARCHAR(12) NOT NULL, # (MM/DD/YYYY) the due date of assignment
  pdfLink INT(11), # assignment pdf link
  additionalFiles VARCHAR(300), # link to any additional files (can be null)
  solutionLink INT(11) # link to the solution (only be available after the professor enables it)
);

CREATE TABLE Lectures (
  lectureID INT(11) PRIMARY KEY AUTO_INCREMENT, # reference lecture table entries
  number INT(6) NOT NULL, # lecture number
  lectureDate VARCHAR(12) NOT NULL, # (MM/DD/YYYY) date of the lecture
  chapters VARCHAR(15) NOT NULL, # chapters covered in the lecture
  lectureSlides VARCHAR(300), # whether or not there are slides associated with the lectures
  programsLinks VARCHAR(300) # links to demos/programs/github repos
);

CREATE TABLE Labs (
  labID INT(11) PRIMARY KEY AUTO_INCREMENT, # reference lab table entries
  title VARCHAR(50) NOT NULL,
  number INT(6) UNIQUE NOT NULL, # display lab numbers in order
  labDate VARCHAR(12) NOT NULL, # (MM/DD/YYYY) date of the lab assigned
  pdfLink INT(11), # link to the lab pdf
  additionalFiles VARCHAR(300), # whether or not there are additional files associated with the lab
  solutionLink INT(11) # link to lab solution zip file / github repo
);

CREATE TABLE PreviousExams (
  examID INT(11) PRIMARY KEY AUTO_INCREMENT, # reference exam table entries
  examDate VARCHAR(30) NOT NULL, # such as "Spring 2018" or "Fall 2017"
  type VARCHAR(20) NOT NULL, # (written/programming/other) type of the exam
  pdfLink INT(11), # link to the exam pdf file
  solution INT(11) # whether or not there are solution links
);

CREATE TABLE Calendar (
  eventID INT(11) PRIMARY KEY AUTO_INCREMENT, # reference event in the calendar
  title VARCHAR(30) NOT NULL, # title of the event
  date VARCHAR(12) NOT NULL, # date of the event
  startTime VARCHAR(8) NOT NULL, # (HH:MM) start time of the event
  endTime VARCHAR(8) NOT NULL # (HH:MM) end time of the event
);

CREATE TABLE Attendance (
  attendanceID INT(11) PRIMARY KEY AUTO_INCREMENT, # reference entries in attendance table
  uscID BIGINT(11) NOT NULL, # associate an entry with a user
  date VARCHAR(12) NOT NULL, # (MM/DD/YYYY) date of the user checked into class
  lecturePeriod INT(11) NOT NULL, # lecture period the user checked in
  FOREIGN KEY fk12(uscID) REFERENCES Account(uscID)
);

INSERT INTO Account (uscID, firstName, lastName, email, password, githubAccount, accountLevel)
      VALUES (1234567890, "Justin", "Wilford", "123@usc.edu", "12345", "justin", 1),
             (7923487924, "Addison", "Herr", "456@usc.edu", "12345", "addison", 1),
             (2719131283, "Tianyi", "Yu", "789@usc.edu", "12345", "tianyi", 1),
             (7237498374, "Yash", "Bhartia", "1010@usc.edu", "12345", "yash", 1),
             (2749273492, "Yang", "Li", "1234@usc.edu", "12345", "yang", 1),
             (7239498234, "Anav", "Batra", "123141@usc.edu", "12345", "anav", 2),
             (6836287031, "Tommy", "Trojan", "ttrojan@usc.edu", "kanade", "github.usc.edu", 1),
             (1010101010, "Timeout", "Guy", "timeout@usc.edu", "kanade", "timeout.com", 3),
             (6171769067, "Thomas", "Wilford", "jtkewl@gmail.com", "kanade", "http://github.com/jtkewl", 3),
             (4923749283, "Jeffrey", "Miller", "12412@usc.edu", "12345", "jeffrey", 3);

INSERT INTO Assignments (number, title, gradePercent, assignedDate, dueDate, pdfLink, solutionLink, additionalFiles)
      VALUES (1, "Calendar Database", 2.5, "08/21/2018", "09/02/2018", 11, 110, "111,112"),
             # additional file is a sample json file, link is "http://www-scf.usc.edu/~csci201/assignments/assignment1-1.json"
             (2, "Sycamore Calendar, Part 1", 4.5, "09/04/2018", "09/23/2018", 12, 120, "121"),
             (3, "Sycamore Calendar, Part 2", 4.5, "09/25/2018", "10/14/2018", 13, 130, "131"),
             (4, "Networked Hangman", 8.5, "10/25/2018", "12/02/2018", 14, NULL, "141");
            # additional file is a text file file, link is "http://www-scf.usc.edu/~csci201/assignments/hangmanwords.txt"

#Assignment table links (id begins with 1)
INSERT INTO Links (linkID, display, href)
	  VALUES (11, "Assignment1-1.pdf", "http://www-scf.usc.edu/~csci201/assignments/Assignment1-1.pdf"),
			 (12, "Assignment2.pdf", "http://www-scf.usc.edu/~csci201/assignments/Assignment2.pdf"),
             (13, "Assignment3.pdf", "http://www-scf.usc.edu/~csci201/assignments/Assignment3.pdf"),
             (14, "Assignment4-5.pdf", "http://www-scf.usc.edu/~csci201/assignments/Assignment4-5.pdf"),
             (110, "Solution 1 (ZIP)", "http://www-scf.usc.edu/~csci201/assignments/Assignment1Solution-1.zip"),
             (120, "Solution 2 (ZIP)", "http://www-scf.usc.edu/~csci201/assignments/Assignment2Solution.zip"),
             (130, "Solution 3 (ZIP)", "http://www-scf.usc.edu/~csci201/assignments/Assignment3Solution.zip"),
             (111, "Sample JSON", "http://www-scf.usc.edu/~csci201/assignments/assignment1-1.json"),
             (112, "Grading Criteria", "http://www-scf.usc.edu/~csci201/assignments/Assignment1GradingCriteria.pdf"),
             (121, "Grading Criteria", "http://www-scf.usc.edu/~csci201/assignments/Assignment2GradingCriteria.pdf"),
             (131, "Grading Criteria", "http://www-scf.usc.edu/~csci201/assignments/Assignment3GradingCriteria.pdf"),
             (141, "Hangman Words", "http://www-scf.usc.edu/~csci201/assignments/hangmanwords.txt");

INSERT INTO Lectures (number, lectureDate, chapters, lectureSlides, programsLinks)
      VALUES (1, "08/21/2018", "1-8", "2101,2102,2103,2104,2105,2106,2107,2108", "2111,2112,2113,2114"),
             (2, "08/23/2018", "9-10", "2201,2202,2203", "2211,2212,2213"),
             (3, "08/28/2018", "11, 13", "2301,2302,2303", "2311,2312,2313,2314,2315"),
             (4, "08/30/2018", "12, 19", "2401,2402,2403,2404", "2411,2412,2413,2414,2415"),
             (5, "09/02/2018", "N/A", "2501,2502", "2511,2512,2513,2514,2515,2516");
#             (6, "09/06/2018", "37-38", "", ""),
#             (7, "09/11/2018", "N/A", "", ""),
#             (8, "09/13/2018", "N/A", "", ""),
#             (9, "09/18/2018", "N/A", "", ""),
#             (10, "09/20/2018", "N/A", "", "");
#Lectures table links (id begins with 2)
INSERT INTO Links (linkID, display, href)
	  VALUES (2101, "Introduction", "http://www-scf.usc.edu/~csci201/lectures/Lecture1/Introduction.pdf"),
			 (2102, "Environment", "http://www-scf.usc.edu/~csci201/lectures/Lecture1/Environment.pdf"),
             (2103, "Reading Parameters", "http://www-scf.usc.edu/~csci201/lectures/Lecture1/ReadingParameters.pdf"),
             (2104, "Conditions", "http://www-scf.usc.edu/~csci201/lectures/Lecture1/Conditions.pdf"),
             (2105, "Loops", "http://www-scf.usc.edu/~csci201/lectures/Lecture1/Loops.pdf"),
             (2106, "Methods", "http://www-scf.usc.edu/~csci201/lectures/Lecture1/Methods.pdf"),
             (2107, "Arrays", "http://www-scf.usc.edu/~csci201/lectures/Lecture1/Arrays.pdf"),
             (2108, "Strings", "http://www-scf.usc.edu/~csci201/lectures/Lecture1/Strings.pdf"),
             (2111, "Operation.java", "http://www-scf.usc.edu/~csci201/lectures/Lecture1/Operation.java"),
             (2112, "Fibonacci.java", "http://www-scf.usc.edu/~csci201/lectures/Lecture1/Fibonacci.java"),
             (2113, "Salary.java", "http://www-scf.usc.edu/~csci201/lectures/Lecture1/Salary.java"),
             (2114, "Dice.java", "http://www-scf.usc.edu/~csci201/lectures/Lecture1/Dice.java"),
             (2201, "Classes", ""),
             (2202, "Packages", ""),
             (2203, "File I/O", ""),
             (2211, "Salary.java", ""),
             (2212, "FileCopy.java", ""),
             (2213, "FileCopier.java", ""),
             (2301, "Inheritance", ""),
             (2302, "Abstract Classes and Interfaces", ""),
             (2303, "Polymorphism", ""),
             (2311, "TestShape.java", ""),
             (2312, "Shape.java", ""),
             (2313, "Rectangle.java", ""),
             (2314, "Square.java", ""),
             (2315, "Triangle.java", ""),
             (2401, "Garbage Collection", ""),
             (2402, "Exception Handling", ""),
             (2403, "Serialization", ""),
             (2404, "Generics", ""),
             (2411, "NumberExceptions.java", ""),
             (2412, "NumberGreaterException.java", ""),
             (2413, "Employee.java", ""),
             (2414, "EmployeeMain.java", ""),
             (2415, "GenericArray.java", ""),
             (2501, "HTML", ""),
             (2502, "CSS", ""),
             (2511, "base.html", ""),
             (2512, "css0.html", ""),
             (2513, "css1.html", ""),
             (2514, "css1.css", ""),
             (2515, "css2.html", ""),
             (2516, "css2.css", "");


INSERT INTO Labs (number, title, labDate, pdfLink, solutionlink, additionalFiles)
      VALUES (0, "GitHub Tutorial", "08/21/2018", 300, NULL, ""),
			 (1, "Environment Setup", "08/21/2018", 301, NULL, ""),
             (2, "Inheritance", "08/28/2018", 302, 312, "3221"),
             (3, "HTML/CSS", "09/04/2018", 303, 313, "3231,3232,3233,3234,3235"),
             (4, "JSP and Servlets", "09/11/2018", 304, 314, ""),
             (5, "JavaScript and AJAX", "09/18/2018", 305, 315, "");
#Labs table links (id begins with 3)
INSERT INTO LINKS (linkID, display, href)
	VALUES 	(300, "Lab0.pdf", "http://www-scf.usc.edu/~csci201/labs/Lab0.pdf"),
			(301, "Lab1.pdf", "http://www-scf.usc.edu/~csci201/labs/Lab1.pdf"),
            (302, "Lab2.pdf", "http://www-scf.usc.edu/~csci201/labs/Lab2.pdf"),
            (312, "Solution (ZIP)", "http://www-scf.usc.edu/~csci201/labs/Lab2-solution.zip"),
            (3221, "Lab2.java", "http://www-scf.usc.edu/~csci201/labs/Lab2.java"),
            (303, "Lab3.pdf", "http://www-scf.usc.edu/~csci201/labs/Lab3.pdf"),
            (313, "Solution (ZIP)", "http://www-scf.usc.edu/~csci201/labs/Lab3-solution.zip"),
            (3231, "css0.html", "http://www-scf.usc.edu/~csci201/labs/css0.html"),
            (3232, "css1.html", "http://www-scf.usc.edu/~csci201/labs/css1.html"),
            (3233, "css1.css", "http://www-scf.usc.edu/~csci201/labs/css1.css"),
            (3234, "css2.html", "http://www-scf.usc.edu/~csci201/labs/css2.html"),
            (3235, "css2.css", "http://www-scf.usc.edu/~csci201/labs/css2.css"),
            (304, "Lab4.pdf", ""),
            (314, "Solution (ZIP)", ""),
            (305, "Lab5.pdf", ""),
            (315, "Solution (ZIP)", "");

INSERT INTO PreviousExams (examDate, type, pdfLink, solution)
      VALUES ("Fall 2018", "Written Exam 1", 401, 411),
             ("Spring 2018", "Written Exam 1", 402, 412),
             ("Spring 2018", "Written Exam 2", 403, 413),
             ("Fall 2017", "Written Exam 1", 404, 414),
             ("Fall 2017", "Written Exam 2", 405, 415),
             ("Fall 2017", "Programming Exam", 406, 416);
#Previous exams links (id begins with 4)
INSERT INTO Links (linkID, display, href)
	VALUES	(401, "PDF", "http://www-scf.usc.edu/~csci201/exams/midterm-written-fall2018.pdf"),
			(411, "Solution", "http://www-scf.usc.edu/~csci201/exams/midterm-written-fall2018-SOLUTION.pdf"),
			(402, "PDF", "http://www-scf.usc.edu/~csci201/exams/midterm-written-spring2018.pdf"),
            (412, "Solution", "http://www-scf.usc.edu/~csci201/exams/midterm-written-spring2018-SOLUTION.pdf"),
            (403, "PDF", "http://www-scf.usc.edu/~csci201/exams/final-written-spring2018.pdf"),
            (413, "Solution", "http://www-scf.usc.edu/~csci201/exams/final-written-spring2018-SOLUTION.pdf"),
            (404, "PDF", "http://www-scf.usc.edu/~csci201/exams/midterm-written-fall2017.pdf"),
            (414, "Solution", "http://www-scf.usc.edu/~csci201/exams/midterm-written-fall2017-SOLUTION.pdf"),
            (405, "PDF", "http://www-scf.usc.edu/~csci201/exams/final-written-fall2017.pdf"),
            (415, "Solution", "http://www-scf.usc.edu/~csci201/exams/final-written-fall2017-SOLUTION.pdf"),
            (406, "PDF", "http://www-scf.usc.edu/~csci201/exams/final-programming-fall2017.pdf"),
            (416, "Grading Criteria", "http://www-scf.usc.edu/~csci201/exams/final-programming-fall2017-gradingcriteria.pdf");


INSERT INTO Calendar (title, date, startTime, endTime)
      VALUES ("CSCI 201 Lecture", "08/21/2018", "11:00", "12:20"),
             ("CSCI 201 Lab", "08/21/2018", "16:00", "18:00");

INSERT INTO Attendance (uscID, date, lecturePeriod)
      VALUES (1234567890, "08/21/2018", 3),
             (7923487924, "08/23/2018", 1),
             (2719131283, "09/18/2018", 2);
