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
  # accountLevel: student (1), TA/CP (2), professor (3), anything else (assume student account)
);

CREATE TABLE Links (
  linkID INT(11) PRIMARY KEY AUTO_INCREMENT, # reference single-link entries in other tables
  displayedText VARCHAR(30) NOT NULL, # text displayed in the browser for the link
  href VARCHAR(150) NOT NULL, # destination of the link (starting with "/" specify relative paths)
  assignmentID INT(11), # generate lists of links in the assignment table for additional files
  lectureID INT(11), # generate lists of links in the lecture table for additional files
  labID INT(11), # generate lists of links in the lab table for additional files
  examID INT(11) # generate lists of links in the exam table for additional files
#   FOREIGN KEY fk7(lectureID) REFERENCES Lectures(lectureID),
#   FOREIGN KEY fk8(assignmentID) REFERENCES Assignments(assignmentID),
#   FOREIGN KEY fk9(labID) REFERENCES Labs(labID),
#   FOREIGN KEY fk10(examID) REFERENCES PreviousExams(examID)
);

CREATE TABLE Assignments (
  assignmentID INT(11) PRIMARY KEY AUTO_INCREMENT, # reference assignment entries
  number INT(6) UNIQUE NOT NULL, # the assignment number
  title VARCHAR(50) NOT NULL, # the assignment title
  gradePercent FLOAT(5,2) NOT NULL, # final grade percent
  assignedDate VARCHAR(12) NOT NULL, # (MM/DD/YYYY) the given date of assignment
  dueDate VARCHAR(12) NOT NULL, # (MM/DD/YYYY) the due date of assignment
  pdfLink INT(11), # assignment pdf link
  additionalFiles BOOLEAN NOT NULL DEFAULT FALSE, # link to any additional files (can be null)
  solutionLink INT(11), # link to the solution (only be available after the professor enables it)
  FOREIGN KEY fk1(pdfLink) REFERENCES Links(linkID),
  FOREIGN KEY fk2(solutionLink) REFERENCES Links(linkID)
);

CREATE TABLE Lectures (
  lectureID INT(11) PRIMARY KEY AUTO_INCREMENT, # reference lecture table entries
  number INT(6) NOT NULL, # lecture number
  lectureDate VARCHAR(12) NOT NULL, # (MM/DD/YYYY) date of the lecture
  lectureTopics VARCHAR(150) NOT NULL, # list of topics covered in the lecture
  chapters VARCHAR(15) NOT NULL, # chapters covered in the lecture
  lectureSlides BOOLEAN NOT NULL, # whether or not there are slides associated with the lectures
  programsLinks INT(11), # links to demos/programs/github repos
  FOREIGN KEY fk3(programsLinks) REFERENCES Links(linkID)
);

CREATE TABLE Labs (
  labID INT(11) PRIMARY KEY AUTO_INCREMENT, # reference lab table entries
  title VARCHAR(50) NOT NULL,
  number INT(6) UNIQUE NOT NULL, # display lab numbers in order
  labDate VARCHAR(12) NOT NULL, # (MM/DD/YYYY) date of the lab assigned
  labTopics VARCHAR(150) NOT NULL, # topics covered in the lab
  pdfLink INT(11), # link to the lab pdf
  additionalFiles BOOLEAN NOT NULL DEFAULT FALSE, # whether or not there are additional files associated with the lab
  solutionLink INT(11), # link to lab solution zip file / github repo
  FOREIGN KEY fk4(pdfLink) REFERENCES Links(linkID),
  FOREIGN KEY fk5(solutionLink) REFERENCES Links(linkID)
);

CREATE TABLE PreviousExams (
  examID INT(11) PRIMARY KEY AUTO_INCREMENT, # reference exam table entries
  examDate VARCHAR(30) NOT NULL, # such as "Spring 2018" or "Fall 2017"
  type VARCHAR(20) NOT NULL, # (written/programming/other) type of the exam
  pdfLink INT(11), # link to the exam pdf file
  solutions BOOLEAN NOT NULL, # whether or not there are solution links
  FOREIGN KEY fk6(pdfLink) REFERENCES Links(linkID)
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
             (1111111111, "Tommy", "Trojan", "ttrojan@usc.edu", "kanade", "github.usc.edu", 1),
             (1111111111, "Timeout", "Guy", "timeout@usc.edu", "kanade", "timeout.com", 3),
             (1111111111, "Thomas", "Wilford", "jtkewl@gmail.com", "kanade", "http://github.com/jtkewl", 3),
             (4923749283, "Jeffrey", "Miller", "12412@usc.edu", "12345", "jeffrey", 3);

INSERT INTO Assignments (number, title, gradePercent, assignedDate, dueDate, additionalFiles)
      VALUES (1, "Calendar Database", 2.5, "08/21/2018", "09/02/2018", TRUE),
             # additional file is a sample json file, link is "http://www-scf.usc.edu/~csci201/assignments/assignment1-1.json"
             (2, "Sycamore Calendar, Part 1", 4.5, "09/04/2018", "09/23/2018", FALSE),
             (3, "Sycamore Calendar, Part 2", 4.5, "09/25/2018", "10/14/2018", FALSE),
             (4, "Networked Hangman", 8.5, "10/25/2018", "12/02/2018", TRUE);
            # additional file is a text file file, link is "http://www-scf.usc.edu/~csci201/assignments/hangmanwords.txt"

INSERT INTO Lectures (number, lectureDate, lectureTopics, chapters, lectureSlides)
      VALUES (1, "08/21/2018", "Introduction", "1-8", TRUE),
             (1, "08/21/2018", "Environment", "1-8", TRUE),
             (1, "08/21/2018", "Reading Parameters", "1-8", TRUE),
             (1, "08/21/2018", "Conditions", "1-8", TRUE),
             (1, "08/21/2018", "Loops", "1-8", TRUE),
             (1, "08/21/2018", "Methods", "1-8", TRUE),
             (1, "08/21/2018", "Arrays", "1-8", TRUE),
             (1, "08/21/2018", "Strings", "1-8", TRUE),
             (2, "08/23/2018", "Classes", "9-10", TRUE),
             (2, "08/23/2018", "Packages", "9-10", TRUE),
             (2, "08/23/2018", "File I/O", "9-10", TRUE),
             (3, "08/28/2018", "Inheritance", "11, 13", TRUE),
             (3, "08/28/2018", "Abstract Classes and Interfaces", "11, 13", TRUE),
             (3, "08/28/2018", "Polymorphism", "11, 13", TRUE),
             (4, "08/30/2018", "Garbage Collection", "12, 19", TRUE),
             (4, "08/30/2018", "Exception Handling", "12, 19", TRUE),
             (4, "08/30/2018", "Serialization", "12, 19", TRUE),
             (4, "08/30/2018", "Generics", "12, 19", TRUE),
             (5, "09/02/2018", "HTML", "", TRUE),
             (5, "09/02/2018", "CSS", "", TRUE),
             (6, "09/06/2018", "Java Servlets", "37-38", TRUE),
             (6, "09/06/2018", "JSP", "37-38", TRUE),
             (7, "09/11/2018", "Javascript", "", TRUE),
             (8, "09/13/2018", "AJAX", "", TRUE),
             (9, "09/18/2018", "Software Engineering", "", TRUE),
             (9, "09/18/2018", "Methodologies", "", TRUE),
             (9, "09/18/2018", "Testing", "", TRUE),
             (10, "09/20/2018", "Final Project Discussion", "", TRUE);


INSERT INTO Labs (number, title, labDate, labTopics, additionalFiles)
      VALUES (1, "Environment Setup", "08/21/2018", "", FALSE),
             (2, "Employee Salary Determination", "08/28/2018",  "Inheritance, Polymorphism", TRUE),
             # additional file is a java file, link is http://www-scf.usc.edu/~csci201/labs/Lab2.java
             (3, "Customizing HTML with CSS", "09/04/2018", "HTML, CSS", TRUE),
             # additional files are 3 html files and 2 css files, links are:
                # http://www-scf.usc.edu/~csci201/labs/css0.html
                # http://www-scf.usc.edu/~csci201/labs/css1.html
                # http://www-scf.usc.edu/~csci201/labs/css0.css
                # http://www-scf.usc.edu/~csci201/labs/css2.html
                # http://www-scf.usc.edu/~csci201/labs/css2.css
             (4, "Form Validation with Servlets and JSPs", "09/11/2018", "HTML, Javascript, Servlets, JSP", FALSE),
             (5, "Form Validation with Servlets, JSPs, and AJAX", "09/18/2018", "HTML, Javascript, Servlets, JSP, AJAX", FALSE),
             (6, "MySQL Installation", "09/25/2018", "Databases", FALSE),
             (7, "Software Engineering", "10/02/2018", "Software Engineering", FALSE);

INSERT INTO PreviousExams (examDate, type, solutions)
      VALUES ("Fall 2018", "written exam1", TRUE),
             ("Spring 2018", "written exam1", TRUE),
             ("Spring 2018", "written exam2", TRUE),
             ("Fall 2017", "written exam1", TRUE),
             ("Fall 2017", "written exam2", TRUE),
             ("Fall 2017", "programming exam", TRUE); # the grading criteria is used as solutions


INSERT INTO Calendar (title, date, startTime, endTime)
      VALUES ("CSCI 201 Lecture", "08/21/2018", "11:00", "12:20"),
             ("CSCI 201 Lab", "08/21/2018", "16:00", "18:00");

INSERT INTO Attendance (uscID, date, lecturePeriod)
      VALUES (1234567890, "08/21/2018", 3),
             (7923487924, "08/23/2018", 1),
             (2719131283, "09/18/2018", 2);
